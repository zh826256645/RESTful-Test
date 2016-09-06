package com.zhonghao.annotation.param;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;

/**
 * @Context 
 * 用来解析上下文参数
 * 
 * @author ZhongHao
 * Create on 2016年9月6日 上午9:50:57
 *
 */

@Path("context-resource")
public class ContextResource {

	@GET
	@Path("{region:.+}/GuangDong/{district:\\w+}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getByAddress(@Context final Application application 
			, @Context final Request request 
			, @Context final Providers provider 
			, @Context final UriInfo uriInfo 
			, @Context final HttpHeaders headers) {
		final StringBuilder buf = new StringBuilder();
		
		// UriInfo 获取路径信息	
		final String path = uriInfo.getPath();
		buf.append("PATH=").append(path).append("\n");
		

		final MultivaluedMap<String, String> pathMap = uriInfo.getPathParameters();
		buf.append("PATH_PARAMETERS:\n");
		iterating(buf, pathMap);
		
		// UriInfo 获取参数信息
		final MultivaluedMap<String, String> queryMap = uriInfo.getQueryParameters();
		buf.append("QUERY_PARAMETERS:\n");
		iterating(buf, queryMap);
		
		// UriInfo 获取 region 包含的所有路径信息
		final List<PathSegment> segmentList = uriInfo.getPathSegments();
		buf.append("PATH_SEGMENTS:\n");
		for(final PathSegment segment : segmentList) {
			final MultivaluedMap<String, String> matrix = segment.getMatrixParameters();
			final String segmentPath = segment.getPath();
			buf.append(matrix);
			buf.append(segmentPath);
		}
		buf.append("\nHEAD:\n");
		final MultivaluedMap<String, String> headerMap = headers.getRequestHeaders();
		iterating(buf, headerMap);
		buf.append("COOKIE:\n");
		final Map<String,Cookie> kukyMap = headers.getCookies();
		final Iterator<Entry<String,Cookie>> i = kukyMap.entrySet().iterator();
		while(i.hasNext()) {
			final Entry<String,Cookie> e = i.next();
			final String k = e.getKey();
			buf.append("key=").append(k).append(",value=");
			final Cookie cookie = e.getValue();
			buf.append(cookie.getValue()).append(" ");
			buf.append("\n");
		}
		return buf.toString();
	}
	
    private void iterating(final StringBuilder buf, final MultivaluedMap<String, String> pathMap) {
        final Iterator<Entry<String, List<String>>> i = pathMap.entrySet().iterator();
        while (i.hasNext()) {
            final Entry<String, List<String>> e = i.next();
            final String k = e.getKey();
            buf.append("key=").append(k).append(",value=");
            final List<String> vList = e.getValue();
            for (final String v : vList) {
                buf.append(v).append(" ");
            }
            buf.append("\n");
        }
    }
}
