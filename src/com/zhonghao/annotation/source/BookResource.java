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
	
	// 使用 @GET 注解接口中的方法
	@GET
	public String getWeight();
	
	// PUT 方法
	@PUT
	// 资源方法定义了 Produces 注解和 Consumes 注解
	// @Produces 定义服务器端产生响应的媒体类型
	@Produces(MediaType.TEXT_PLAIN)
	// @Consumes 定义了请求实体媒体类型
	@Consumes(MediaType.APPLICATION_XML)
	public String newBook(Book book);
	
	// DELETE 方法
	// 返回值可以定义为 void
	@DELETE
	public void delete(@QueryParam("bookId") final long bookId);
	
	// POST 方法
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Book createBook(Book book);
	
	public boolean moveBooks(Books books);
}
