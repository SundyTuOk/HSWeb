package com.sf.energy.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.FTAmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.model.FTAmmeterModel;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.Formula_Calculator;

/**
 * 能耗分摊电表电能数据有关操作
 * @author Administrator
 *
 */
public class FTAmCurrentManageDao
{
	DateFormat df  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DecimalFormat decimalFormat=new DecimalFormat(".00");

	public void autoreplenishFTAmDataPlanExec(){
		int intervalSeconds = 300;
		Date now = new Date();
		autoreplenishFTAmData(now, intervalSeconds);	
	}


	/**
	 * 自动插补数据
	 * 重载方法  处理传进来的所有电表
	 * @param ids 电表id数组
	 * @param endDate 截止日期
	 * 		起始日期从电表数据表中查询最后一条数据日期
	 * @param intervalSeconds 单位 秒，每隔多长时间一条数据
	 * @return
	 */
	public String autoreplenishFTAmData(String[] ids,Date endDate,int intervalSeconds){
		if(ids==null){
			System.out.println("无分摊电表!");		
			return "无分摊电表!";
		}else{
			for(int i=0;i<ids.length;i++){
				autoReplenishFTAmData(Integer.parseInt(ids[i]), endDate, intervalSeconds);
			}
			return "选中电表数据插入完成!";
		}
	}
	/**
	 * 自动插补数据
	 * 重载方法  查询数据库中所有分摊电表，进行处理
	 * @param endDate 截止日期
	 * 	起始日期从电表数据表中查询最后一条数据日期
	 * @param intervalSeconds 单位 秒，每隔多长时间一条数据
	 * @return
	 */
	public String autoreplenishFTAmData(Date endDate,int intervalSeconds){
		FTAmmeterDao fDao = new FTAmmeterDao();
		List<FTAmmeterModel> list = fDao.queryAllFTAmmeterIDs();
		if(list==null){
			System.out.println("无分摊电表!");
			return "无分摊电表!";
		}else{
			for(FTAmmeterModel model:list){
				autoReplenishFTAmData(model, endDate, intervalSeconds);
			}
			return "所有电表数据插入完成!";
		}
	}
	/**
	 * 自动插补数据
	 * 重载方法 处理单个电表 
	 * @param ammeter_Id 电表id
	 * @param endDate
	 * @param intervalSeconds
	 * @return
	 */
	public String autoReplenishFTAmData(int ammeter_Id,Date endDate,int intervalSeconds){
		FTAmmeterDao fDao = new FTAmmeterDao();
		FTAmmeterModel model = fDao.queryOneFTAmmeter(ammeter_Id);
		if(model!=null){
			String formula = model.getFormula();
			String pramIds = model.getParameters();
			if(formula!=null&&!formula.equals("")
					&&pramIds!=null&&!pramIds.equals("")){
				String[] ids = pramIds.split(",");
				Date startDate=null;
				AmMeterMataData lastData = getAmmeterLastDataBeforeTime(ammeter_Id,null);
				if(lastData==null){
					startDate = getFirstDateInAmmeters(ids, null, endDate);
					if(startDate==null){
						System.out.println(model.getAmmeterName()+" "+df.format(startDate)+"-"+df.format(endDate)
								+" 内所关联电表当前时间段内无数据");
						return model.getAmmeterName()+" "+df.format(startDate)+"-"+df.format(endDate)
								+" 内所关联电表当前时间段内无数据";
					}
				}else{
					startDate = lastData.getRecordTimeDate();
				}
				if(endDate.before(startDate)){
					System.out.println(model.getAmmeterName()+" 日期错误!");
					return model.getAmmeterName()+" 日期错误!";
				}
				int count = replenishFTAmmeterData(ammeter_Id, startDate, endDate, intervalSeconds);
				if(count==-1){
					System.out.println(model.getAmmeterName()+" "+df.format(startDate)+"-"+df.format(endDate)
							+" 内所关联电表当前时间段内无数据");
					return model.getAmmeterName()+" "+df.format(startDate)+"-"+df.format(endDate)
							+" 内所关联电表当前时间段内无数据";
				}
				System.out.println(model.getAmmeterName()+" "+df.format(startDate)+"-"+df.format(endDate)
						+" 内插补数据:"+count);
				return model.getAmmeterName()+" "+df.format(startDate)+"-"+df.format(endDate)
						+" 内插补数据:"+count;
			}else{
				System.out.println(model.getAmmeterName()+" 无能耗公式信息!");
				return model.getAmmeterName()+" 无能耗公式信息!";
			}
		}else{
			System.out.println("电表 "+ammeter_Id+"不属于分摊电表!");
			return "电表 "+ammeter_Id+"不属于分摊电表!";
		}
	}
	/**
	 * 重载方法 处理单个电表 
	 * @param model 已经封装好的单个电表对象 里面包括电表id 参数 公式信息
	 * @param endDate
	 * @param intervalSeconds
	 * @return
	 */
	public String autoReplenishFTAmData(FTAmmeterModel model,Date endDate,int intervalSeconds){
		if(model!=null){
			int ammeter_Id = model.getAmmeterId();
			if(ammeter_Id<=0){
				return "电表Id有误!";
			}
			String formula = model.getFormula();
			String pramIds = model.getParameters();
			if(formula!=null&&!formula.equals("")
					&&pramIds!=null&&!pramIds.equals("")){
				String[] ids = pramIds.split(",");
				Date startDate=null;
				AmMeterMataData lastData = getAmmeterLastDataBeforeTime(ammeter_Id,null);
				if(lastData==null){
					startDate = getFirstDateInAmmeters(ids, null, endDate);
					if(startDate==null){
						System.out.println("电表 "+ammeter_Id+" "+df.format(startDate)+"-"+df.format(endDate)
								+" 内所关联电表当前时间段内无数据");
						return "电表 "+ammeter_Id+" "+df.format(startDate)+"-"+df.format(endDate)
								+" 内所关联电表当前时间段内无数据";
					}
				}else{
					startDate = lastData.getRecordTimeDate();
				}
				if(endDate.before(startDate)){
					System.out.println("电表 "+ammeter_Id+" 日期错误!");
					return "电表 "+ammeter_Id+" 日期错误!";
				}
				int count = replenishFTAmmeterData(ammeter_Id, startDate, endDate, intervalSeconds);
				if(count==-1){
					System.out.println(model.getAmmeterName()+" "+df.format(startDate)+"-"+df.format(endDate)
							+" 内所关联电表当前时间段内无数据");
					return model.getAmmeterName()+" "+df.format(startDate)+"-"+df.format(endDate)
							+" 内所关联电表当前时间段内无数据";
				}
				System.out.println("电表 "+ammeter_Id+" "+df.format(startDate)+"-"+df.format(endDate)
						+" 内插补数据:"+count);
				return "电表 "+ammeter_Id+" "+df.format(startDate)+"-"+df.format(endDate)
						+" 内插补数据:"+count;
			}else{
				System.out.println(model.getAmmeterName()+" 无能耗公式信息!");
				return model.getAmmeterName()+" 无能耗公式信息!";
			}
		}else{
			System.out.println("电表不属于分摊电表!");
			return "电表不属于分摊电表!";
		}
	}
	/**
	 * 删除指定电表在时间段内的所有数据
	 * @param ammeter_Id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean deleteFTAmmeterData(int ammeter_Id,Date startDate,Date endDate){
		Connection conn = null;
		PreparedStatement ps = null;
		String condition = "";
		if(endDate!=null){
			condition=" WHERE VALUETIME<=TO_DATE('"+df.format(endDate)+"', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if(startDate!=null){
			condition+=" and VALUETIME>=TO_DATE('"+df.format(startDate)+"', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		String sql = "DELETE from ZAMDATAS"+ammeter_Id+
				" "+condition;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		} finally
		{
			ConnDB.release(conn, ps);
		}	
	}
	/**
	 * 插补指定时间段内的分摊电表数据
	 * @param ammeter_Id
	 * @param startDate
	 * @param endDate
	 * @param intervalSeconds 间隔时间 单位为秒
	 * @return
	 */
	public int replenishFTAmmeterData(int ammeter_Id,Date startDate,Date endDate,int intervalSeconds){
		FTAmmeterDao dao = new FTAmmeterDao();
		FTAmmeterModel model = dao.queryOneFTAmmeter(ammeter_Id);
		String formula = dao.formateFormula(model.getParameters(), model.getFormula());
		int successCount = 0;
		if(formula!=null){
			String parmIds = model.getParameters();
			if(parmIds!=null&&!parmIds.equals("")){
				String[] ids = parmIds.split(","); 
				Date lastdDate = getLastDateInAmmeters(ids,startDate,endDate);
				if(lastdDate!=null){
					endDate = adjustDate(lastdDate);
					AmMeterMataData[] datas = new AmMeterMataData[ids.length];
					while(endDate.after(startDate)){
						Date beforDate = new Date(endDate.getTime()-intervalSeconds*1000);
						for(int i=0;i<ids.length;i++){
							int id = Integer.parseInt(ids[i]);					
							AmMeterMataData currData = getAmmeterLastDataBeforeTime(id, beforDate, endDate);
							if(currData==null){
								currData = getAmmeterLastDataBeforeTime(id, null, endDate);
								if(currData==null){
									currData = new AmMeterMataData();
									currData.setAmMeterID(id);
									currData.setRecordTimeDate(beforDate);
									currData.setValue((float)0);
								}else{
									currData.setRecordTimeDate(beforDate);
								}
							}
							datas[i] = currData;
						}
						AmMeterMataData insertData = getFTAmmeterInsertData(ammeter_Id, formula, datas);
						System.out.println(insertData);
						if(insertData!=null){
							try
							{
								if(insertDataIntoZamdatas(ammeter_Id,df.format(insertData.getRecordTimeDate()),decimalFormat.format(insertData.getValue()))==0){
									successCount++;
								}
							} catch (Exception e)
							{
								e.printStackTrace();
							}	
						}
						endDate = new Date(endDate.getTime()-intervalSeconds*1000);
					}
				}else{
					return -1;
				}
			}
		}else{
			return -2;
		}
		return successCount;
	}
	/**
	 * 查询电表在指定时间点内的最近一条数据的读取时间
	 * @param ids
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Date getLastDateInAmmeters(String[] ids,Date startDate,Date endDate){
		Date date = null;
		for(int i=0;i<ids.length;i++){
			AmMeterMataData currData = getAmmeterLastDataBeforeTime(Integer.parseInt(ids[i]), startDate, endDate);
			if(currData!=null){
				if(date==null){
					date = currData.getRecordTimeDate();
				}else{
					if(currData.getRecordTimeDate().after(date)){
						date = currData.getRecordTimeDate();
					}
				}
			}
		}
		return date;
	}
	/**
	 * 查询电表在指定时间点内的最早一条数据的读取时间
	 * @param ids
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Date getFirstDateInAmmeters(String[] ids,Date startDate,Date endDate){
		Date date = null;
		for(int i=0;i<ids.length;i++){
			AmMeterMataData currData = getAmmeterFirstDataBeforeTime(Integer.parseInt(ids[i]), startDate, endDate);
			if(currData!=null){
				if(date==null){
					date = currData.getRecordTimeDate();
				}else{
					if(currData.getRecordTimeDate().before(date)){
						date = currData.getRecordTimeDate();
					}
				}
			}
		}
		return date;
	}
	/**
	 * 根据分摊电表id和需要插入时间点插入能耗分摊数据
	 * @param ammeter_Id
	 * @param date 需要插入数据的时间点
	 * @return
	 */
	public boolean insertIntoFTAmmeterData(int ammeter_Id,Date date){
		AmMeterMataData data = getFTAmmeterInsertData(ammeter_Id,date);
		try
		{
			if(data!=null){
				insertDataIntoZamdatas(ammeter_Id,df.format(data.getRecordTimeDate()),decimalFormat.format(data.getValue()));	
				//System.out.println("result:"+result);
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 获取电表数据表中指定时间点前最近一条数据
	 * @param ammeter_Id
	 * @param date
	 * @return
	 */
	public AmMeterMataData getAmmeterLastDataBeforeTime(int ammeter_Id,Date date){
		AmmeterModel am = null;
		try
		{
			am = new AmmeterDao().queryByID(ammeter_Id);
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		AmMeterMataData data = null;
		if(am!=null){
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String condition = "";
			if(date!=null){
				condition=" WHERE VALUETIME<=TO_DATE('"+df.format(date)+"', 'yyyy-mm-dd hh24:mi:ss') ";
			}
			String sql = "SELECT a.*, ROWNUM rn from (SELECT * from ZAMDATAS"+ammeter_Id+
					" "+condition+" ORDER BY VALUETIME DESC )a  where ROWNUM =1";
			//System.out.println(sql);
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					data = new AmMeterMataData();
					data.setAmMeterID(ammeter_Id);
					data.setValue((rs.getFloat("ZVALUEZY")-am.getXiuzheng())/am.getBeilv());
					data.setRecordTimeDate(df.parse(df.format(rs.getTimestamp("VALUETIME"))));
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}	
		}
		return data;
	}
	/**
	 * 获取电表数据表中指定时间段内最近一条数据
	 * @param ammeter_Id
	 * @param date
	 * @return
	 */
	public AmMeterMataData getAmmeterLastDataBeforeTime(int ammeter_Id,Date startDate,Date endDate){
		AmmeterModel am = null;
		try
		{
			am = new AmmeterDao().queryByID(ammeter_Id);
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		AmMeterMataData data = null;
		if(am!=null){
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String condition = "";
			if(endDate!=null){
				condition=" WHERE VALUETIME<=TO_DATE('"+df.format(endDate)+"', 'yyyy-mm-dd hh24:mi:ss') ";
			}
			if(startDate!=null){
				condition+=" and VALUETIME>=TO_DATE('"+df.format(startDate)+"', 'yyyy-mm-dd hh24:mi:ss') ";
			}
			String sql = "SELECT a.*, ROWNUM rn from (SELECT * from ZAMDATAS"+ammeter_Id+
					" "+condition+" ORDER BY VALUETIME DESC )a  where ROWNUM =1";
			//System.out.println(sql);
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next())
				{				
					data = new AmMeterMataData();
					data.setAmMeterID(ammeter_Id);
					data.setValue((rs.getFloat("ZVALUEZY")-am.getXiuzheng())/am.getBeilv());
					data.setRecordTimeDate(df.parse(df.format(rs.getTimestamp("VALUETIME"))));
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}	
		}
		return data;
	}
	/**
	 * 获取指定时间段内最早一条数据
	 * @param ammeter_Id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public AmMeterMataData getAmmeterFirstDataBeforeTime(int ammeter_Id,Date startDate,Date endDate){

		AmmeterModel am = null;
		try
		{
			am = new AmmeterDao().queryByID(ammeter_Id);
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		AmMeterMataData data = null;
		if(am!=null){
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String condition = "";
			if(endDate!=null){
				condition=" WHERE VALUETIME<=TO_DATE('"+df.format(endDate)+"', 'yyyy-mm-dd hh24:mi:ss') ";
			}
			if(startDate!=null){
				condition+=" and VALUETIME>=TO_DATE('"+df.format(startDate)+"', 'yyyy-mm-dd hh24:mi:ss') ";
			}
			String sql = "SELECT a.*, ROWNUM rn from (SELECT * from ZAMDATAS"+ammeter_Id+
					" "+condition+" ORDER BY VALUETIME ASC )a  where ROWNUM =1";
			//System.out.println(sql);
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next())
				{				
					data = new AmMeterMataData();
					data.setAmMeterID(ammeter_Id);
					data.setValue((rs.getFloat("ZVALUEZY")-am.getXiuzheng())/am.getBeilv());
					data.setRecordTimeDate(df.parse(df.format(rs.getTimestamp("VALUETIME"))));
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}	
		}
		return data;
	}
	/**
	 * 根据分摊电表id时间点 计算封装当前输入时间点的电表数据对象 
	 * 重载方法
	 * @param ammeterId
	 * @param date
	 * @return
	 */
	public AmMeterMataData getFTAmmeterInsertData(int ammeterId,Date date){
		AmMeterMataData data = null;
		FTAmmeterDao dao = new FTAmmeterDao();
		FTAmmeterModel model = dao.queryOneFTAmmeter(ammeterId);
		String formula = dao.formateFormula(model.getParameters(), model.getFormula());
		/**
		 * formula eg: Am(23227)^10+Am(43196)
		 * Am() 括号中为虚拟表关联的电表id
		 */
		if(formula!=null){
			String amIds = model.getParameters();
			String ids[] = amIds.split(",");
			Date insertDate = null;
			AmMeterMataData currData = null;
			for(int i=0;i<ids.length;i++){
				currData = getAmmeterLastDataBeforeTime(Integer.parseInt(ids[i]), date);
				if(currData!=null){
					formula = formula.replace("Am("+ids[i]+")", String.valueOf(currData.getValue()));
					//System.out.println(formula);
					if(insertDate==null){
						insertDate = currData.getRecordTimeDate();
					}else{
						if(insertDate.before(currData.getRecordTimeDate())){
							insertDate = currData.getRecordTimeDate();
						}
					}
				}
			}
			if(insertDate!=null){
				data = new AmMeterMataData();
				data.setRecordTimeDate(insertDate);
				data.setAmMeterID(ammeterId);
				try
				{
					data.setValue(Formula_Calculator.CalculatorFormula(formula));
				} catch (Exception e)
				{
					e.printStackTrace();
					return null;
				}
			}
		}
		return data;
	}
	/**计算封装能耗分摊电表数据对象
	 * 重载方法
	 * 分摊电表关联电表的数据对象已知时可使用此方法
	 * @param ammeterId
	 * @param datas 
	 * @return
	 */
	public AmMeterMataData getFTAmmeterInsertData(int ammeterId,AmMeterMataData...datas){
		AmMeterMataData data = null;
		FTAmmeterDao dao = new FTAmmeterDao();
		String formula = dao.getFormulaInfo(ammeterId);
		/**
		 * formula eg: Am(23227)^10+Am(43196)
		 * Am() 括号中为虚拟表关联的电表id
		 */
		if(formula!=null){
			Date insertDate = null;
			AmMeterMataData currData = null;
			for(int i=0;i<datas.length;i++){
				currData = datas[i];
				if(currData!=null){
					formula = formula.replace("Am("+currData.getAmMeterID()+")", String.valueOf(currData.getValue()));
					//System.out.println(formula);
					if(insertDate==null){
						insertDate = currData.getRecordTimeDate();
					}else{
						if(insertDate.before(currData.getRecordTimeDate())){
							insertDate = currData.getRecordTimeDate();
						}
					}
				}
			}
			if(insertDate!=null){
				data = new AmMeterMataData();
				data.setRecordTimeDate(insertDate);
				data.setAmMeterID(ammeterId);
				try
				{
					data.setValue(Formula_Calculator.CalculatorFormula(formula));
				} catch (Exception e)
				{
					e.printStackTrace();
					return null;
				}
			}
		}
		return data;
	}
	/**计算封装能耗分摊电表数据对象
	 * 重载方法 
	 * 已知电表id 公式信息 关联电表数据对象
	 * @param ammeterId
	 * @param formula
	 * @param datas
	 * @return
	 */
	public AmMeterMataData getFTAmmeterInsertData(int ammeterId,String formula,AmMeterMataData...datas){
		AmMeterMataData data = null;
		//String formula = dao.getFormulaInfo(ammeterId);
		/**
		 * formula eg: Am(23227)^10+Am(43196)
		 * Am() 括号中为虚拟表关联的电表id
		 */
		if(formula!=null){
			Date insertDate = null;
			AmMeterMataData currData = null;
			for(int i=0;i<datas.length;i++){
				currData = datas[i];
				if(currData!=null){
					formula = formula.replace("Am("+currData.getAmMeterID()+")", String.valueOf(currData.getValue()));
					//System.out.println(formula);
					if(insertDate==null){
						insertDate = currData.getRecordTimeDate();
					}else{
						if(insertDate.before(currData.getRecordTimeDate())){
							insertDate = currData.getRecordTimeDate();
						}
					}
				}
			}
			if(insertDate!=null){
				data = new AmMeterMataData();
				data.setRecordTimeDate(insertDate);
				data.setAmMeterID(ammeterId);
				try
				{
					data.setValue(Formula_Calculator.CalculatorFormula(formula));
				} catch (Exception e)
				{
					e.printStackTrace();
					return null;
				}
			}
		}
		return data;
	}

	public int insertDataIntoZwaterdatas(int wmmeterId,String readTime, String dataValue,String CURRENTFLOW) throws Exception
	{
		String meterID = "";
		double beilv = 1;
		String xiuZheng = "";
		double inValue = 0;
		double zValueZY = 0;
		String sql = "";
		String valueTime = "";
		int result = 100;
		double limitPart = 0;
		String LastTime="";
		if (Double.parseDouble(dataValue)>320000)
		{
			result=0;
		}
		// 获取meterID,倍率，修正参数
		sql = "select WaterMeter_ID,nvl(beilv,1) as beilv,nvl(Xiuzheng,0) as xiuzheng,nvl(LIMITPART,0) as LIMITPART,nvl(to_char(a.LASTTIME,'YYYY-MM-DD HH24:MI:SS'),'2000-01-01 00:00:00')LASTTIME from WaterMeter a,Gather b where a.Gather_ID=b.Gather_ID and Watermeter_ID="+wmmeterId;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			String[] PartList={};
			ps =GetResultSetBySQL(sql,PartList);
			rs = ps.executeQuery();
			if (rs.next())
			{
				meterID = rs.getString("WaterMeter_ID");
				beilv = rs.getDouble("beilv");
				xiuZheng = rs.getString("xiuzheng");
				limitPart=rs.getDouble("LIMITPART");
				LastTime = rs.getString("LastTime");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return  -2;
		}finally{
			ConnDB.release(ps, rs);
		}

		// 对meterID 进行判断，校验
		if (meterID.equals(""))
		{
			result = -1;
			return result;
		}

		DecimalFormat numDf = new DecimalFormat(".000");

		inValue = Double.parseDouble(dataValue) * beilv + Double.parseDouble(xiuZheng);
		inValue = Double.parseDouble(numDf.format(inValue));

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date readDate = df.parse(readTime);
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, 5);//容许误差5分钟 李嵘20160112
		// 插入的时间应该比当前的时间小，若大于，则报错
		if (readDate.after(nowTime.getTime()))
		{
			sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values( ? , to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? ,101)";
			String[] PartList={meterID,readTime,String.valueOf(inValue )};
			executeSQL(sql,PartList);
			result = 1;
			return result;
		}

		// 对ZValueZY进行判定（不能小于以前的最大值）
		zValueZY = -1;
		sql = "select ZValueZY,to_char(ValueTime,'YYYY-MM-DD HH24:MI:SS')ValueTime from ( select ZValueZY,ValueTime from ZWATERDATAS" + String.valueOf(meterID)
				+ " where ValueTime <= to_date( ? ,'yyyy-mm-dd hh24:mi:ss')  order by ValueTime desc) where rownum =1";
		try {
			String[] PartList={readTime};
			ps =GetResultSetBySQL(sql,PartList);
			rs = ps.executeQuery();
			if (rs.next())
			{
				zValueZY = rs.getDouble(1);
				valueTime=rs.getString(2);
				if(readDate.equals( df.parse(valueTime)))
				{//如果数据库中存在该时间段内的数据就退出
					result = -3;
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return  -2;
		}finally{
			ConnDB.release(ps, rs);
		}

		if (inValue < zValueZY)
		{
			sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values( ? ,to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? ,102)";
			String[] PartList={meterID,readTime,String.valueOf(inValue )};
			executeSQL(sql,PartList);
			result = 2;
			return result;
		}


		if ((zValueZY!=-1)&&(limitPart>0))
		{
			int days=DateDiff(valueTime,readTime);
			double addvalue=(inValue-zValueZY)/beilv/days;
			if (addvalue>limitPart)//李嵘20160422每小时突增
			{
				sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values( ? ,to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? ,104)";
				String[] PartList={meterID,readTime,String.valueOf(inValue )};
				executeSQL(sql,PartList);
				result = 4;
				return result;
			}
		}
		zValueZY = -1;
		// 对ZValueZY进行判定(不能大于以后的最小值)
		sql = "select ZValueZY,to_char(ValueTime,'YYYY-MM-DD HH24:MI:SS')ValueTime from ( select ZValueZY,ValueTime from ZWATERDATAS" + String.valueOf(meterID)
				+ " where ValueTime > to_date( ? ,'yyyy-mm-dd hh24:mi:ss')  order by ValueTime) where rownum =1";
		try {
			String[] PartList={readTime};
			ps =GetResultSetBySQL(sql,PartList);
			rs = ps.executeQuery();
			if (rs.next())
			{
				zValueZY = rs.getDouble(1);
				valueTime=rs.getString(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return  -2;
		}finally{
			ConnDB.release(ps, rs);
		}

		if ((zValueZY!=-1)&&(inValue > zValueZY))
		{
			sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values( ? ,to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? ,102)";
			String[] PartList={meterID,readTime,String.valueOf(inValue )};
			executeSQL(sql,PartList);
			result = 2;
			return result;
		}

		sql = " insert into ZWATERDATAS" + String.valueOf(meterID) + "(ValueTime,ZValueZY,CURRENTFLOW) values(to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? , ? )";
		String[] PartList={readTime,String.valueOf(inValue ),CURRENTFLOW};
		executeSQL(sql,PartList);
		updateWMLastTime(meterID, readTime,LastTime);
		result = 0;
		return result;
	}
	private void updateWMLastTime(String meterID, String readTime,String LastTime) throws SQLException, ParseException
	{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date rDate = dFormat.parse(readTime);
		Date rLastTime =  dFormat.parse(LastTime);
		if (rDate.after(rLastTime))
		{
			String sql = "update watermeter set LastTime=to_date( ? ,'yyyy-mm-dd hh24:mi:ss') where watermeter_id= ?  ";
			String[] PartList={readTime,meterID};
			executeSQL(sql,PartList);
		}
	}

	/**
	 * 向zamdatas表中插入一条数据
	 * @param ammeterId
	 * @param readTime
	 * @param dataValue
	 * @return
	 * @throws Exception
	 */
	public int insertDataIntoZamdatas(int ammeterId,String readTime, String dataValue) throws Exception{
		int result = 1;
		String meterID = "";
		double beilv = 1;
		String xiuZheng = null;
		double inValue = 0;
		String value;
		double zValueZY = 0;
		String sql = null;
		double limitPart = 0;
		String valueTime = null;
		String LastTime=null;
		// 获取meterID,倍率，修正参数
		sql = "select AmMeter_ID,nvl(beilv,1) as beilv,nvl(Xiuzheng,0) as Xiuzheng,nvl(LIMITPART,0) as LIMITPART,LastTime from AmMeter  where  AmMeter_ID="+ammeterId;
		PreparedStatement ps=null;
		ResultSet rs =null;
		try {
			String[] PartList={};
			ps=GetResultSetBySQL(sql,PartList);
			rs = ps.executeQuery();
			if (rs.next())
			{
				meterID = rs.getString(1);
				beilv = rs.getDouble(2); 
				xiuZheng = rs.getString(3);
				limitPart=rs.getDouble(4);
				LastTime = rs.getString(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return  -2;
		}finally{
			ConnDB.releasePs(ps, rs);
		}


		// 对meterID 进行判断，校验
		if (meterID.equals(""))
		{
			result = -1;
			return result;
		}
		DecimalFormat numDf = new DecimalFormat(".000");
		inValue = Double.parseDouble(dataValue) * beilv + Double.parseDouble(xiuZheng);
		value = numDf.format(inValue);
		inValue = Double.parseDouble(value);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date readDate = df.parse(readTime);
		//Date date = new Date();
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, 5);//容许误差5分钟 李嵘20160112
		// 插入的时间应该比当前的时间小，若大于，则报错
		if (readDate.after(nowTime.getTime()))
		{
			sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values( ? , to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? ,1)";

			String[] PartList={meterID,readTime,String.valueOf(inValue )};
			executeSQL(sql,PartList);

			result = 1;
			return result;
		}
		String ss="";
		// 对ZValueZY进行判定（不能小于以前的最大值）
		zValueZY = -1;
		sql = "select ZValueZY,to_char(ValueTime,'YYYY-MM-DD HH24:MI:SS')ValueTime  from ( select ZValueZY,ValueTime from ZAMDATAS"+String.valueOf(meterID)+" where ValueTime <= to_date( ? ,'yyyy-mm-dd hh24:mi:ss')  order by ValueTime desc ) where rownum =1";
		try {
			String[] PartList={readTime};
			ps=GetResultSetBySQL(sql,PartList);
			rs = ps.executeQuery();
			if (rs.next())
			{
				zValueZY = rs.getDouble(1);
				valueTime = rs.getString(2);
				ss= rs.getString(2);
				if(readDate.equals( df.parse(valueTime)))
				{//如果数据库中存在该时间段内的数据就退出
					result = -3;
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return  -2;
		}finally{
			ConnDB.releasePs(ps, rs);
		}

		if (inValue < zValueZY)//倒走
		{
			sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values( ? ,to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? ,2)";
			String[] PartList={meterID,readTime,String.valueOf(inValue )};
			executeSQL(sql,PartList);
			result =2;
			return result;
		}

		if ((zValueZY!=-1)&&(limitPart>0))
		{
			int days=DateDiff(valueTime,readTime);
			if ((inValue-zValueZY)/beilv/days>limitPart)//李嵘20160422每小时突增
			{
				sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values( ? ,to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? ,4)";
				String[] PartList={meterID,readTime,String.valueOf(inValue )};
				executeSQL(sql,PartList);
				result = 4;
				return result;
			}
		}

		zValueZY = -1;

		// 对ZValueZY进行判定(不能大于以后的最小值)
		//sql = "select ZValueZY,to_char(ValueTime,'YYYY-MM-DD HH24:MI:SS')ValueTime from ( select ZValueZY,ValueTime from ZAMDATAS" + String.valueOf(meterID)
		//		+ " where ValueTime > to_date( '"+readTime+"' ,'yyyy-mm-dd hh24:mi:ss')  order by ValueTime) where rownum =1";
		sql = "select ZValueZY,to_char(ValueTime,'YYYY-MM-DD HH24:MI:SS')ValueTime from ( select ZValueZY,ValueTime from ZAMDATAS" + String.valueOf(meterID)
				+ " where ValueTime > to_date( ? ,'yyyy-mm-dd hh24:mi:ss')  order by ValueTime) where rownum =1";

		try {
			String[] PartList={readTime};
			ps=GetResultSetBySQL(sql,PartList);
			rs = ps.executeQuery();
			if (rs.next())
			{
				zValueZY = rs.getDouble(1);
				valueTime = rs.getString(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return  -2;
		}finally{
			ConnDB.releasePs(ps, rs);
		}

		if ((zValueZY!=-1)&&(inValue > zValueZY))//倒走
		{
			sql = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values( ? ,to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? ,2)";
			String[] PartList={meterID,readTime,String.valueOf(inValue )};
			executeSQL(sql,PartList);
			result = 2;
			return result;
		}

		updateAMLastTime(meterID, readTime,LastTime);
		sql = " insert into ZAMDATAS" + String.valueOf(meterID) + "(ValueTime,ZValueZY) values(to_date( ? ,'yyyy-mm-dd hh24:mi:ss'), ? )";
		//System.out.println(sql);
		String[] PartList={readTime,String.valueOf(inValue )};
		executeSQL(sql,PartList,ss);
		result = 0;
		return result;
	}
	/**
	 * 更新Ammeter表中电表的通讯时间
	 * @param meterID
	 * @param readTime
	 * @param LastTime
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void updateAMLastTime(String meterID, String readTime,String LastTime) throws SQLException, ParseException
	{
		Date rDate = df.parse(readTime);
		Date rLastTime =  df.parse(LastTime);
		if (rDate.after(rLastTime))
		{
			String sql = "update ammeter set LastTime= ? where ammeter_id= ? ";
			String[] PartList={readTime,meterID};
			executeSQL(sql,PartList);
		}

	}

	/**
	 * 封装数据库操作
	 * @param sql
	 * @param PartValue
	 * @return
	 * @throws SQLException
	 */
	private int executeSQL(String sql,String [] PartValue) throws SQLException {
		int rt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			for(int i=0;i<PartValue.length;i++)
			{
				ps.setString(i+1, PartValue[i]);
			}
			rt = ps.executeUpdate();
			return rt;
		} catch (Exception e) {

			e.printStackTrace();
			return rt;
		} finally {
			ConnDB.release(conn, ps);
		}

	}
	private int executeSQL(String sql,String [] PartValue,String PP) throws SQLException {
		int rt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			for(int i=0;i<PartValue.length;i++)
			{
				ps.setString(i+1, PartValue[i]);
			}
			rt = ps.executeUpdate();
			return rt;
		} catch (Exception e) {
			e.printStackTrace();
			return rt;
		} finally {
			ConnDB.release(conn, ps);
		}

	}

	private PreparedStatement GetResultSetBySQL(String sql,String [] PartValue)  {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnDB.getConnection();	
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			for(int i=0;i<PartValue.length;i++)
			{
				ps.setString(i+1, PartValue[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//ConnDB.release(conn);
		}
		return ps;
	}
	/**
	 * 计算两个日期间的天数
	 * 
	 * @param fromDate
	 * 起始日期
	 * @param toDate
	 * 结束日期
	 * @return
	 * @throws ParseException
	 */
	public static int DateDiff(String fromDate, String toDate)
			throws ParseException {
		int days = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date from = df.parse(fromDate);
		Date to = df.parse(toDate);
		//days = (int) Math.abs((to.getTime() - from.getTime())
		// / (24*60 * 60 * 1000)) + 1;
		days = (int) Math.abs((to.getTime() - from.getTime())
				/ (60 * 60 * 1000)) + 1;
		return days;
	}
	/**
	 * 调整日期时间 将时间调整为大于等于输入时间点的最近一个整5分钟的点
	 * eg: 2016-10-10 10:23:56----> 2016-10-10 10:25:00
	 * @param date
	 * @return
	 */
	private Date adjustDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if(c.get(Calendar.SECOND)!=0){
			c.add(Calendar.SECOND, 60-c.get(Calendar.SECOND));
		}
		if(c.get(Calendar.MINUTE)%5!=0){
			c.set(Calendar.MINUTE, (c.get(Calendar.MINUTE)/5+1)*5);
		}
		return c.getTime();
	}
	public static void main(String[] args)
	{
		Date date = new Date();
		System.out.println(date);
		System.out.println(new FTAmCurrentManageDao().adjustDate(date));
	}
}
