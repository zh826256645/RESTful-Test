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
 * ����ͬ�������ء���Դ��ַ��ͬ������ GET ����
 * ��û�ж��� Accept ������£�����ֵ XML ������
 * @author ZhongHao
 * Create on 2016��9��7�� ����10:07:05
 *
 */
@Path("conneg-resource")
public class ConnegResource {

	@Path("{id}")
	@GET
	// ý������Ϊ XML
	@Produces(MediaType.APPLICATION_XML)
	public Book getJaxbBook(@PathParam("id") final Long bookId) {
		return new Book(bookId);
	}
	
	@Path("{id}")
	@GET
	// ý������Ϊ JSON
	@Produces(MediaType.APPLICATION_JSON)
	public Book getJsonBook(@PathParam("id") final Long bookId) {
		return new Book(bookId);
	}
	
    @GET
    // JSON ���������ش��� XML
    @Produces({"application/json; qs=.9", "application/xml; qs=.5"})
    @Path("book/{id}")
    public Book getBook(@PathParam("id") final Long bookId) {
        return new Book(bookId);
    }
    
    private static HashMap<String, String> helloMap = new HashMap<>();

    static {
        helloMap.put("ja", "���Ϥ褦�������ޤ�");
        helloMap.put("fr", "bonjour");
        helloMap.put("es", "hola");
        helloMap.put("zh-cn", "���");
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
