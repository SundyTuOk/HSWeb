package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import net.sf.json.JSONObject;

import com.sf.energy.expert.model.AmmeterExportModel;
import com.sf.energy.expert.model.ExpertDetailExportModel;
import com.sf.energy.statistics.dao.DictionaryValueHelper;
import com.sf.energy.statistics.dao.DictionaryValueImpl;
import com.sf.energy.statistics.model.ArcAmDetailData;
import com.sf.energy.util.ConnDB;

public class DataExportDao
{
	private int totalCount = 0;

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public int getCount(String sql){
		int count = 0;
		String sql1 = "select count(*) from("+sql+")";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		//System.out.println("count:"+sql1);
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql1);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return count;
	}


	public List<ExpertDetailExportModel> queryExportDataInfo(int areaId,int archId,String start, String end, int type){
		List<ExpertDetailExportModel> result_List = new LinkedList<ExpertDetailExportModel>();
		String typeName = (type==1)?"PARTMENT":"PRICE";
		String areaCondition =" ";
		if(type==1){
			areaCondition+=" 	WHERE PARTMENT_ID<>0";//部门要排除 ID为0
		}else{
			areaCondition+=" 	WHERE PRICE_ID<>0 ";//电费类型要排除 电费ID为0的 
		}
		if(archId!=-1 && areaId!=-1){
			areaCondition+=" AND ARCHITECTURE_ID="+archId;

		}else if(archId==-1 && areaId!=-1){
			areaCondition+=" AND AREA_ID="+areaId;
		}
		areaCondition+=" AND ISCOMPUTATION = 1";//且只统计纳入计量的表
		String sql="";
		if(type==1&&archId==-1 && areaId==-1){//选择部门且查询全校时
			sql = "SELECT * FROM(SELECT "
					+" PP.PARTMENT_ID,"
					+" NVL (PP.ZGROSS ,- 1) ZGROSS,"
					+" PR.PARTMENT_NAME"
					+" FROM"
					+"	("
					+"		SELECT"
					+"			PARTMENT_ID,"
					+"			SUM (ZGROSS) ZGROSS"
					+"		FROM"
					+"			T_PARTMENTDAYAM"
					+"		WHERE"
					+"			TO_DATE ("
					+"				SELECTYEAR || '-' || SELECTMONTH || '-' || SELECTDAY,"
					+"			'YYYY-MM-DD'"
					+" 		) BETWEEN TO_DATE ('"+start+"', 'YYYY-MM-DD')"
					+"		AND TO_DATE ('"+end+"', 'YYYY-MM-DD')"
					+"		GROUP BY"
					+"			PARTMENT_ID"
					+" ) PP"
					+" LEFT JOIN PARTMENT PR ON PP.PARTMENT_ID = PR.PARTMENT_ID)"
					+ " ORDER BY PARTMENT_ID ";
		}else{
			sql = "SELECT * FROM"
					+ "(SELECT PP."+typeName+"_ID,NVL(PP.ZGROSS,-1) ZGROSS,PR."+typeName+"_NAME FROM (SELECT "+typeName+"_ID,SUM(ZGROSS) ZGROSS FROM("
					+" SELECT "
					+"	A.AMMETER_ID,"
					+"	A.PARTMENT_ID,"
					+"	A.PRICE_ID,"
					+"	(SELECT SUM(ZGROSS) FROM T_DAYAM WHERE TO_DATE(SELECTYEAR||'-'||SELECTMONTH||'-'||SELECTDAY, 'YYYY-MM-DD') BETWEEN TO_DATE('"+start+"', 'YYYY-MM-DD') AND TO_DATE('"+end+"', 'YYYY-MM-DD') AND AMMETER_ID = A.AMMETER_ID) ZGROSS"
					+" FROM"
					+"	(SELECT AMMETER_ID,PARTMENT_ID,PRICE_ID FROM AMMETER "+areaCondition+" ) A) GROUP BY "+typeName+"_ID )PP"
					+" LEFT JOIN "+typeName+" PR ON PP."+typeName+"_ID = PR."+typeName+"_ID)"
							+ " ORDER BY "+typeName+"_ID  ";
		}
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		ExpertDetailExportModel aadd = null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				aadd = new ExpertDetailExportModel();
				aadd.setType(type);
				aadd.setType_Id(rs.getString(typeName+"_ID"));
				aadd.setType_Name(rs.getString(typeName+"_NAME"));
				aadd.setZgross(rs.getFloat("ZGROSS"));
				result_List.add(aadd);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return result_List;	
	}

	public List<ExpertDetailExportModel> queryShowDataInfo(int areaId,int archId,int thePageCount, int thePageIndex,
			String start, String end, int type){

		List<ExpertDetailExportModel> result_List = new LinkedList<ExpertDetailExportModel>();
		int startCount = thePageCount*thePageIndex;
		int endCount = thePageCount*(thePageIndex+1);
		String typeName = (type==1)?"PARTMENT":"PRICE";
		String areaCondition =" ";
		if(type==1){
			areaCondition+=" 	WHERE PARTMENT_ID<>0";//部门要排除 华中师范大学
		}else{
			areaCondition+=" 	WHERE PRICE_ID<>0";//电费类型要排除 电费ID为0的
		}
		if(archId!=-1 && areaId!=-1){
			areaCondition+=" AND ARCHITECTURE_ID="+archId;

		}else if(archId==-1 && areaId!=-1){
			areaCondition+=" AND AREA_ID="+areaId;
		}
		areaCondition+=" AND ISCOMPUTATION = 1";//且只统计纳入计量的表
		String sql = "";
		if(type==1&&archId==-1 && areaId==-1){
			sql = "SELECT * FROM(SELECT "
					+" PP.PARTMENT_ID,"
					+" NVL (PP.ZGROSS ,- 1) ZGROSS,"
					+" PR.PARTMENT_NAME"
					+" FROM"
					+"	("
					+"		SELECT"
					+"			PARTMENT_ID,"
					+"			SUM (ZGROSS) ZGROSS"
					+"		FROM"
					+"			T_PARTMENTDAYAM"
					+"		WHERE"
					+"			TO_DATE ("
					+"				SELECTYEAR || '-' || SELECTMONTH || '-' || SELECTDAY,"
					+"			'YYYY-MM-DD'"
					+" 		) BETWEEN TO_DATE ('"+start+"', 'YYYY-MM-DD')"
					+"		AND TO_DATE ('"+end+"', 'YYYY-MM-DD')"
					+"		GROUP BY"
					+"			PARTMENT_ID"
					+" ) PP"
					+" LEFT JOIN PARTMENT PR ON PP.PARTMENT_ID = PR.PARTMENT_ID)"
					+ " ORDER BY PARTMENT_ID ";
			setTotalCount(getCount(sql));
		}else{
			sql = "SELECT * FROM"
					+ "(SELECT PP."+typeName+"_ID,NVL(PP.ZGROSS,-1) ZGROSS,PR."+typeName+"_NAME FROM (SELECT "+typeName+"_ID,SUM(ZGROSS) ZGROSS FROM("
					+" SELECT "
					+"	A.AMMETER_ID,"
					+"	A.PARTMENT_ID,"
					+"	A.PRICE_ID,"
					+"	(SELECT SUM(ZGROSS) FROM T_DAYAM WHERE TO_DATE(SELECTYEAR||'-'||SELECTMONTH||'-'||SELECTDAY, 'YYYY-MM-DD') BETWEEN TO_DATE('"+start+"', 'YYYY-MM-DD') AND TO_DATE('"+end+"', 'YYYY-MM-DD') AND AMMETER_ID = A.AMMETER_ID) ZGROSS"
					+" FROM"
					+"	(SELECT AMMETER_ID,PARTMENT_ID,PRICE_ID FROM AMMETER "+areaCondition+" ) A) GROUP BY "+typeName+"_ID )PP"
					+" LEFT JOIN "+typeName+" PR ON PP."+typeName+"_ID = PR."+typeName+"_ID)"
							+ " ORDER BY "+typeName+"_ID  ";
			String countsql ="SELECT PP."+typeName+"_ID,PR."+typeName+"_NAME FROM (SELECT distinct("+typeName+"_ID) "+typeName+"_ID FROM("
					+" SELECT "
					+"	A.AMMETER_ID,"
					+"	A.PARTMENT_ID,"
					+"	A.PRICE_ID"
					+" FROM"
					+"	(SELECT AMMETER_ID,PARTMENT_ID,PRICE_ID FROM AMMETER "+areaCondition+" ) A)  )PP"
					+" LEFT JOIN "+typeName+" PR ON PP."+typeName+"_ID = PR."+typeName+"_ID";;

					setTotalCount(getCount(countsql));
		}
		sql = "select * from(select ROWNUM rno ,b.* from(" +sql+ " )B WHERE ROWNUM <="+endCount+")where rno > "+startCount;
		////System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		ExpertDetailExportModel aadd = null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				aadd = new ExpertDetailExportModel();
				aadd.setType(type);
				aadd.setType_Id(rs.getString(typeName+"_ID"));
				aadd.setType_Name(rs.getString(typeName+"_NAME"));
				aadd.setZgross(rs.getFloat("ZGROSS"));
				result_List.add(aadd);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return result_List;	
	}

	/**
	 * 
	 * @param areaId
	 * @param archId
	 * @param thePageCount
	 * @param thePageIndex
	 * @param start
	 * @param end
	 * @param order
	 * @param userID
	 * @param price_id 
	 * @param part_id
	 * @return
	 */
	public List<ArcAmDetailData> queryShowAmmeterInfo(int areaId,int archId,int thePageCount, int thePageIndex,
			String start, String end, String order,int userID,int price_id,int part_id){

		List<ArcAmDetailData> result_List = null;
		int startCount = thePageCount*thePageIndex;
		int endCount = thePageCount*(thePageIndex+1);
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量
		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();
		String condition = "";
		if(price_id!=0){
			condition+=" and A.price_id ="+price_id;
		}
		if(part_id!=0){
			condition+= " and A.PARTMENT_ID ="+part_id;
		}
		String sql = "";
		if(archId!=-1 && areaId!=-1){
			sql =	 "select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
					+ "	UseAmStyle ,area_name ,NVL(price_name, '-') price_name"
					+ "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID  and a.Architecture_ID ="+archId
					+ "				left join	area on a.area_id = area.area_id  	 "
					+ "				LEFT JOIN PRICE ON A.price_id = PRICE.PRICE_ID"
					+ " 	where a.PARTMENT_ID in(select Partment_ID from PARTMENT) "+condition+" ";


		}else if(archId==-1 && areaId!=-1){
			sql =	 "select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
					+ "	UseAmStyle ,area_name,NVL(price_name, '-') price_name "
					+ "	from  " + "	Ammeter a  " + "	left join  Partment on Partment.Partment_ID = a.Partment_ID "
					+ "				left join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
					+ "				LEFT JOIN PRICE ON A.price_id = PRICE.PRICE_ID"
					+ "				inner join	area on a.area_id = area.area_id  and a.area_id ="+areaId+"	 "
					+ " where a.PARTMENT_ID in(select Partment_ID from PARTMENT) "+condition+" " ;

		}else{
			sql =	
					"SELECT T.Ammeter_ID,T.Architecture_ID,T.Ammeter_name,T.isComputation,T.Architecture_NAME"
							+ ",NVL(T.Partment_Name,'-')Partment_Name,T.floor,T.Ammeter_Num,T.Ammeter_485address,T.BeiLv,T.UseAmStyle"
							+ ",NVL(T.area_name,'-')area_name,price_name"
							+ " FROM (SELECT  a.Ammeter_ID,Architecture_ID,Ammeter_name,isComputation, (SELECT Architecture_NAME FROM"
							+ " ARCHITECTURE WHERE a.Architecture_ID = Architecture.Architecture_ID  )Architecture_NAME, "
							+ "(SELECT NVL(Partment_Name,'-') FROM PARTMENT where Partment.Partment_ID = a.Partment_ID   )Partment_Name, "
							+ "floor,Ammeter_Num,Ammeter_485address,BeiLv,UseAmStyle, "
							+ "(SELECT NVL(area_name,'-') FROM area where a.area_id = area.area_id)area_name, "
							+ "(select NVL(price_name, '-') from PRICE where a.PRICE_ID = PRICE.PRICE_ID) price_name "
							//+ "FROM " + "Ammeter a where a.PARTMENT_ID in(select Partment_ID from OPRPARTMENT_LIST where USERS_ID="+userID+") "+condition+")T ";
							+ "FROM " + "Ammeter a where a.PARTMENT_ID in(select Partment_ID from PARTMENT) "+condition+")T ";
		}
		//System.out.println(sql);
		setTotalCount(getCount(sql));
		if(getTotalCount()<=0){
			return new ArrayList<ArcAmDetailData>();
		}
		sql = "select * from(select ROWNUM rno ,b.* from(" +sql + order + " )B WHERE ROWNUM <="+endCount+")where rno > "+startCount;
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		ArcAmDetailData aadd = null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if(result_List==null){
					result_List = new ArrayList<ArcAmDetailData>(thePageCount);
				}
				aadd = new ArcAmDetailData();
				int ammeterid = rs.getInt("Ammeter_ID");
				aadd.setAmmeter_ID(ammeterid);
				aadd.setAmmeter_name(rs.getString("Ammeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));
				aadd.setPrice_name(rs.getString("price_name"));
				String maxtime = null;
				String mintime = null;
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) AmValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC) WHERE ROWNUM = 1)"
						+")))";
				//System.out.println(sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					ps1 = conn.prepareStatement(sqllString);

					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if (rs1.getString("max_valuetime") != null)
						{
							maxtime = rs1.getString("max_valuetime");
						} else
						{
							maxtime = "";
						}
						if (rs1.getString("min_valuetime") != null)
						{
							mintime = rs1.getString("min_valuetime");
						} else
						{
							mintime = "";
						}
						aadd.setStarttime(mintime);
						aadd.setEndtime(maxtime);
						aadd.setStartvalue(rs1.getFloat("minzvaluezy"));
						aadd.setEndvalue(rs1.getFloat("maxzvaluezy"));
						aadd.setTotalvalue(rs1.getFloat("AmValue"));
					}
					aadd.setAmmeter_num(rs.getString("Ammeter_num"));
					String Ammeter_num = aadd.getAmmeter_num();
					String Fenxiang = 'A' + "";
					if (Ammeter_num == null || Ammeter_num.length() < 16)
					{
						Fenxiang = 'A' + "";
					} else
					{
						Fenxiang = Ammeter_num.toCharArray()[13] + "";
					}

					aadd.setAmmeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 7));
					style = rs.getString("UseAmStyle");
					aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
					aadd.setBeiLv(rs.getInt("BeiLv"));
					aadd.setAmmeterNo(rs.getString("AmMeter_485Address"));
					count = rs.getInt("isComputation");
					if (count == 1)
					{
						name = "是";
						aadd.setIstotalvalue(rs1.getFloat("AmValue"));
					} else
					{
						name = "否";
						aadd.setNototalvalue(rs1.getFloat("AmValue"));
					}
					aadd.setIsTotal(name);
					result_List.add(aadd);
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(ps1,rs1);
				}
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return result_List;	
	}


	public List<ArcAmDetailData> queryExportAmmeterInfo(int areaId,int archId, String start, String end,int userID,int price_id,int part_id){

		List<ArcAmDetailData> result_List = null;
		ArcAmDetailData aadd = null;
		String style = null; // 性质
		int count = -1; // 纳入计量
		String name = null; // 是否纳入计量
		String startime = start + " 00:00:00";
		String endtime = end + " 23:59:59";
		DictionaryValueHelper dictionaryValueHelper = new DictionaryValueImpl();
		String condition = "";
		if(price_id!=0){
			condition+=" and A.price_id ="+price_id;
		}
		if(part_id!=0){
			condition+= " and A.PARTMENT_ID ="+part_id;
		}
		String sql = "";
		if(archId!=-1 && areaId!=-1){
			sql ="select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
					+ "	UseAmStyle ,area_name,NVL(price_name, '-') price_name " 
					+ "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID  and a.Architecture_ID ="+archId
					+ "				LEFT JOIN PRICE ON A.price_id = PRICE.PRICE_ID"
					+ "				left join	area on a.area_id = area.area_id  where a.PARTMENT_ID in(select Partment_ID from PARTMENT) "+condition+" order by Architecture_ID ,ammeter_name " ;
		}else if(archId==-1 && areaId!=-1){
			sql ="select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
					+ "	UseAmStyle ,area_name,NVL(price_name, '-') price_name " 
					+ "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID  and a.Area_ID ="+areaId
					+ "				LEFT JOIN PRICE ON A.price_id = PRICE.PRICE_ID"
					+ "				left join	area on a.area_id = area.area_id  where a.PARTMENT_ID in(select Partment_ID from PARTMENT) "+condition+" order by Architecture_ID ,ammeter_name" ;
		}else{
			sql ="select distinct " + "	a.Ammeter_ID,a.Architecture_ID,a.Ammeter_name,a.isComputation, "
					+ "	Architecture_NAME,nvl(Partment_Name,'-')Partment_Name,floor,  " + "	Ammeter_Num, Ammeter_485address,BeiLv, "
					+ "	UseAmStyle ,area_name ,NVL(price_name, '-') price_name" 
					+ "	from  " + "	Ammeter a  " + "				left join  Partment on Partment.Partment_ID = a.Partment_ID "
					+ "				inner join Architecture on a.Architecture_ID = Architecture.Architecture_ID "
					+ "				LEFT JOIN PRICE ON A.price_id = PRICE.PRICE_ID"
					+ "				left join	area on a.area_id = area.area_id  where a.PARTMENT_ID in(select Partment_ID from PARTMENT) "+condition+" order by Architecture_ID ,ammeter_name" ;
		}
		////System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			result_List = new LinkedList<ArcAmDetailData>();
			while (rs.next())
			{
				aadd = new ArcAmDetailData();
				int ammeterid = rs.getInt("Ammeter_ID");
				aadd.setAmmeter_ID(ammeterid);
				aadd.setAmmeter_name(rs.getString("Ammeter_name"));
				aadd.setFloor(rs.getInt("floor"));
				aadd.setArea_name(rs.getString("area_name"));
				aadd.setArchitecture_Name(rs.getString("Architecture_NAME"));
				aadd.setPartmentName(rs.getString("Partment_Name"));
				aadd.setArchitecture_ID(rs.getInt("Architecture_ID"));
				aadd.setPrice_name(rs.getString("price_name"));
				String maxtime = null;
				String mintime = null;
				String sqllString ="SELECT MaxZVALUEZY, Max_VALUETIME,MinZVALUEZY, Min_VALUETIME, (MaxZVALUEZY - MinZVALUEZY) AmValue FROM ("
						+"SELECT NVL (MaxZVALUEZY, MinZVALUEZY) MaxZVALUEZY, Max_VALUETIME, NVL (MinZVALUEZY, MindZVALUEZY) MinZVALUEZY, NVL ( Min_VALUETIME, Mind_VALUETIME ) Min_VALUETIME FROM ("
						+"SELECT MaxZVALUEZY, Max_VALUETIME, MinZVALUEZY, Min_VALUETIME, MindZVALUEZY, Mind_VALUETIME FROM ("
						+"SELECT nvl(MAX (ZVALUEZY),NULL) MaxZVALUEZY, NVL (TO_CHAR (MAX (VALUETIME),'yyyy-mm-dd hh24:mi:ss'),'"+endtime+"') Max_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC ) WHERE ROWNUM = 1))," 
						+"("
						+"SELECT NVL (MAX(ZVALUEZY), NULL) MinZVALUEZY, NVL ( TO_CHAR ( MAX (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), NULL ) Min_VALUETIME FROM ("
						+"SELECT * FROM(SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME < TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME DESC )WHERE ROWNUM = 1) "
						+"),("
						+"SELECT NVL (MIN(ZVALUEZY), 0) MindZVALUEZY, NVL ( TO_CHAR ( MIN (VALUETIME), 'yyyy-mm-dd hh24:mi:ss' ), '"+startime+"' ) Mind_VALUETIME FROM ("
						+"SELECT * FROM (SELECT * FROM ZAMDATAS"+ammeterid+" WHERE VALUETIME >= TO_DATE ( '"+startime+"', 'yyyy-mm-dd hh24:mi:ss' ) AND VALUETIME <= TO_DATE ( '"+endtime+"', 'yyyy-mm-dd hh24:mi:ss' ) ORDER BY VALUETIME ASC)WHERE ROWNUM = 1)"
						+")))";
				//System.out.println(sqllString);
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
					ps1 = conn.prepareStatement(sqllString,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						if (rs1.getString("max_valuetime") != null)
						{
							maxtime = rs1.getString("max_valuetime");
						} else
						{
							maxtime = "";
						}
						if (rs1.getString("min_valuetime") != null)
						{
							mintime = rs1.getString("min_valuetime");
						} else
						{
							mintime = "";
						}
						aadd.setStarttime(mintime);
						aadd.setEndtime(maxtime);
						aadd.setStartvalue(rs1.getFloat("minzvaluezy"));
						aadd.setEndvalue(rs1.getFloat("maxzvaluezy"));
						aadd.setTotalvalue(rs1.getFloat("AmValue"));
						aadd.setAmmeter_num(rs.getString("Ammeter_num"));
						String Ammeter_num = aadd.getAmmeter_num();
						String Fenxiang = 'A' + "";
						if (Ammeter_num == null || Ammeter_num.length() < 16)
						{
							Fenxiang = 'A' + "";
						} else
						{
							Fenxiang = Ammeter_num.toCharArray()[13] + "";
						}
						aadd.setAmmeter_num(dictionaryValueHelper.queryValueByNum(Fenxiang, 7));
						style = rs.getString("UseAmStyle");
						aadd.setStyle(dictionaryValueHelper.queryValueByNum(style, 26));
						aadd.setBeiLv(rs.getInt("BeiLv"));
						aadd.setAmmeterNo(rs.getString("AmMeter_485Address"));
						count = rs.getInt("isComputation");
						if (count == 1)
						{
							name = "是";
							aadd.setIstotalvalue(rs1.getFloat("AmValue"));
						} else
						{
							name = "否";
							aadd.setNototalvalue(rs1.getFloat("AmValue"));
						}
						aadd.setIsTotal(name);
						result_List.add(aadd);
					}
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{

					ConnDB.release(ps1, rs1);
				}
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return result_List;	
	}

	public JSONArray queryPriceInfo(){
		JSONArray result_list = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select price_id,price_name from price where price_style = 'E'";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				JSONObject object = new JSONObject();
				object.put("price_id", rs.getInt("price_id"));
				object.put("price_name", rs.getString("price_name"));
				result_list.put(object);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn,ps,rs);
		}	
		return result_list;	
	}
}
