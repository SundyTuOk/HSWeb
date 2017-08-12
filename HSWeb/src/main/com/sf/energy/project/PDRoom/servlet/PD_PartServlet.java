package com.sf.energy.project.PDRoom.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.sf.energy.project.PDRoom.dao.PD_PartDao;
import com.sf.energy.project.PDRoom.dao.PD_PartInfo01Dao;
import com.sf.energy.project.PDRoom.dao.PD_PartInfo02Dao;
import com.sf.energy.project.PDRoom.dao.PD_PartInfo03Dao;
import com.sf.energy.project.PDRoom.model.PD_PartInfo01Model;
import com.sf.energy.project.PDRoom.model.PD_PartInfo02Model;
import com.sf.energy.project.PDRoom.model.PD_PartInfo03Model;
import com.sf.energy.project.PDRoom.model.PD_PartModel;

public class PD_PartServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
       String flag=req.getParameter("flag");
       
		
		if("edit".equals(flag))
		{
			try {
				editPD_Part(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if("add".equals(flag))
		{
			try {
				addPD_Part(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("delete".equals(flag))
		{
			try
			{
				deletePD_Part(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("deletearray".equals(flag))
		{
			
			try
			{
				deletePD_PartArray(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("selectPD".equals(flag))
		{
			
			try {
				selectPD_Part(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("queryPD".equals(flag))
		{
			try {
				queryPD_Part(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 else if("loaddate".equals(flag))
		 {
			 try {
				loadDataPD_PartInfo01(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	/**
	 * 加载数据
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	
	private void loadDataPD_PartInfo01(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("PD_Part_01PageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("PD_Part_01PageIndex"));
		int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
	
		 PD_PartDao partDao01=new PD_PartDao();
		 List<PD_PartModel> pd_Part01List=new ArrayList<PD_PartModel>();
		 pd_Part01List=partDao01.queryPD_Part(thePageCount,thePageIndex,Pd_Parent);
	
		 //JSONArray json = new JSONArray();
		 JSONObject json =new JSONObject();
		 json.put("totalCount", partDao01.getTotalCount01());
		// json.add(jototal);
		 
		 JSONArray  jo=JSONArray.fromObject(pd_Part01List);
		
		 json.put("jo",jo);
		 
		 resp.setContentType("text/html;charset=utf-8");  

		 PrintWriter pw = resp.getWriter();  
		 pw.print(json.toString());  
		 pw.flush();
		 pw.close();
	}
	
	/**
	 * 查询到相应的表
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void queryPD_Part(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int id=0;
		id=Integer.parseInt(req.getParameter("id"));
		String PartStyle_ID=req.getParameter("PartStyle_ID");
		
		if("01".equals(PartStyle_ID))
		{
			PD_PartModel partModel=new PD_PartModel();
			PD_PartInfo01Dao partInfo01Dao=new PD_PartInfo01Dao();
			partModel=partInfo01Dao.queryPD_Part(id);
			
		     int Part_ID=partModel.getPart_ID();		   
		     String PD_Part01ShowState=partModel.getState();
		     String PD_Part01ShowNum=partModel.getPart_Num();
		     String PD_Part01ShowName=partModel.getPart_Name();
		     String PD_Part01ShowSCCJ=partModel.getPart_SCCJ();
		     String PD_Part01ShowTyrq=partModel.getPart_TYRQ();
		     String PD_Part01ShowRemark=partModel.getPart_Remark();
		     String PD_Part01ShowXmlName=partModel.getXMLNAME();
		     String PD_Part01ShowDylevel=partModel.getDYLevel();
		     String PD_Part01ShowAddress=partModel.getAddress();
		     String menu_parent_name=partModel.getPart_Parent_name();
		     int menu_parent=partModel.getPart_Parent();
		    
		     
			
			//发送给前台
			 resp.setContentType("text/html;charset=utf-8");  
			 JSONObject json = new JSONObject(); 
			 json.put("id",Part_ID);
			 json.put("PartStyle","配电房");
			 json.put("PartStyle_ID", "01");
			 json.put("PD_Part01ShowState",PD_Part01ShowState);
			 json.put("PD_Part01ShowNum",PD_Part01ShowNum);
			 json.put("PD_Part01ShowName",PD_Part01ShowName);
			 json.put("PD_Part01ShowSCCJ",PD_Part01ShowSCCJ);
			 json.put("PD_Part01ShowTyrq",PD_Part01ShowTyrq);
			 json.put("PD_Part01ShowRemark",PD_Part01ShowRemark);
			 json.put("PD_Part01ShowXmlName",PD_Part01ShowXmlName);
			 json.put("PD_Part01ShowDylevel",PD_Part01ShowDylevel);
			 json.put("PD_Part01ShowAddress",PD_Part01ShowAddress);
			 
			 json.put("menu_parent_name",menu_parent_name);
			 json.put("menu_parent",menu_parent);
			
			 
			 PrintWriter pw = resp.getWriter();   
		     pw.print(json.toString());  
			
			
			
			
			
			
		}
		else if("02".equals(PartStyle_ID))
		{
			
			PD_PartInfo02Dao partInfo02Dao=new PD_PartInfo02Dao();
			PD_PartInfo02Model partInfo02Model=new PD_PartInfo02Model();
			
			partInfo02Model=partInfo02Dao.queryPD_PartInfo02(id);
			 id=partInfo02Model.getPart_ID();
			
			String PD_Part02ShowState=partInfo02Model.getState();
			String PD_Part02ShowNum=partInfo02Model.getPart_Num();
			String PD_Part02ShowName=partInfo02Model.getPart_Name();
			String PD_Part02ShowSCCJ=partInfo02Model.getPart_SCCJ();
			String PD_Part02ShowTYRQ=partInfo02Model.getPart_TYRQ();
			String PD_Part02ShowRemark=partInfo02Model.getPart_Remark();
			String PD_Part02_parent_nameshow=partInfo02Model.getPart_Parent_name();
			int PD_Part02_parent_idshow=partInfo02Model.getPart_Parent();
			//String PD_Part02_parent_idsshow=partInfo02Model.get
			String PD_Part02ShowMan=partInfo02Model.getMan();
			String PD_Part02ShowManPhone=partInfo02Model.getManPhone();
			String PD_Part02ShowGZPL=partInfo02Model.getGZPL();
			String PD_Part02ShowEDGL=partInfo02Model.getEDGL();
			String PD_Part02ShowEDDY=partInfo02Model.getEDDY();
			String PD_Part02ShowDYB=partInfo02Model.getDYB();
			String PD_Part02ShowKZDL=partInfo02Model.getKZDL();
			String PD_Part02ShowKZSH=partInfo02Model.getKZSH();
			String PD_Part02ShowXL=partInfo02Model.getXL();
			String PD_Part02ShowDYSX=partInfo02Model.getDYSX();
			String PD_Part02ShowDYXX=partInfo02Model.getDYXX();
			String PD_Part02ShowXEDL=partInfo02Model.getXEDL();
			String PD_Part02ShowXEWG=partInfo02Model.getXEWG();
			String PD_Part02ShowGLYS=partInfo02Model.getGLYS();

			resp.setContentType("text/html;charset=utf-8");  
			 JSONObject json = new JSONObject(); 
			  
			 json.put("id",id);
			 json.put("PartStyle","变压器");
			 json.put("PartStyle_ID","02");
			 json.put("PD_Part02ShowState",PD_Part02ShowState);
			 json.put("PD_Part02ShowNum",PD_Part02ShowNum);
			 json.put("PD_Part02ShowName",PD_Part02ShowName);
			 json.put("PD_Part02ShowSCCJ",PD_Part02ShowSCCJ);
			 json.put("PD_Part02ShowTYRQ",PD_Part02ShowTYRQ);
			 json.put("PD_Part02ShowRemark",PD_Part02ShowRemark);
			 json.put("PD_Part02_parent_nameshow",PD_Part02_parent_nameshow);
			 json.put("PD_Part02_parent_idshow",PD_Part02_parent_idshow);
			 //json.put("PD_Part02_parent_idsshow",PD_Part02_parent_idsshow);
			 json.put("PD_Part02ShowMan",PD_Part02ShowMan);
			 json.put("PD_Part02ShowManPhone",PD_Part02ShowManPhone);
			 json.put("PD_Part02ShowGZPL",PD_Part02ShowGZPL);
			 json.put("PD_Part02ShowEDGL",PD_Part02ShowEDGL);
			 json.put("PD_Part02ShowEDDY",PD_Part02ShowEDDY);
			 json.put("PD_Part02ShowDYB",PD_Part02ShowDYB);
			 json.put("PD_Part02ShowKZDL",PD_Part02ShowKZDL);
			 json.put("PD_Part02ShowKZSH",PD_Part02ShowKZSH);
			 json.put("PD_Part02ShowXL",PD_Part02ShowXL);
			 json.put("PD_Part02ShowDYSX",PD_Part02ShowDYSX);
			 json.put("PD_Part02ShowDYXX",PD_Part02ShowDYXX);
			 json.put("PD_Part02ShowXEDL",PD_Part02ShowXEDL);
			 json.put("PD_Part02ShowXEWG",PD_Part02ShowXEWG);
			 json.put("PD_Part02ShowGLYS",PD_Part02ShowGLYS);
			
	 
			 PrintWriter pw = resp.getWriter();   
		     pw.print(json.toString());  
		}
		else if("03".equals(PartStyle_ID))
		{
			
			PD_PartInfo03Dao partInfo03Dao=new PD_PartInfo03Dao();
			PD_PartInfo03Model partInfo03Model=new PD_PartInfo03Model();
			partInfo03Model=partInfo03Dao.queryPD_PartInfo03(id);
			
			
					
			 id=partInfo03Model.getPart_ID();			
			
			String PD_Part03ShowNum=partInfo03Model.getPart_Num();
		    String PD_Part03ShowName=partInfo03Model.getPart_Name();
		    String PD_Part03ShowState=partInfo03Model.getState();

		    String PD_Part03_parent_nameShow=partInfo03Model.getPart_Parent_name();
		    int PD_Part03_parent_idedit=partInfo03Model.getPart_Parent();
		    
		    
		    String PD_Part03ShowSCCJ = partInfo03Model.getPart_SCCJ();
		    String PD_Part03ShowTYRQ=partInfo03Model.getPart_TYRQ();
		    String PD_Part03ShowRemark=partInfo03Model.getPart_Remark();
		    double PD_Part03ShowJiemian=partInfo03Model.getJiemian();

		    String PD_Part03ShowStartAddress=partInfo03Model.getStartAddress();
		    String PD_Part03ShowEndAddress=partInfo03Model.getEndAddress();
		    int PD_Part03ShowLength=partInfo03Model.getLength();

		    int PD_Part03ShowJLLX=partInfo03Model.getJLLX();
		    String PD_Part03ShowJLID=partInfo03Model.getJLID();
		    double PD_Part03ShowGZPL=partInfo03Model.getGZPL();

		    double PD_Part03ShowEDDY=partInfo03Model.getEDDY();
		    double PD_Part03ShowKZDL=partInfo03Model.getKZDL();
		    double PD_Part03ShowKZSH=partInfo03Model.getKZSH();

		    double PD_Part03ShowXL=partInfo03Model.getXL();
		    double PD_Part03ShowGLYS=partInfo03Model.getGLYS();
		    double PD_Part03ShowDYB=partInfo03Model.getDYB();

		    double PD_Part03ShowAEDGL=partInfo03Model.getAEDGL();
		    double PD_Part03ShowBEDGL=partInfo03Model.getBEDGL();
		    double PD_Part03ShowCEDGL=partInfo03Model.getCEDGL();
		    double PD_Part03ShowZEDGL=partInfo03Model.getZEDGL();

		    double PD_Part03ShowAXEWG=partInfo03Model.getAXEWG();
		    double PD_Part03ShowBXEWG=partInfo03Model.getBXEWG();
		    double PD_Part03ShowCXEWG=partInfo03Model.getCXEWG();
		    double PD_Part03ShowZXEWG=partInfo03Model.getZXEWG();

		    double PD_Part03ISMULINE = partInfo03Model.getISMULINE();

		    double PD_Part03ShowAXEDL=partInfo03Model.getAXEDL();
		    double PD_Part03ShowBXEDL=partInfo03Model.getBXEDL();
		    double PD_Part03ShowCXEDL=partInfo03Model.getCXEDL();
		    double PD_Part03ShowXEDLL=partInfo03Model.getXEDLL();
		    
		    double PD_Part03ShowADYSX=partInfo03Model.getADYSX();
		    double PD_Part03ShowBDYSX=partInfo03Model.getBDYSX();
		    double PD_Part03ShowCDYSX=partInfo03Model.getCDYSX();
		    double PD_Part03ShowDYSXL=partInfo03Model.getDYSXL();
		    
		    double PD_Part03ShowADYXX=partInfo03Model.getADYXX();
		    double PD_Part03ShowBDYXX=partInfo03Model.getBDYXX();
		    double PD_Part03ShowCDYXX=partInfo03Model.getCDYXX();
		    double PD_Part03ShowDYXXL=partInfo03Model.getDYXXL();
		   
		    
		    
		    
		    resp.setContentType("text/html;charset=utf-8");  
			JSONObject json = new JSONObject(); 
			  
			 json.put("id",id);
			 json.put("PartStyle","回路");
			 json.put("PartStyle_ID","03");
			 json.put("PD_Part03ShowNum",PD_Part03ShowNum);
			 json.put("PD_Part03ShowName",PD_Part03ShowName);
			 json.put("PD_Part03ShowState",PD_Part03ShowState);
			 json.put("PD_Part03_parent_nameShow",PD_Part03_parent_nameShow);
			 json.put("PD_Part03ShowSCCJ",PD_Part03ShowSCCJ);
			 json.put("PD_Part03ShowTYRQ",PD_Part03ShowTYRQ);
			 json.put("PD_Part03ShowRemark",PD_Part03ShowRemark);
			 json.put("PD_Part03ShowJiemian",PD_Part03ShowJiemian);
			 //json.put("PD_Part02_parent_idsshow",PD_Part02_parent_idsshow);
			 json.put("PD_Part03ShowStartAddress",PD_Part03ShowStartAddress);
			 json.put("PD_Part03ShowEndAddress",PD_Part03ShowEndAddress);
			 json.put("PD_Part03ShowLength",PD_Part03ShowLength);
			 json.put("PD_Part03ShowJLLX",PD_Part03ShowJLLX);
			 json.put("PD_Part03ShowJLID",PD_Part03ShowJLID);
			 json.put("PD_Part03ShowGZPL",PD_Part03ShowGZPL);
			 json.put("PD_Part03ShowEDDY",PD_Part03ShowEDDY);
			 json.put("PD_Part03ShowKZDL",PD_Part03ShowKZDL);
			 json.put("PD_Part03ShowKZSH",PD_Part03ShowKZSH);
			 json.put("PD_Part03ShowXL",PD_Part03ShowXL);
			 json.put("PD_Part03ShowGLYS",PD_Part03ShowGLYS);
			 json.put("PD_Part03ShowDYB",PD_Part03ShowDYB);
			 json.put("PD_Part03ShowAEDGL",PD_Part03ShowAEDGL);
			 json.put("PD_Part03ShowBEDGL",PD_Part03ShowBEDGL);
			 json.put("PD_Part03ShowCEDGL",PD_Part03ShowCEDGL);
			 json.put("PD_Part03ShowZEDGL",PD_Part03ShowZEDGL);
			 json.put("PD_Part03ShowAXEWG",PD_Part03ShowAXEWG);
			 json.put("PD_Part03ShowBXEWG",PD_Part03ShowBXEWG);
			 json.put("PD_Part03ShowCXEWG",PD_Part03ShowCXEWG);
			 json.put("PD_Part03ShowZXEWG",PD_Part03ShowZXEWG);
			 json.put("PD_Part03ShowAXEDL",PD_Part03ShowAXEDL);
			 json.put("PD_Part03ShowBXEDL",PD_Part03ShowBXEDL);
			 json.put("PD_Part03ShowCXEDL",PD_Part03ShowCXEDL);
			 json.put("PD_Part03ShowADYSX",PD_Part03ShowADYSX);
			 json.put("PD_Part03ShowBDYSX",PD_Part03ShowBDYSX);
			 json.put("PD_Part03ShowCDYSX",PD_Part03ShowCDYSX);
			 json.put("PD_Part03ShowADYXX",PD_Part03ShowADYXX);
			 json.put("PD_Part03ShowBDYXX",PD_Part03ShowBDYXX);
			 json.put("PD_Part03ShowCDYXX",PD_Part03ShowCDYXX);
			 json.put("PD_Part03ISMULINE",PD_Part03ISMULINE);
			 json.put("PD_Part03ShowXEDLL",PD_Part03ShowXEDLL);
			 json.put("PD_Part03ShowDYSXL",PD_Part03ShowDYSXL);
			 json.put("PD_Part03ShowDYXXL",PD_Part03ShowDYXXL);
			 PrintWriter pw = resp.getWriter();   
		     pw.print(json.toString());  
		    
		    
		    
			
		}
		
		
		
	
	}
	
	
	
	
	
	
	
	/**
	 * 选择列表
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void selectPD_Part(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		JSONArray array = new JSONArray();  
		JSONObject member = null;  
		int id=0;
		int parent_id=0;
		String name="";
	
		String PartStyle_ID="";
		List<PD_PartModel> list=new ArrayList<PD_PartModel>();
		PD_PartModel partModel=new PD_PartModel();
		 PD_PartDao partDao=new PD_PartDao();
		 list=partDao.queryPD_PartSelect();
		 Iterator<PD_PartModel> partIt=list.iterator(); 
		 while(partIt.hasNext())
		  {
			 member=new JSONObject();
		   partModel=partIt.next();
		   id=partModel.getPart_ID();
		   parent_id=partModel.getPart_Parent();
		   name=partModel.getPart_Name();
		  
		   PartStyle_ID=partModel.getPartStyle_ID();
		   
		 
		   member.put("id",id);
		   member.put("parent_id",parent_id);
		   member.put("name",name);
		
		   member.put("PartStyle_ID",PartStyle_ID);
		   array.add(member);
		  }
		 String root=partDao.getRoot();
		 json.put("root", root);
		 json.put("jsonArray", array);  
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
		 pw.flush();
		 pw.close();
	}
	/**
	 * 删除配电参数里面的信息
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws SQLException 
	 */
    private void deletePD_Part(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
    {
    	 boolean flag=false;
 		 int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
    	 int id=0;
    	 int count =0;
		 id=Integer.parseInt(req.getParameter("id"));
		 PD_PartDao partDao=new PD_PartDao();
		 try {
			 flag=partDao.deletePD_Part(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 count = partDao.QueryPD_Part01Num(Pd_Parent);
		 String isSuccess="";
    	 if(flag==true)
    	 {
    		 isSuccess="success";
    	 }
    	 else {
			isSuccess="failure";
		}
    	 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject();
		 json.put("count", count);
		 json.put("isSuccess",isSuccess);	
	//	 System.out.println("删除调用:"+json.toString());
		 PrintWriter pw = resp.getWriter();   
		 pw.print(json.toString());  
		 pw.flush();
		 pw.close();  
    }
    
    /**
     * 增加配电参数信息
     * @param req
     * @param resp
     * @throws SQLException 
     * @throws IOException 
     */
    private void addPD_Part(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
    {
    	boolean flag1=false;
    	boolean flag2=false;
    	boolean menu_parent_name_flag =false;
    	boolean PD_PartAddTyrq_flag =false;
    	String PD_PartAddNum=req.getParameter("PD_PartAddNum");
    	String PD_PartAddName=req.getParameter("PD_PartAddName");
    	
    	String menu_parent_name=req.getParameter("menu_parent_name");
    	int menu_parent=0;
    	if(!menu_parent_name.equals("点击选择上级节点")&&!menu_parent_name.equals("")){
        	menu_parent=Integer.parseInt(req.getParameter("menu_parent"));
        	menu_parent_name_flag=true;
    	}
    	
    	String PartStyle_ID="01";  
    	
    	String PD_PartAddState=req.getParameter("PD_PartAddState");
    	String PD_PartAddSCCJ=req.getParameter("PD_PartAddSCCJ");
    	
    	String PD_PartAddTyrq=req.getParameter("PD_PartAddTyrq");
    	if(!PD_PartAddTyrq.equals("点击选择日期")){
    		PD_PartAddTyrq_flag =true;
    	}
    	
    	String PD_PartAddDylevel=req.getParameter("PD_PartAddDylevel");
    	String PD_PartAddXmlName=req.getParameter("PD_PartAddXmlName");
    	String PD_PartAddRemark=req.getParameter("PD_PartAddRemark");
    	String PD_PartAddAddress=req.getParameter("PD_PartAddAddress");
    	int Part_ID=0;
    	PD_PartDao partDao=new PD_PartDao();
    	//获取ID
    	Part_ID=partDao.getPD_PartId();
    	
    	PD_PartModel partModel=new PD_PartModel();    	
    	partModel.setPart_ID(Part_ID);
    	partModel.setPartStyle_ID(PartStyle_ID);
    	partModel.setPart_Name(PD_PartAddName);
    	partModel.setPart_Num(PD_PartAddNum);
    	partModel.setPart_Parent(menu_parent);
    
    	partModel.setPart_Remark(PD_PartAddRemark);
    	partModel.setPart_SCCJ(PD_PartAddSCCJ);
    	//partModel.setPart_TYRQ("2014-05-03");
    	partModel.setPart_TYRQ(PD_PartAddTyrq);
    	//partModel.setPartStyle(partStyle);
    	partModel.setState(PD_PartAddState);  
    	if((menu_parent_name_flag&&PD_PartAddTyrq_flag)==true){
    		flag1=partDao.addPD_Part(partModel);
    	}
    		
    	PD_PartInfo01Model partInfo01Model=new PD_PartInfo01Model();
    	partInfo01Model.setPart_ID(Part_ID);
    	partInfo01Model.setDYLevel(PD_PartAddDylevel);
    	partInfo01Model.setAddress(PD_PartAddAddress);    	
    	partInfo01Model.setXMLNAME(PD_PartAddXmlName);
    	PD_PartInfo01Dao partInfo01Dao=new PD_PartInfo01Dao();
    	int PARTINFO_ID=0;
    	//获取ID
    	PARTINFO_ID=partInfo01Dao.getPD_PartInfo01Id();
    	partInfo01Model.setPartInfo_ID(PARTINFO_ID);
    	if((menu_parent_name_flag&&PD_PartAddTyrq_flag)==true){
    		flag2=partInfo01Dao.addPD_PartInfo01(partInfo01Model);
    	}
    	 String isSuccess="";
    	 if((flag1&flag2)==true)
    	 {
    		 isSuccess="success";
    	 }
    	 else {
			isSuccess="failure";
		}
    	 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("isSuccess",isSuccess);	 
		 PrintWriter pw = resp.getWriter();   
		 pw.print(json.toString());  
		 pw.flush();
		 pw.close(); 
    	
    	
    }
    
    /**
     * 修改配电参数信息
     * @param req
     * @param resp
     * @throws SQLException 
     * @throws IOException 
     */
    private void editPD_Part(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
    {
    	boolean flag1=false;
    	boolean flag2=false;
    	int PD_PartEditID=Integer.parseInt(req.getParameter("PD_PartEditID"));
    	String PD_PartEditNum=req.getParameter("PD_PartEditNum");
    	String PD_PartEditName=req.getParameter("PD_PartEditName");
    	
    	String menu_parent_nameedit=req.getParameter("menu_parent_nameedit");   	
    	int menu_parentedit=Integer.parseInt(req.getParameter("menu_parentedit"));   	
    
    	
    	
    	String PD_PartEditState=req.getParameter("PD_PartEditState");
    	String PD_PartEditSCCJ=req.getParameter("PD_PartEditSCCJ");
    	String PD_PartEditTyrq=req.getParameter("PD_PartEditTyrq");
    	
    	String PD_PartEditDylevel=req.getParameter("PD_PartEditDylevel");
    	String PD_PartEditXmlName=req.getParameter("PD_PartEditXmlName");
    	String PD_PartEditAddress=req.getParameter("PD_PartEditAddress");
    	String PD_PartEditRemark=req.getParameter("PD_PartEditRemark");
    	
    
    	
    	PD_PartModel partModel=new PD_PartModel();
    	partModel.setPart_ID(PD_PartEditID);
    	partModel.setPart_Num(PD_PartEditNum);
    	partModel.setPart_Name(PD_PartEditName);
    	
    	partModel.setPart_Parent(menu_parentedit);
    	
    	
    	partModel.setState(PD_PartEditState);
    	partModel.setPart_SCCJ(PD_PartEditSCCJ);
    	partModel.setPart_TYRQ(PD_PartEditTyrq);
    	partModel.setPart_Remark(PD_PartEditRemark);
    	
    	PD_PartInfo01Model partInfo01Model=new PD_PartInfo01Model();
    	partInfo01Model.setAddress(PD_PartEditAddress);
    	partInfo01Model.setDYLevel(PD_PartEditDylevel);
    	partInfo01Model.setPart_ID(PD_PartEditID);
    	partInfo01Model.setXMLNAME(PD_PartEditXmlName);
    	
    	
    	
    	PD_PartDao partDao=new PD_PartDao();
    	flag1=partDao.editPD_Part(partModel);
    	
    	PD_PartInfo01Dao partInfo01Dao=new PD_PartInfo01Dao();
    	flag2=partInfo01Dao.editPD_PartInfo01(partInfo01Model);
    	
    	
    	 String isSuccess="";
    	 if((flag1&flag2)==true)
    	 {
    		 isSuccess="success";
    	 }
    	 else {
			isSuccess="failure";
		}
    	 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("isSuccess",isSuccess);	 
		 PrintWriter pw = resp.getWriter();   
		 pw.print(json.toString());  
		 pw.flush();
		 pw.close();
    	
    	
    	
    	
    	
    	
    }
    /**
     * 批量删除
     * @param req
     * @param resp
     * @throws IOException 
     * @throws SQLException 
     */
    private void deletePD_PartArray(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException{
		boolean flag=true;
	    boolean flag1=false;
		int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
		int count =0;
		 String[] idarray=req.getParameterValues("idarray[]");
		 PD_PartDao partDao=new PD_PartDao();
				for(int i=0;i<idarray.length;i++)
				{
					
					try {
						flag1=partDao.deletePD_Part(Integer.parseInt(idarray[i]));												
						flag=flag&flag1;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				count =partDao.QueryPD_Part01Num(Pd_Parent);
				 String isSuccess="";
		    	 if(flag==true)
		    	 {
		    		 isSuccess="success";
		    	 }
		    	 else {
					isSuccess="failure";
				}
		    	 resp.setContentType("text/html;charset=utf-8");  
				 JSONObject json = new JSONObject();
				 json.put("count", count);
				 json.put("isSuccess",isSuccess);	 
				 PrintWriter pw = resp.getWriter(); 
		//		 System.out.println("批量删除调用:"+json.toString());
				 pw.print(json.toString());  
				 pw.flush();
				 pw.close();	
				
	}
}
