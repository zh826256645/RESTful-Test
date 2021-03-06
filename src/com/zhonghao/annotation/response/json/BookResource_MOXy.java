package com.zhonghao.annotation.response.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.zhonghao.annotation.domain.Book2;
import com.zhonghao.annotation.domain.Books;

/**
 * MOXy
 * 使用 MOXy 解析 JSON
 * 支持： POJO-based JSON Binding 
 * 	   JAXB-based JSON Binding 
 * 
 * @author ZhongHao
 * Create on 2016年9月5日 上午10:46:21
 *
 */
@Path("books-moxy")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({"application/x-javascript;charset=UTF-8","application/json;charset=UTF-8"})
public class BookResource_MOXy {
	private static final Logger LOGGER = Logger.getLogger(BookResource_MOXy.class);
	private static final HashMap<Long,Book2> memoryBase;
	
	// 初始化 Map 集合
    static {
        memoryBase = com.google.common.collect.Maps.newHashMap();
        memoryBase.put(1L, new Book2(1L, "JSF2和RichFaces4使用指南", "电子工业出版社", "9787121177378", "2012-09-01"));
        memoryBase.put(2L, new Book2(2L, "Java Restful Web Services实战", "机械工业出版社", "9787111478881", "2014-09-01"));
        memoryBase.put(3L, new Book2(3L, "Java EE 7 精髓", "人民邮电出版社", "9787115375483", "2015-02-01"));
        memoryBase.put(4L, new Book2(4L, "Java Restful Web Services实战II", "机械工业出版社"));
    }
    
    @GET
    public Books getBooks() {
    	final List<Book2> bookList = new ArrayList<Book2>();
    	final Set<Map.Entry<Long, Book2>> entries = BookResource_MOXy.memoryBase.entrySet();
    	final Iterator<Entry<Long, Book2>> iterator = entries.iterator();
    	while (iterator.hasNext()) {
    		final Entry<Long, Book2> cursor = iterator.next();
    		BookResource_MOXy.LOGGER.debug(cursor.getKey());
    		bookList.add(cursor.getValue());
    	}
    	final Books books = new Books(bookList);
    	BookResource_MOXy.LOGGER.debug(books);
    	return books;
    }
}
