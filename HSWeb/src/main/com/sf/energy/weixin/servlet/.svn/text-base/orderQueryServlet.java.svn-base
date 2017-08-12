package com.sf.energy.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.weixin.service.orderQueryService;
import com.sf.energy.weixin.service.refundService;

public class orderQueryServlet extends HttpServlet
{

	private void findmethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		String method = (String) request.getParameter("method");
		if(("orderQuery").equals(method)){
			orderQuery(request, response);
		}else if(("orderInsert").equals(method)){
			orderInsert(request, response);
		}else if(("batchResendOrder").equals(method)){
			batchResendOrder(request, response);
		}else if(("refundOrder").equals(method)){
			refundOrder(request, response);
		}else if(("refundQuery").equals(method)){
			refundQuery(request, response);
		}
	}
	private void refundQuery(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		refundService service = new refundService();
		String orderNo = null;
		if(request.getParameter("orderNo")!=null&&!("").equals(request.getParameter("orderNo"))){
			orderNo = (String) request.getParameter("orderNo");
		}
		if(orderNo==null){
			return;
		}
		String str = service.refund_Query_By_OrderNo_Service(orderNo);

		PrintWriter out = response.getWriter();
		//System.out.println(str);
		out.println(str);
		out.flush();
		out.close();
	}
	private void refundOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException
	{
		refundService service = new refundService();
		String orderNo = null;
		if(request.getParameter("orderNo")!=null&&!("").equals(request.getParameter("orderNo"))){
			orderNo = (String) request.getParameter("orderNo");
		}
		if(orderNo==null){
			return;
		}
		HttpSession session = request.getSession();
		int userId = 0;
		if(session.getAttribute("userId")!=null){
			userId = (int) session.getAttribute("userId");
		}
		int flag = service.refundOrder_Service(orderNo, userId);
		JSONObject robj = new JSONObject();
		switch (flag)
		{
		case -5:
			robj.put("result_code", "订单不存在,无法申请退款!");
			break;
		case -4:
			robj.put("result_code", "微信订单查询结果异常,退款申请失败!");
			break;
		case -3:
			robj.put("result_code", "购电单已生成,无法申请退款!");
			break;
		case -2:
			robj.put("result_code", "订单已提交退款申请,无需重复申请!");
			break;
		case -1:
			robj.put("result_code", "程序运行异常,退款申请失败!");
			break;
		case 0://订单存在且已插入数据库
			robj.put("result_code", "退款申请成功,但数据库更新失败!");
			break;
		case 1:
			robj.put("result_code", "订单存在且支付成功,退款申请成功!");
			break;
		case 2:
			robj.put("result_code", "向微信服务器退款申请失败!");
			break;

		default:
			break;
		}
		//System.out.println(robj.toString());
		PrintWriter out = response.getWriter();
		out.println(robj.toString());
		out.flush();
		out.close();
	}
	private void batchResendOrder(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		orderQueryService service = new orderQueryService();
		Map<String, Integer> map= service.check_And_Resend_Order_Service();
		//System.out.println(new Gson().toJson(map));
		PrintWriter out = response.getWriter();
		out.println(new Gson().toJson(map));
		out.flush();
		out.close();
		
	}
	private void orderInsert(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		orderQueryService service = new orderQueryService();
		String orderNo = null;
		if(request.getParameter("orderNo")!=null&&!("").equals(request.getParameter("orderNo"))){
			orderNo = (String) request.getParameter("orderNo");
		}
		if(orderNo==null){
			return;
		}
		int ammeterId = -1;
		if(request.getParameter("ammeterId")!=null&&!("").equals(request.getParameter("ammeterId"))){
			ammeterId = Integer.parseInt(request.getParameter("ammeterId"));
		}
		if(ammeterId==-1){
			return;
		}
		int flag = service.insert_new_weixinOder_Service(ammeterId, orderNo,"异常申述");
		JSONObject robj = new JSONObject();
		switch (flag)
		{
		case -3:
			robj.put("result_code", "程序运行异常,请稍后重试!");
			break;
		case -2:
			robj.put("result_code", "微信订单查询结果异常,请稍后重试!");
			break;
		case -1:
			robj.put("result_code", "微信订单不存在,请仔细核对订单号!");
			break;
		case 0://订单存在且已插入数据库
			robj.put("result_code", "订单已下发，无需重新下发!");
			break;
		case 1:
			robj.put("result_code", "订单下发未成功,请稍后重试!");
			break;
		case 2:
			robj.put("result_code", "订单下发成功,祝您生活愉快!");
			break;

		default:
			break;
		}
		//System.out.println(robj.toString());
		PrintWriter out = response.getWriter();
		out.println(robj.toString());
		out.flush();
		out.close();
	}
	private void orderQuery(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		orderQueryService service = new orderQueryService();
		String orderNo = null;
		if(request.getParameter("orderNo")!=null&&!("").equals(request.getParameter("orderNo"))){
			orderNo = (String) request.getParameter("orderNo");
		}
		if(orderNo==null){
			return;
		}
		String str = service.order_Query_By_OrderNo_Service(orderNo);

		PrintWriter out = response.getWriter();
		//System.out.println(str);
		out.println(str);
		out.flush();
		out.close();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try
		{
			findmethod(request, response);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
