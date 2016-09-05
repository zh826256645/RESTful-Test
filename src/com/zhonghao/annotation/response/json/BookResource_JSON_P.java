package com.zhonghao.annotation.response.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;

@Path("books-json-p")
public class BookResource_JSON_P {

	private static final Logger LOGGER = Logger.getLogger(BookResource_JSON_P.class);
	private static final HashMap<Long, JsonObject> memoryBase;

	// 初始化
	static{
		memoryBase = com.google.common.collect.Maps.newHashMap();
		// 构建 JsonObjectBuilder 实例
		JsonObjectBuilder jsonObjectBuilder  = Json.createObjectBuilder();
		// 构建 JsonObject 实例
		JsonObject newBook1 = jsonObjectBuilder.add("bookId", 1)
			.add("bookName", "Java Restful Web Services实战")
			.add("publisher", "机械工业出版社")
			.add("isbn", "9787111478881")
			.add("publishTime", "2014-09-01").build();
		memoryBase.put( 1L, newBook1);
        memoryBase.put(2L, jsonObjectBuilder.add("bookId", 2).add("bookName", "JSF2和RichFaces4使用指南")
                .add("publisher", "电子工业出版社").add("isbn", "9787121177378")
                .add("publishTime", "2012-09-01").build());
        memoryBase.put(3L, jsonObjectBuilder.add("bookId", 3).add("bookName", "Java EE 7 精髓")
                .add("publisher", "人民邮电出版社").add("isbn", "9787115375483")
                .add("publishTime", "2015-02-01").build());
        memoryBase.put(4L, jsonObjectBuilder.add("bookId", 4).add("bookName", "Java Restful Web Services实战II")
                .add("publisher", "机械工业出版社").build());
	}
	
	@GET
	public JsonArray getBooks() {
		// 构建 JsonArrayBuilder 实例
		final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		final Set<Map.Entry<Long, JsonObject>> entries = memoryBase.entrySet();
		final Iterator<Entry<Long, JsonObject>> iterator = entries.iterator();
		while (iterator.hasNext()) {
            final Entry<Long, JsonObject> cursor = iterator.next();
            Long key = cursor.getKey();
            JsonObject value = cursor.getValue();
            LOGGER.debug(key);
            jsonArrayBuilder.add(value);
		}
		JsonArray result = jsonArrayBuilder.build();
		return result;
	}
	
	@GET
	@Path("/book")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getBook(@QueryParam("id") final Long bookId) {
		final JsonObject book = BookResource_JSON_P.memoryBase.get(bookId);
		LOGGER.debug(book);
		return book;
	}
	
	@POST
	public JsonObject saveBook(final JsonObject book) {
		long id = System.nanoTime();
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		JsonObject newBook = jsonObjectBuilder.add("bookId", id)
			.add("bookName", book.getString("bookName"))
			.add("publisher", book.get("publisher"))
			.build();
		LOGGER.debug(id);
		memoryBase.put(id, newBook);
        return newBook;
	}
}
