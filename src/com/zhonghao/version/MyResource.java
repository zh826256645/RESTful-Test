package com.zhonghao.version;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * �汾����
 * REST �İ汾�ţ�version�������Ǳ�Ҫ��
 * �����ṩ���汾�ŵ���Դ��ַ���Ͳ����汾�ŵ���Դ��ַһ��ʹ��
 * 
 * @author �Ӻ�
 * Create on 2016��9��21�� ����3:25:18
 *
 */
@Path("version")
public class MyResource {
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            if (i % 10 == 0) {
                result.append(">").append(i);
            }
        }
        return result.toString();
    }
    
    // �汾1
    // ���ڶ��壺�� API �ĵ��и����汾�������������
    // ���ذ汾2�� link
    @GET
    @Path("v1.0")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt1(@Context UriInfo uriInfo) {
    	final UriBuilder ub = uriInfo.getAbsolutePathBuilder();
    	final URI uri = ub.replacePath("version/v2.0").build();
    	return Response.accepted().link(uri, "currentVersion").build();
    }
    
    // �汾2
    @GET
    @Path("v2.0")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt2() {
    	StringBuffer result = new StringBuffer("v2");
    	for (int i = 0; i < 100; i++) {
    		if (i % 10 == 0) {
    			result.append(">").append(i);
    		}
    	}
    	return result.toString();
    }

    // ����ͷ��Ϣѡ��汾��
    @GET
    @Path("head-version")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt3(@Context final HttpHeaders headers) {
    	String version = headers.getRequestHeaders().get("X-API-Version").get(0);
    	if (version.equals("2")) 
    		return getIt2();
    	else
    		return getIt();
    }
}
