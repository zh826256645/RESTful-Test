package com.zhonghao.annotation.response.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.zhonghao.annotation.domain.Book2;
import com.zhonghao.annotation.domain.Books;
import com.zhonghao.annotation.domain.jackson.JsonBook;
import com.zhonghao.annotation.domain.jackson.JsonHybridBook;
import com.zhonghao.annotation.domain.jackson.JsonNoJaxbBook;

@Path("books-jackson")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource_Jackson {
	private static final Logger LOGGER = Logger.getLogger(BookResource_Jackson.class);

    private static final HashMap<Long, Book2> memoryBase;

    static {
        memoryBase = com.google.common.collect.Maps.newHashMap();
        memoryBase.put(1L, new Book2(1L, "JSF2和RichFaces4使用指南", "电子工业出版社", "9787121177378", "2012-09-01"));
        memoryBase.put(2L, new Book2(2L, "Java Restful Web Services实战", "机械工业出版社", "9787111478881", "2014-09-01"));
        memoryBase.put(3L, new Book2(3L, "Java EE 7 精髓", "人民邮电出版社", "9787115375483", "2015-02-01"));
        memoryBase.put(4L, new Book2(4L, "Java Restful Web Services实战II", "机械工业出版社"));
    }
	
    @Path("/emptybook")
    @GET
	public JsonBook getEmptyArrayBook() {
		final JsonBook book = new JsonBook();
		LOGGER.debug(book);
		return book;
	}
    
    
    @Path("/hybridbook")
    @GET
    public JsonHybridBook getHybridBook() {
    	final JsonHybridBook book = new JsonHybridBook();
    	LOGGER.debug(book);
    	return book;
    }
    
    @Path("/nojaxbbook")
    @GET
    public JsonNoJaxbBook getNoJaxbBook() {
    	final JsonNoJaxbBook book = new JsonNoJaxbBook();
    	LOGGER.debug(book);
    	return book;
    }
    
    @GET
    public Books getBooks() {
    	final List<Book2> bookList = new ArrayList<Book2>();
    	final Set<Map.Entry<Long, Book2>> entries = memoryBase.entrySet();
    	final Iterator<Entry<Long, Book2>> iterator = entries.iterator();
    	while (iterator.hasNext()) {
    		final Entry<Long, Book2> cursor = iterator.next();
    		LOGGER.debug(cursor);
    		bookList.add(cursor.getValue());
    	}
    	final Books books = new Books(bookList);
    	LOGGER.debug(books);
    	return books;
    }
}
