package com.zhonghao.link;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.zhonghao.annotation.domain.Book;
import com.zhonghao.annotation.exception.Jaxrs2GuideNotFoundException;

/**
 * Web Link
 * 通过使用 HTTP 的头信息来传递操作链接
 * 
 * @author ZhongHao
 * Create on 2016年9月6日 上午9:48:32
 *
 */
@Path("weblink-resource")
public class WebLinkResource {

	// @Context 用来解析上下文参数
	@Context
	UriInfo uriInfo;
	
    @Path("{bookId:[0-9]*}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Book getOne(@PathParam("bookId") final Long bookId) {
        final Book result = LinkCache.map.get(bookId);
        if (result == null) {
            throw new Jaxrs2GuideNotFoundException("Not found for bookId=" + bookId);
        }
        return result;
    }

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response saveBook(final Book book) {
		final long newId = System.nanoTime();
		book.setBookId(newId);
		System.out.println(book);

		// 通过 UriInfo 实例获取资源路径
		final UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		final URI location = ub.path("" + newId).build();
		// 通过模板获取资源路径
		final String uriTemplate = "http://{host}:{port}/{path}/{param}";
		final URI location2 = UriBuilder.fromUri(uriTemplate)
				.resolveTemplate("host", "localhost")
				.resolveTemplate("port", "9998")
				.resolveTemplate("path", "weblink-resource")
				.resolveTemplate("param", newId)
				.build();
		System.out.println(location2.toString());
		// 通过模板方法获取资源路径
		final UriBuilder ub3 = uriInfo.getAbsolutePathBuilder();
		final URI location3 = ub3.scheme("http").host("localhost").port(9998)
			.path("" + newId).build();
		// 为响应实例添加路径信息
		Response response =  Response.created(location)
				.link(location2, "view1")
				.link(location3, "view2")
				.entity(book).build();
		return response;
	}
}
