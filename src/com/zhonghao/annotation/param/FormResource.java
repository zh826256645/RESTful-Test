package com.zhonghao.annotation.param;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("form-resource")
public class FormResource {

	public static final String USER = "user";
	public static final String PW = "password";
	public static final String NPW = "newPassword";
	public static final String VNPW = "verification";
	
	@POST
	public String newPassword(
			@DefaultValue("ZhongHao") @FormParam(FormResource.USER) final String user 
			, @Encoded @FormParam(FormResource.PW) final String password 
			, @Encoded @FormParam(FormResource.NPW)final String newPassword 
			, @FormParam(FormResource.VNPW) final String verification) {
		return user + ":" + password + ":" + newPassword + ":" + verification;
		
	}
}
