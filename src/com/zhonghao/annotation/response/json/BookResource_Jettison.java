package com.zhonghao.annotation.response.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.zhonghao.annotation.domain.Book2;
import com.zhonghao.annotation.domain.Books;
import com.zhonghao.annotation.domain.jackson.JsonBook2;
import com.zhonghao.annotation.domain.jackson.JsonBook3;

/**
 * Jettison
 * 支持：JAXB-based JSON Binding
 *     Low-level JSON parsing & processing
 *     
 * @author ZhongHao
 * Create on 2016年9月5日 下午8:10:19
 *
 */
@Path("books-jettison")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource_Jettison {
	private static final Logger LOGGER = Logger.getLogger(BookResource_Jettison.class);
    private static final HashMap<Long, Book2> memoryBase;

    static {
        memoryBase = com.google.common.collect.Maps.newHashMap();
        memoryBase.put(1L, new Book2(1L, "JSF2和RichFaces4使用指南", "电子工业出版社", "9787121177378", "2012-09-01"));
        memoryBase.put(2L, new Book2(2L, "Java Restful Web Services实战", "机械工业出版社", "9787111478881", "2014-09-01"));
        memoryBase.put(3L, new Book2(3L, "Java EE 7 精髓", "人民邮电出版社", "9787115375483", "2015-02-01"));
        memoryBase.put(4L, new Book2(4L, "Java Restful Web Services实战II", "机械工业出版社"));
    }

    @Path("/jsonbook2")
    @GET
	public JsonBook2 getBook2() {
		final JsonBook2 book = new JsonBook2();
		LOGGER.debug(book);
		return book;
	}
    
    @Path("/jsonbook3")
    @GET
    public JsonBook3 getBook3() {
    	final JsonBook3 book = new JsonBook3();
    	LOGGER.debug(book);
    	return book;
    }
    
    @GET
    public Books getBooks() {
    	final List<Book2> booksList = new ArrayList<Book2>();
    	final Set<Map.Entry<Long, Book2>> set = memoryBase.entrySet();
    	final Iterator<Entry<Long, Book2>> iterator = set.iterator();
    	while (iterator.hasNext()) {
    		Entry<Long, Book2> entry = iterator.next();
    		LOGGER.debug(entry);
    		booksList.add(entry.getValue());
    	}
    	Books books = new Books(booksList);
    	LOGGER.debug(books);
    	return books;
    }
    
    @Path("/book/{bookId:[0-9]*}")
    @GET
    public Book2 getBook(@PathParam("bookId") final Long bookId) {
    	Book2 book = memoryBase.get(bookId);
    	LOGGER.debug(book);
    	return book;
    }
    
    @Path("/book")
    @GET
    public Book2 getBookById(@QueryParam("bookId") final Long bookId) {
    	Book2 book = memoryBase.get(bookId);
    	LOGGER.debug(book);
    	return book;
    }
}
