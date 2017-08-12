package com.sf.energy.project.PDRoom.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.sf.energy.project.PDRoom.dao.PD_PartDao;
import com.sf.energy.project.PDRoom.dao.PD_PartInfo03Dao;
import com.sf.energy.project.PDRoom.model.PD_PartInfo03Model;
import com.sf.energy.project.PDRoom.model.PD_PartModel;
/**
 * 03 回路管理
 * @author Administrator
 *
 */
public class PD_PartInfo03Servlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String flag=req.getParameter("flag");

		if("add".equals(flag))
		{
			try {
				addPD_PartInfo03(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("setParent".equals(flag))
		{
			try {
				SetPD_Parent(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("delete".equals(flag))
		{
			try {
				deletePD_PartInfo03(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("edit".equals(flag))
		{
			try {
				editPD_PartInfo03(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("deletearray".equals(flag))
		{
			try
			{
				deletePD_PartInfo03Array(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("loaddate".equals(flag))
		{
			try {
				loadDataPD_PartInfo03(req, resp);
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

	private void loadDataPD_PartInfo03(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("PD_Part_03PageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("PD_Part_03PageIndex"));
		int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
		String PD_Part_03TableName=req.getParameter("PD_Part_03TableName");
		String PD_Part_03Order=req.getParameter("PD_Part_03Order");

		PD_PartDao partDao03=new PD_PartDao();
		List<PD_PartInfo03Model> pd_Part03List=new ArrayList<PD_PartInfo03Model>();
		pd_Part03List=partDao03.queryPD_PartInfo03(thePageCount,thePageIndex,Pd_Parent,PD_Part_03TableName,PD_Part_03Order);


		JSONObject json =new JSONObject();
		json.put("totalCount", partDao03.getTotalCount03());			

		JSONArray  jo=JSONArray.fromObject(pd_Part03List);

		json.put("jo",jo);


		resp.setContentType("text/html;charset=utf-8");  

		PrintWriter pw = resp.getWriter();  
		pw.print(json.toString());  
		//		 System.out.println("回路管理 数据加载："+json.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 删除
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void deletePD_PartInfo03(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int count =0;
		boolean flag=false;
		int id=0;
		id=Integer.parseInt(req.getParameter("id"));
		int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
		PD_PartDao partDao=new PD_PartDao();
		flag=partDao.deletePD_Part(id);		 

		String isSuccess="";
		if(flag==true)
		{
			isSuccess="success";
		}
		else {
			isSuccess="failure";
		}
		count = partDao.QueryPD_Part03Num(Pd_Parent);
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("isSuccess",isSuccess);
		json.put("count", count);
		// System.out.println("回路管理删除 ：Pd_Parent"+Pd_Parent+"  count:"+count);
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString()); 
		pw.flush();
		pw.close();  
	}

	/**
	 * 删除
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void SetPD_Parent(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{

		boolean flag=false;
		String theID=req.getParameter("theID");
		String Pd_Parent=req.getParameter("Pd_Parent");
		PD_PartDao partDao=new PD_PartDao();
		flag=partDao.SetPD_Parent(Pd_Parent,theID);		 

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
		json.put("isSuccess",isSuccess);

		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString()); 
		pw.flush();
		pw.close();  
	}

	/**
	 * 增加
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void addPD_PartInfo03(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag1=false;
		boolean flag2=false;
		boolean PD_Part03_parent_idadd_flag = false;
		boolean PD_Part03AddTYRQ_flag =false;
		String PD_Part03AddNum=req.getParameter("PD_Part03AddNum");
		String PD_Part03AddName=req.getParameter("PD_Part03AddName");
		String PD_Part03AddState=req.getParameter("PD_Part03AddState");

		String PD_Part03_parent_nameadd=req.getParameter("PD_Part03_parent_nameadd");
		if(!PD_Part03_parent_nameadd.equals("点击选择上级节点")){
			PD_Part03_parent_idadd_flag = true;
		}
		// System.out.println("  |  "+PD_Part03_parent_nameadd);
		int PD_Part03_parent_idadd=Integer.parseInt(req.getParameter("PD_Part03_parent_idadd"));
		
		String PD_Part03AddSCCJ=req.getParameter("PD_Part03AddSCCJ");
		String PD_Part03AddTYRQ=req.getParameter("PD_Part03AddTYRQ");
		if(!PD_Part03AddTYRQ.equals("点击选择日期")){
			PD_Part03AddTYRQ_flag=true;
		}
		else 
		{
			
			PD_Part03AddTYRQ="2015-01-01";
			PD_Part03AddTYRQ_flag=true;
		}
		
		String PD_Part03AddRemark=req.getParameter("PD_Part03AddRemark");
		double PD_Part03AddJiemian=Double.parseDouble(req.getParameter("PD_Part03AddJiemian"));

		String PD_Part03AddStartAddress=req.getParameter("PD_Part03AddStartAddress");
		String PD_Part03AddEndAddress=req.getParameter("PD_Part03AddEndAddress");
		int PD_Part03AddLength=Integer.parseInt(req.getParameter("PD_Part03AddLength"));

		int PD_Part03AddJLLX=Integer.parseInt(req.getParameter("PD_Part03AddJLLX"));
		String PD_Part03AddJLID=req.getParameter("PD_Part03AddJLID");
		double PD_Part03AddGZPL=Double.parseDouble(req.getParameter("PD_Part03AddGZPL"));

		double PD_Part03AddEDDY=Double.parseDouble(req.getParameter("PD_Part03AddEDDY"));
		double PD_Part03AddKZDL=Double.parseDouble(req.getParameter("PD_Part03AddKZDL"));

		double PD_Part03AddKZSH=Double.parseDouble(req.getParameter("PD_Part03AddKZSH"));

		double PD_Part03AddXL=Double.parseDouble(req.getParameter("PD_Part03AddXL"));
		double PD_Part03AddGLYS=Double.parseDouble(req.getParameter("PD_Part03AddGLYS"));
		double PD_Part03AddDYB=Double.parseDouble(req.getParameter("PD_Part03AddDYB"));

		double PD_Part03AddAEDGL=Double.parseDouble(req.getParameter("PD_Part03AddAEDGL"));
		double PD_Part03AddBEDGL=Double.parseDouble(req.getParameter("PD_Part03AddBEDGL"));
		double PD_Part03AddCEDGL=Double.parseDouble(req.getParameter("PD_Part03AddCEDGL"));
		double PD_Part03AddZEDGL=Double.parseDouble(req.getParameter("PD_Part03AddZEDGL"));

		double PD_Part03AddAXEWG=Double.parseDouble(req.getParameter("PD_Part03AddAXEWG"));
		double PD_Part03AddBXEWG=Double.parseDouble(req.getParameter("PD_Part03AddBXEWG"));
		double PD_Part03AddCXEWG=Double.parseDouble(req.getParameter("PD_Part03AddCXEWG"));
		double PD_Part03AddZXEWG=Double.parseDouble(req.getParameter("PD_Part03AddZXEWG"));

		double PD_Part03AddISMULINE=Double.parseDouble(req.getParameter("PD_Part03AddISMULINE"));
		double PD_Part03AddAXEDL=Double.parseDouble(req.getParameter("PD_Part03AddAXEDL"));
		double PD_Part03AddBXEDL=Double.parseDouble(req.getParameter("PD_Part03AddBXEDL"));
		double PD_Part03AddCXEDL=Double.parseDouble(req.getParameter("PD_Part03AddCXEDL"));
		double PD_Part03AddXEDLL=Double.parseDouble(req.getParameter("PD_Part03AddXEDLL"));

		double PD_Part03AddADYSX=Double.parseDouble(req.getParameter("PD_Part03AddADYSX"));
		double PD_Part03AddBDYSX=Double.parseDouble(req.getParameter("PD_Part03AddBDYSX"));
		double PD_Part03AddCDYSX=Double.parseDouble(req.getParameter("PD_Part03AddCDYSX"));
		double PD_Part03AddDYSXL=Double.parseDouble(req.getParameter("PD_Part03AddDYSXL"));

		double PD_Part03AddADYXX=Double.parseDouble(req.getParameter("PD_Part03AddADYXX"));
		double PD_Part03AddBDYXX=Double.parseDouble(req.getParameter("PD_Part03AddBDYXX"));
		double PD_Part03AddCDYXX=Double.parseDouble(req.getParameter("PD_Part03AddCDYXX"));
		double PD_Part03AddDYXXL=Double.parseDouble(req.getParameter("PD_Part03AddDYXXL"));

		//回路类型
		String PartStyle_ID="03";
		//ID
		int Part_ID=0;
		PD_PartDao partDao=new PD_PartDao();
		//获取ID
		Part_ID=partDao.getPD_PartId();


		PD_PartModel partModel=new PD_PartModel();
		partModel.setPart_ID(Part_ID);
		partModel.setPartStyle_ID(PartStyle_ID);
		partModel.setPart_Parent(PD_Part03_parent_idadd);
		partModel.setState(PD_Part03AddState);
		partModel.setPart_Num(PD_Part03AddNum);
		partModel.setPart_Name(PD_Part03AddName);

		partModel.setPart_SCCJ(PD_Part03AddSCCJ);
		partModel.setPart_TYRQ(PD_Part03AddTYRQ);
		partModel.setPart_Remark(PD_Part03AddRemark);

		if((PD_Part03_parent_idadd_flag&PD_Part03AddTYRQ_flag)==true){
			flag1=partDao.addPD_Part(partModel);
		}

		//获取回路详细信息表id
		PD_PartInfo03Dao partInfo03Dao=new PD_PartInfo03Dao();
		int PartInfo_ID=0;
		PartInfo_ID=partInfo03Dao.getPD_PartInfo03Id();

		PD_PartInfo03Model partInfo03Model=new PD_PartInfo03Model();
		
		partInfo03Model.setPartInfo_ID(PartInfo_ID);
		partInfo03Model.setPart_ID(Part_ID);

		partInfo03Model.setJiemian(PD_Part03AddJiemian);

		partInfo03Model.setStartAddress(PD_Part03AddStartAddress);
		partInfo03Model.setEndAddress(PD_Part03AddEndAddress);
		partInfo03Model.setLength(PD_Part03AddLength);

		partInfo03Model.setJLLX(PD_Part03AddJLLX);
		partInfo03Model.setJLID(PD_Part03AddJLID);
		partInfo03Model.setGZPL(PD_Part03AddGZPL);

		partInfo03Model.setEDDY(PD_Part03AddEDDY);
		partInfo03Model.setKZDL(PD_Part03AddKZDL);
		partInfo03Model.setKZSH(PD_Part03AddKZSH);

		partInfo03Model.setXL(PD_Part03AddXL);
		partInfo03Model.setGLYS(PD_Part03AddGLYS);
		partInfo03Model.setDYB(PD_Part03AddDYB);

		partInfo03Model.setAEDGL(PD_Part03AddAEDGL);
		partInfo03Model.setBEDGL(PD_Part03AddBEDGL);
		partInfo03Model.setCEDGL(PD_Part03AddCEDGL);
		partInfo03Model.setZEDGL(PD_Part03AddZEDGL);

		partInfo03Model.setAXEWG(PD_Part03AddAXEWG);
		partInfo03Model.setBXEWG(PD_Part03AddBXEWG);
		partInfo03Model.setCXEWG(PD_Part03AddCXEWG);
		partInfo03Model.setZXEWG(PD_Part03AddZXEWG);

		partInfo03Model.setISMULINE(PD_Part03AddISMULINE);
		partInfo03Model.setAXEDL(PD_Part03AddAXEDL);
		partInfo03Model.setBXEDL(PD_Part03AddBXEDL);
		partInfo03Model.setCXEDL(PD_Part03AddCXEDL);
		partInfo03Model.setXEDLL(PD_Part03AddXEDLL);
		
		partInfo03Model.setADYSX(PD_Part03AddADYSX);
		partInfo03Model.setBDYSX(PD_Part03AddBDYSX);
		partInfo03Model.setCDYSX(PD_Part03AddCDYSX);
		partInfo03Model.setDYSXL(PD_Part03AddDYSXL);
		
		partInfo03Model.setADYXX(PD_Part03AddADYXX);
		partInfo03Model.setBDYXX(PD_Part03AddBDYXX);
		partInfo03Model.setCDYXX(PD_Part03AddCDYXX);
		partInfo03Model.setDYXXL(PD_Part03AddDYXXL);
		
		if((PD_Part03_parent_idadd_flag&PD_Part03AddTYRQ_flag)==true){
			flag2=partInfo03Dao.addPD_PartInfo03(partInfo03Model);

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
	 * 修改
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void editPD_PartInfo03(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag1=false;
		boolean flag2=false;
		//要修改的ID
		int PD_Part03EditID=Integer.parseInt(req.getParameter("PD_Part03EditID"));

		String PD_Part03EditNum=req.getParameter("PD_Part03EditNum");
		String PD_Part03EditName=req.getParameter("PD_Part03EditName");
		String PD_Part03EditState=req.getParameter("PD_Part03EditState");

		String PD_Part03_parent_nameedit=req.getParameter("PD_Part03_parent_nameedit");
		int PD_Part03_parent_idedit=Integer.parseInt(req.getParameter("PD_Part03_parent_idedit"));

		double PD_Part03ISMULINE=Double.parseDouble(req.getParameter("PD_Part03ISMULINE"));
		String PD_Part03EditSCCJ=req.getParameter("PD_Part03EditSCCJ");
		String PD_Part03EditTYRQ=req.getParameter("PD_Part03EditTYRQ");
		String PD_Part03EditRemark=req.getParameter("PD_Part03EditRemark");
		double PD_Part03EditJiemian=Double.parseDouble(req.getParameter("PD_Part03EditJiemian"));

		String PD_Part03EditStartAddress=req.getParameter("PD_Part03EditStartAddress");
		String PD_Part03EditEndAddress=req.getParameter("PD_Part03EditEndAddress");
		int PD_Part03EditLength=Integer.parseInt(req.getParameter("PD_Part03EditLength"));

		int PD_Part03EditJLLX=Integer.parseInt(req.getParameter("PD_Part03EditJLLX"));
		String PD_Part03EditJLID=req.getParameter("PD_Part03EditJLID");
		double PD_Part03EditGZPL=Double.parseDouble(req.getParameter("PD_Part03EditGZPL"));

		double PD_Part03EditEDDY=Double.parseDouble(req.getParameter("PD_Part03EditEDDY"));
		double PD_Part03EditKZDL=Double.parseDouble(req.getParameter("PD_Part03EditKZDL"));
		double PD_Part03EditKZSH=Double.parseDouble(req.getParameter("PD_Part03EditKZSH"));

		double PD_Part03EditXL=Double.parseDouble(req.getParameter("PD_Part03EditXL"));
		double PD_Part03EditGLYS=Double.parseDouble(req.getParameter("PD_Part03EditGLYS"));
		double PD_Part03EditDYB=Double.parseDouble(req.getParameter("PD_Part03EditDYB"));

		double PD_Part03EditAEDGL=Double.parseDouble(req.getParameter("PD_Part03EditAEDGL"));
		double PD_Part03EditBEDGL=Double.parseDouble(req.getParameter("PD_Part03EditBEDGL"));
		double PD_Part03EditCEDGL=Double.parseDouble(req.getParameter("PD_Part03EditCEDGL"));
		double PD_Part03EditZEDGL=Double.parseDouble(req.getParameter("PD_Part03EditZEDGL"));

		double PD_Part03EditAXEWG=Double.parseDouble(req.getParameter("PD_Part03EditAXEWG"));
		double PD_Part03EditBXEWG=Double.parseDouble(req.getParameter("PD_Part03EditBXEWG"));
		double PD_Part03EditCXEWG=Double.parseDouble(req.getParameter("PD_Part03EditCXEWG"));
		double PD_Part03EditZXEWG=Double.parseDouble(req.getParameter("PD_Part03EditZXEWG"));


		double PD_Part03EditAXEDL=Double.parseDouble(req.getParameter("PD_Part03EditAXEDL"));
		double PD_Part03EditBXEDL=Double.parseDouble(req.getParameter("PD_Part03EditBXEDL"));
		double PD_Part03EditCXEDL=Double.parseDouble(req.getParameter("PD_Part03EditCXEDL"));
		double PD_Part03EditXEDLL=Double.parseDouble(req.getParameter("PD_Part03EditXEDLL"));

		double PD_Part03EditADYSX=Double.parseDouble(req.getParameter("PD_Part03EditADYSX"));
		double PD_Part03EditBDYSX=Double.parseDouble(req.getParameter("PD_Part03EditBDYSX"));
		double PD_Part03EditCDYSX=Double.parseDouble(req.getParameter("PD_Part03EditCDYSX"));
		double PD_Part03EditDYSXL=Double.parseDouble(req.getParameter("PD_Part03EditDYSXL"));

		double PD_Part03EditADYXX=Double.parseDouble(req.getParameter("PD_Part03EditADYXX"));
		double PD_Part03EditBDYXX=Double.parseDouble(req.getParameter("PD_Part03EditBDYXX"));
		double PD_Part03EditCDYXX=Double.parseDouble(req.getParameter("PD_Part03EditCDYXX"));
		double PD_Part03EditDYXXL=Double.parseDouble(req.getParameter("PD_Part03EditDYXXL"));



		PD_PartModel partModel=new PD_PartModel();
		partModel.setPart_ID(PD_Part03EditID);
		partModel.setPartStyle_ID("03");
		partModel.setPart_Parent(PD_Part03_parent_idedit);
		partModel.setState(PD_Part03EditState);
		partModel.setPart_Num(PD_Part03EditNum);
		partModel.setPart_Name(PD_Part03EditName);
		partModel.setPart_SCCJ(PD_Part03EditSCCJ);
		partModel.setPart_TYRQ(PD_Part03EditTYRQ);
		partModel.setPart_Remark(PD_Part03EditRemark);


		//修改节点表
		PD_PartDao partDao=new PD_PartDao();
		flag1=partDao.editPD_Part(partModel);






		PD_PartInfo03Model partInfo03Model=new PD_PartInfo03Model();

		partInfo03Model.setPart_ID(PD_Part03EditID);

		partInfo03Model.setJiemian(PD_Part03EditJiemian);
		partInfo03Model.setISMULINE(PD_Part03ISMULINE);
		partInfo03Model.setStartAddress(PD_Part03EditStartAddress);
		partInfo03Model.setEndAddress(PD_Part03EditEndAddress);
		partInfo03Model.setLength(PD_Part03EditLength);

		partInfo03Model.setJLLX(PD_Part03EditJLLX);
		partInfo03Model.setJLID(PD_Part03EditJLID);
		partInfo03Model.setGZPL(PD_Part03EditGZPL);

		partInfo03Model.setEDDY(PD_Part03EditEDDY);
		partInfo03Model.setKZDL(PD_Part03EditKZDL);
		partInfo03Model.setKZSH(PD_Part03EditKZSH);

		partInfo03Model.setXL(PD_Part03EditXL);
		partInfo03Model.setGLYS(PD_Part03EditGLYS);
		partInfo03Model.setDYB(PD_Part03EditDYB);

		partInfo03Model.setAEDGL(PD_Part03EditAEDGL);
		partInfo03Model.setBEDGL(PD_Part03EditBEDGL);
		partInfo03Model.setCEDGL(PD_Part03EditCEDGL);
		partInfo03Model.setZEDGL(PD_Part03EditZEDGL);

		partInfo03Model.setAXEWG(PD_Part03EditAXEWG);
		partInfo03Model.setBXEWG(PD_Part03EditBXEWG);
		partInfo03Model.setCXEWG(PD_Part03EditCXEWG);
		partInfo03Model.setZXEWG(PD_Part03EditZXEWG);


		partInfo03Model.setAXEDL(PD_Part03EditAXEDL);
		partInfo03Model.setBXEDL(PD_Part03EditBXEDL);
		partInfo03Model.setCXEDL(PD_Part03EditCXEDL);
		partInfo03Model.setXEDLL(PD_Part03EditXEDLL);
		
		partInfo03Model.setADYSX(PD_Part03EditADYSX);
		partInfo03Model.setBDYSX(PD_Part03EditBDYSX);
		partInfo03Model.setCDYSX(PD_Part03EditCDYSX);
		partInfo03Model.setDYSXL(PD_Part03EditDYSXL);
		
		partInfo03Model.setADYXX(PD_Part03EditADYXX);
		partInfo03Model.setBDYXX(PD_Part03EditBDYXX);
		partInfo03Model.setCDYXX(PD_Part03EditCDYXX);
		partInfo03Model.setDYXXL(PD_Part03EditDYXXL);
		
		//修改回路信息
		PD_PartInfo03Dao partInfo03Dao=new PD_PartInfo03Dao();
		flag2=partInfo03Dao.editPD_PartInfo03(partInfo03Model);

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
	private void deletePD_PartInfo03Array(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		int count =0;
		int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
		boolean flag=true;
		boolean flag1=false;
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
		count = partDao.QueryPD_Part03Num(Pd_Parent);
		//	System.out.println("批量删除调用：count"+count);
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
		json.put("isSuccess",isSuccess);
		json.put("count", count);
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  
		pw.flush();
		pw.close();	
	}

}
