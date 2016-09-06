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
 * ���ڴ���ۼ����ݲ�������������
 * ͨ��ʹ�� Atom ��ʽ��ʵ���ֶ����ṩ������Ϣ
 * 
 * @author ZhongHao
 * Create on 2016��9��6�� ����8:52:10
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
		// ͨ�� UriInfo ʵ����ȡ��Դ·��
		final UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		final URI uri = ub.path("" + newId).build();
		BookWrapper b = new BookWrapper();
		b.setBook(book);
		// ����Դ·��������Դʵ��
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
