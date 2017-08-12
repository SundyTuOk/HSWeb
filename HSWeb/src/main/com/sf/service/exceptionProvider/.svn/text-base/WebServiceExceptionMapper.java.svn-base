package com.sf.service.exceptionProvider;

import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebServiceExceptionMapper implements ExceptionMapper<Exception>
{

	@Override
	public Response toResponse(Exception ex)
	{
        System.out.println("处理异常的响应:"+ex);
        ResponseBuilder rb = Response.status(Response.Status.NOT_FOUND);
        rb.type("application/json;charset=UTF-8");
        rb.entity("{'error':'"+ex.getMessage()+"'}");
        rb.language(Locale.SIMPLIFIED_CHINESE);
        rb.status(200);
        Response r = rb.build();
        return r;
	}
	
}
