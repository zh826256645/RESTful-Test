package com.zhonghao.conneg;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.zhonghao.annotation.domain.Book;

/**
 * 两个同质量因素、资源地址相同的两个 GET 方法
 * 在没有定义 Accept 的情况下，返回值 XML 的优先
 * @author ZhongHao
 * Create on 2016年9月7日 上午10:07:05
 *
 */
@Path("conneg-resource")
public class ConnegResource {

	@Path("{id}")
	@GET
	// 媒体类型为 XML
	@Produces(MediaType.APPLICATION_XML)
	public Book getJaxbBook(@PathParam("id") final Long bookId) {
		return new Book(bookId);
	}
	
	@Path("{id}")
	@GET
	// 媒体类型为 JSON
	@Produces(MediaType.APPLICATION_JSON)
	public Book getJsonBook(@PathParam("id") final Long bookId) {
		return new Book(bookId);
	}
	
    @GET
    // JSON 的质量因素大于 XML
    @Produces({"application/json; qs=.9", "application/xml; qs=.5"})
    @Path("book/{id}")
    public Book getBook(@PathParam("id") final Long bookId) {
        return new Book(bookId);
    }
    
    private static HashMap<String, String> helloMap = new HashMap<>();

    static {
        helloMap.put("ja", "おはようございます");
        helloMap.put("fr", "bonjour");
        helloMap.put("es", "hola");
        helloMap.put("zh-cn", "你好");
    }
    

    @GET
    @Produces("text/*")
    public String getHello(@Context HttpHeaders headers) {
        final MultivaluedMap<String, String> headerMap = headers.getRequestHeaders();
        //simple way to presentation, maybe, more values
        String lang = headerMap.get("content-language").get(0);
        String hello = helloMap.get(lang);
        if (hello != null) {
            return hello;
        } else {
            return "Hello";
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public Book getEntity(Book book) {
    	return book;
    }
}
