package com.zhonghao.link;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.zhonghao.annotation.domain.Book;
import com.zhonghao.annotation.domain.BookWrapper;
import com.zhonghao.annotation.exception.Jaxrs2GuideNotFoundException;

/**
 * HEATEOAS
 * 用于代替聚集数据并避免描述膨胀
 * 通常使用 Atom 格式在实体字段中提供链接信息
 * 
 * @author ZhongHao
 * Create on 2016年9月6日 下午8:52:10
 *
 */
@Path("hateoas-resource")
public class HATEOASResource {
	@Context
	UriInfo uriInfo;
	
	@POST
	@Produces({MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_XML})
	public BookWrapper saveBook(final Book book) {
		final long newId = System.nanoTime();
		book.setBookId(newId);
		LinkCache.map.put(newId, book);
		// 通过 UriInfo 实例获取资源路径
		final UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		final URI uri = ub.path("" + newId).build();
		BookWrapper b = new BookWrapper();
		b.setBook(book);
		// 讲资源路径赋给资源实体
		b.setLink(uri.toString());
		return b;
	}
	
	@Path("{bookId:[0-9]*}")
	@GET
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public Book getOne(@PathParam("bookId") final long bookId) {
		final Book result = LinkCache.map.get(bookId);
        if (result == null) {
            throw new Jaxrs2GuideNotFoundException("Not found for bookId=" + bookId);
        }
        return result;
	}
}
