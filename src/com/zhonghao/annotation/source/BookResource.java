package com.zhonghao.annotation.source;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.zhonghao.annotation.domain.Book;
import com.zhonghao.annotation.domain.Books;

@Path("book")
public interface BookResource {
	
	// ʹ�� @GET ע��ӿ��еķ���
	@GET
	public String getWeight();
	
	// PUT ����
	@PUT
	// ��Դ���������� Produces ע��� Consumes ע��
	// @Produces ����������˲�����Ӧ��ý������
	@Produces(MediaType.TEXT_PLAIN)
	// @Consumes ����������ʵ��ý������
	@Consumes(MediaType.APPLICATION_XML)
	public String newBook(Book book);
	
	// DELETE ����
	// ����ֵ���Զ���Ϊ void
	@DELETE
	public void delete(@QueryParam("bookId") final long bookId);
	
	// POST ����
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Book createBook(Book book);
	
	public boolean moveBooks(Books books);
}
