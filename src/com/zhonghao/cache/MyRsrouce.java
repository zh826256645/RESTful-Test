package com.zhonghao.cache;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * ���� GET
 * ʹ�� If-Modified-Since ���� If-None-Match ͷ��Ϣ�� GET ���󷽷����л��洦���һ�ַ���
 * 
 * @author �Ӻ�
 * Create on 2016��9��20�� ����4:06:58
 *
 */
@Path("cache")
public class MyRsrouce {
	
	static ConcurrentHashMap<String, Date> map = new ConcurrentHashMap<>();
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            if (i % 30 == 0) {
                result.append(">").append(i);
            }
        }
        return result.toString();
    }
	
    // ʹ�� If-Modified ���洦���ʶ
	@GET
	@Path("last_modified")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getItWithLastModified(@QueryParam("userId") String userId, @Context Request request) {
		// CacheControl ���� HTTP ͷ��Ϣ Cache-Control ��Ӧ
		CacheControl cacheControl = new CacheControl();
		// ������󻺴�ʱ��
		cacheControl.setMaxAge(1200);
		// ���ÿͻ���ǿ�Ʋ�����
		// cacheControl.setMustRevalidate(true);
		Date lastModified = map.get(userId);
		Response.ResponseBuilder rb = null;
		if (lastModified != null) {
			// �ַ���ʱ�䳬����������ʱ��  + ��󻺴�ʱ�䡱���᷵�� null
			rb = request.evaluatePreconditions(lastModified);
		}
		// ��� ResponseBuilder ʵ����Ϊ�ռ���������δʧЧ
		// �������»�ȡ���ݲ����� CacheControl �� lastModified ��Ӧͷ��Ϣ
		if (rb != null) {
			return rb.cacheControl(cacheControl).build();
		} else {
			// ����������ʱ��
			Date date = new Date();
			map.put(userId, date);
			return Response.ok(getIt()).cacheControl(cacheControl).lastModified(date).build();
		}
	}
	
	// ʹ��
	@GET
	@Path("e_tag")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getItWithETag(@QueryParam("userId") String userId, @Context Request request) {
		Response.ResponseBuilder rb;
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(1200);
		// EntityTag ���� HTTP ͷ��Ϣ ETag ��Ӧ
		// ʹ���û� ID �� hashcode ��Ϊ ETage ��ֵ
		EntityTag tag = new EntityTag(userId.hashCode() + "");
		rb = request.evaluatePreconditions(tag);
		// ��� ResponseBuilder ʵ����Ϊ�ռ���������δʧЧ
		// �������»�ȡ���ݲ����� CacheControl �� ETag ��Ӧͷ��Ϣ
		if (rb != null) {
			return rb.cacheControl(cacheControl).build();
		} else {
			// ��� HTTP ͷ��Ϣ�����ú󣬿������� Response ʵ������Ϊ��Ӧͷ��Ϣ
			return Response.ok(getIt()).cacheControl(cacheControl).tag(tag).build();
		}
	}
}
