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

import com.sf.energy.project.PDRoom.dao.PD_PartInfo02Dao;
import com.sf.energy.project.PDRoom.model.PD_PartInfo02Model;

import com.sf.energy.project.PDRoom.model.PD_PartModel;

/**
 * 02 变压器管理
 * @author Administrator
 *
 */
public class PD_PartInfo02Servlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String flag=req.getParameter("flag");

		if("add".equals(flag))
		{
			try {
				addPD_PartInfo02(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("delete".equals(flag))
		{
			try {
				deletePD_PartInfo02(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("edit".equals(flag))
		{
			try {
				editPD_PartInfo02(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("deletearray".equals(flag))
		{
			try
			{
				deletePD_PartInfo02Array(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("loaddate".equals(flag))
		{
			try {
				loadDataPD_PartInfo02(req, resp);
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

	private void loadDataPD_PartInfo02(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("PD_Part02PageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("PD_Part02PageIndex"));
		int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
		//System.out.println("加载数据调用"+" thePageCount:"+thePageCount+" thePageIndex:"+thePageIndex+" Pd_Parent:"+Pd_Parent);

		PD_PartDao partDao02=new PD_PartDao();
		List<PD_PartInfo02Model> pd_Part02List=new ArrayList<PD_PartInfo02Model>();
		pd_Part02List=partDao02.queryPD_PartInfo02(thePageCount,thePageIndex,Pd_Parent);

		JSONObject json =new JSONObject();
		json.put("totalCount", partDao02.getTotalCount02());
		JSONArray  jo=JSONArray.fromObject(pd_Part02List);

		json.put("jo",jo);

		resp.setContentType("text/html;charset=utf-8");  

		PrintWriter pw = resp.getWriter();  
		pw.print(json.toString());  
		//	 System.out.println("变压器管理 数据加载："+json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 删除
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void deletePD_PartInfo02(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
		boolean flag=false;
		int count = 0;
		int id=0;
		id=Integer.parseInt(req.getParameter("id"));
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
		count = partDao.QueryPD_Part02Num(Pd_Parent);
	//	System.out.println("变压器删除 ：Pd_Parent :"+Pd_Parent+"  count:"+count);
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("isSuccess",isSuccess);	
		json.put("count", count);
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  
		//	 System.out.println("删除调用:"+json.toString());
		pw.flush();
		pw.close();  

	}

	/**
	 * 增加
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void addPD_PartInfo02(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag1=false;
		boolean flag2=false;
		boolean PD_Part02_parent_idadd_flag = false;
		boolean PD_Part02AddTYRQ_flag =false;
		String PD_Part02AddNum=req.getParameter("PD_Part02AddNum");
		String PD_Part02AddName=req.getParameter("PD_Part02AddName");
		String PD_Part02AddState=req.getParameter("PD_Part02AddState");
		String PD_Part02AddSCCJ=req.getParameter("PD_Part02AddSCCJ");
		String PD_Part02_parent_nameadd=req.getParameter("PD_Part02_parent_nameadd");
		//	System.out.println("PD_Part02_parent_idadd::"+req.getParameter("PD_Part02_parent_idadd"));
		int PD_Part02_parent_idadd=-1;
		if((!req.getParameter("PD_Part02_parent_idadd").equals("点击选择上级节点"))&&!req.getParameter("PD_Part02_parent_idadd").equals("")){
			PD_Part02_parent_idadd=Integer.parseInt(req.getParameter("PD_Part02_parent_idadd"));
			PD_Part02_parent_idadd_flag=true;
		}
		String PD_Part02AddTYRQ=req.getParameter("PD_Part02AddTYRQ");
		if(!PD_Part02AddTYRQ.equals("点击选择日期"));{
			PD_Part02AddTYRQ_flag = true;
		}

		//		System.out.println(PD_Part02_parent_idadd+"  "+PD_Part02AddTYRQ
		//				+"  "+PD_Part02_parent_idadd_flag+PD_Part02AddTYRQ_flag);

		String PD_Part02AddMan=req.getParameter("PD_Part02AddMan");
		String PD_Part02AddManPhone=req.getParameter("PD_Part02AddManPhone");
		String PD_Part02AddGZPL=req.getParameter("PD_Part02AddGZPL");
		String PD_Part02AddEDGL=req.getParameter("PD_Part02AddEDGL");
		String PD_Part02AddEDDY=req.getParameter("PD_Part02AddEDDY");
		String PD_Part02AddDYB=req.getParameter("PD_Part02AddDYB");
		String PD_Part02AddKZDL=req.getParameter("PD_Part02AddKZDL");
		String PD_Part02AddKZSH=req.getParameter("PD_Part02AddKZSH");
		String PD_Part02AddXL=req.getParameter("PD_Part02AddXL");
		String PD_Part02AddDYSX=req.getParameter("PD_Part02AddDYSX");
		String PD_Part02AddDYXX=req.getParameter("PD_Part02AddDYXX");
		String PD_Part02AddXEDL=req.getParameter("PD_Part02AddXEDL");
		String PD_Part02AddXEWG=req.getParameter("PD_Part02AddXEWG");
		String PD_Part02AddGLYS=req.getParameter("PD_Part02AddGLYS");
		String PD_Part02AddRemark=req.getParameter("PD_Part02AddRemark");

		//变压器类型
		String PartStyle_ID="02";
		int Part_ID=0;
		PD_PartDao partDao=new PD_PartDao();
		//获取ID
		Part_ID=partDao.getPD_PartId();



		PD_PartModel partModel=new PD_PartModel();
		partModel.setPart_ID(Part_ID);
		partModel.setPartStyle_ID(PartStyle_ID);
		partModel.setPart_Parent(PD_Part02_parent_idadd);
		partModel.setState(PD_Part02AddState);
		partModel.setPart_Num(PD_Part02AddNum);
		partModel.setPart_Name(PD_Part02AddName);
		partModel.setPart_SCCJ(PD_Part02AddSCCJ);
		partModel.setPart_TYRQ(PD_Part02AddTYRQ);
		partModel.setPart_Remark(PD_Part02AddRemark);



		PD_PartInfo02Dao partInfo02Dao=new PD_PartInfo02Dao();
		int PartInfo_ID=0;
		PartInfo_ID=partInfo02Dao.getPD_PartInfo02Id();
		PD_PartInfo02Model partInfo02Model=new PD_PartInfo02Model();
		partInfo02Model.setPartInfo_ID(PartInfo_ID);
		partInfo02Model.setPart_ID(Part_ID);
		partInfo02Model.setMan(PD_Part02AddMan);
		partInfo02Model.setManPhone(PD_Part02AddManPhone);
		partInfo02Model.setGZPL(PD_Part02AddGZPL);
		partInfo02Model.setEDGL(PD_Part02AddEDGL);
		partInfo02Model.setEDDY(PD_Part02AddEDDY);
		partInfo02Model.setDYB(PD_Part02AddDYB);
		partInfo02Model.setKZDL(PD_Part02AddKZDL);
		partInfo02Model.setKZSH(PD_Part02AddKZSH);
		partInfo02Model.setXL(PD_Part02AddXL);
		partInfo02Model.setDYSX(PD_Part02AddDYSX);
		partInfo02Model.setDYXX(PD_Part02AddDYXX);
		partInfo02Model.setXEDL(PD_Part02AddXEDL);
		partInfo02Model.setXEWG(PD_Part02AddXEWG);
		partInfo02Model.setGLYS(PD_Part02AddGLYS);

		if((PD_Part02_parent_idadd_flag&&PD_Part02AddTYRQ_flag)==true){
			flag1=partDao.addPD_Part(partModel);
			flag2=partInfo02Dao.addPD_PartInfo02(partInfo02Model);
		}

		//    	System.out.println("新建调用 "+PD_Part02AddNum+" | "
		//		+" | "+PD_Part02AddName
		//		+" | "+PD_Part02AddState
		//		+" | "+PD_Part02AddSCCJ
		//		+" | "+PD_Part02_parent_nameadd
		//		+" | "+PD_Part02_parent_idadd	
		//		+" | "+PD_Part02AddTYRQ
		//		+" | "+PD_Part02AddMan
		//		+" | "+PD_Part02AddManPhone
		//		+" | "+PD_Part02AddGZPL
		//		+" | "+PD_Part02AddEDGL
		//		+" | "+PD_Part02AddEDDY
		//		+" | "+PD_Part02AddDYB
		//		+" | "+PD_Part02AddKZDL
		//		+" | "+PD_Part02AddKZSH
		//		+" | "+PD_Part02AddXL
		//		+" | "+PD_Part02AddDYSX
		//		+" | "+PD_Part02AddDYXX
		//		+" | "+PD_Part02AddXEDL
		//		+" | "+PD_Part02AddXEWG
		//		+" | "+PD_Part02AddGLYS
		//		+" | "+PD_Part02AddRemark);

		String isSuccess="";
		if((flag1&&flag2)==true)
		{
			isSuccess="success";
		}
		else {
			isSuccess="failure";
		}
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("isSuccess",isSuccess);
		json.put("PD_Part02_parent_idadd", PD_Part02_parent_idadd);
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  
		pw.flush();
		pw.close(); 



	}

	/**
	 * 修改
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void editPD_PartInfo02(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag1=false;
		boolean flag2=false;
		int PD_Part02EditID=Integer.parseInt(req.getParameter("PD_Part02EditID"));

		String PD_Part02EditNum=req.getParameter("PD_Part02EditNum");
		String PD_Part02EditName=req.getParameter("PD_Part02EditName");
		String PD_Part02EditState=req.getParameter("PD_Part02EditState");
		String PD_Part02EditSCCJ=req.getParameter("PD_Part02EditSCCJ");
		String PD_Part02_parent_nameedit=req.getParameter("PD_Part02_parent_nameedit");
		int PD_Part02_parent_idedit=Integer.parseInt(req.getParameter("PD_Part02_parent_idedit"));

		String PD_Part02EditTYRQ=req.getParameter("PD_Part02EditTYRQ");


		String PD_Part02EditMan=req.getParameter("PD_Part02EditMan");
		String PD_Part02EditManPhone=req.getParameter("PD_Part02EditManPhone");
		String PD_Part02EditGZPL=req.getParameter("PD_Part02EditGZPL");
		String PD_Part02EditEDGL=req.getParameter("PD_Part02EditEDGL");
		String PD_Part02EditEDDY=req.getParameter("PD_Part02EditEDDY");
		String PD_Part02EditDYB=req.getParameter("PD_Part02EditDYB");
		String PD_Part02EditKZDL=req.getParameter("PD_Part02EditKZDL");
		String PD_Part02EditKZSH=req.getParameter("PD_Part02EditKZSH");
		String PD_Part02EditXL=req.getParameter("PD_Part02EditXL");
		String PD_Part02EditDYSX=req.getParameter("PD_Part02EditDYSX");
		String PD_Part02EditDYXX=req.getParameter("PD_Part02EditDYXX");
		String PD_Part02EditXEDL=req.getParameter("PD_Part02EditXEDL");
		String PD_Part02EditXEWG=req.getParameter("PD_Part02EditXEWG");
		String PD_Part02EditGLYS=req.getParameter("PD_Part02EditGLYS");
		String PD_Part02EditRemark=req.getParameter("PD_Part02EditRemark");




		PD_PartModel partModel=new PD_PartModel();
		partModel.setPart_ID(PD_Part02EditID);
		partModel.setPartStyle_ID("02");
		partModel.setPart_Parent(PD_Part02_parent_idedit);
		partModel.setState(PD_Part02EditState);
		partModel.setPart_Num(PD_Part02EditNum);
		partModel.setPart_Name(PD_Part02EditName);
		partModel.setPart_SCCJ(PD_Part02EditSCCJ);
		partModel.setPart_TYRQ(PD_Part02EditTYRQ);
		partModel.setPart_Remark(PD_Part02EditRemark);




		PD_PartInfo02Model partInfo02Model=new PD_PartInfo02Model();
		partInfo02Model.setPart_ID(PD_Part02EditID);
		partInfo02Model.setMan(PD_Part02EditMan);
		partInfo02Model.setManPhone(PD_Part02EditManPhone);
		partInfo02Model.setGZPL(PD_Part02EditGZPL);
		partInfo02Model.setEDGL(PD_Part02EditEDGL);
		partInfo02Model.setEDDY(PD_Part02EditEDDY);
		partInfo02Model.setDYB(PD_Part02EditDYB);
		partInfo02Model.setKZDL(PD_Part02EditKZDL);
		partInfo02Model.setKZSH(PD_Part02EditKZSH);
		partInfo02Model.setXL(PD_Part02EditXL);
		partInfo02Model.setDYSX(PD_Part02EditDYSX);
		partInfo02Model.setDYXX(PD_Part02EditDYXX);
		partInfo02Model.setXEDL(PD_Part02EditXEDL);
		partInfo02Model.setXEWG(PD_Part02EditXEWG);
		partInfo02Model.setGLYS(PD_Part02EditGLYS);


		PD_PartDao partDao=new PD_PartDao();
		flag1=partDao.editPD_Part(partModel);

		PD_PartInfo02Dao partInfo02Dao=new PD_PartInfo02Dao();
		flag2=partInfo02Dao.editPD_PartInfo02(partInfo02Model);



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
	private void deletePD_PartInfo02Array(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		boolean flag=true;
		boolean flag1=false;
		int count = 0;
		int Pd_Parent=Integer.parseInt(req.getParameter("Pd_Parent"));
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
		String isSuccess="";
		if(flag==true)
		{
			isSuccess="success";
		}
		else {
			isSuccess="failure";
		}
		count = partDao.QueryPD_Part02Num(Pd_Parent);
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("isSuccess",isSuccess);	
		json.put("count", count);
		PrintWriter pw = resp.getWriter();   
		pw.print(json.toString());  
		//	 System.out.println("批量删除调用:"+json.toString());
		pw.flush();
		pw.close();	
	}
}
