package com.zhonghao.annotation.response.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.annotation.response.InResource;

public class InputTest extends JerseyTest {
    private static final Logger LOGGER = Logger.getLogger(InputTest.class);
    final String path = "in-resource";
    String result;
    
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(InResource.class);
    }

    @Test
    public void testBytes() {
    	final String message = "TEST STRING";
    	final Builder request = target(path).path("b").request();
    	final Response response = request.post(Entity.entity(message, MediaType.TEXT_PLAIN_TYPE),Response.class);
        //final Response response = request.post(Entity.entity(message, MediaType.TEXT_HTML_TYPE), Response.class);
        result = response.readEntity(String.class);
        LOGGER.debug(result);
        Assert.assertEquals("byte[]:" + message, result);
    }
    
    @Test
    public void testFile() throws IOException {
    	// 获取文件全路径
    	final URL resource = getClass().getClassLoader().getResource("gua.txt");
    	// assert 断言关键字
    	assert resource != null;
    	
    	// 构建 File 实例
    	final String file = resource.getFile();
    	final File f = new File(file);
    	final Builder request = target(path).path("f").request();
    	
    	// 提交 POST 请求
    	Entity<File> e = Entity.entity(f, MediaType.TEXT_PLAIN_TYPE);
    	final Response response = request.post(e,Response.class);
    	// 获得返回值
    	File result = response.readEntity(File.class);
    	try(BufferedReader br = new BufferedReader(new FileReader(result))) {
    		String s; 
    		do {
    			s = br.readLine();
    			LOGGER.debug(s);
    			} while(s != null);
    	}
    }
    
    @Test
    public void testStream() {
    	final InputStream resource = getClass().getClassLoader().getResourceAsStream("gua.txt");
    	final Builder request = target(path).path("bio").request();
    	Entity<InputStream> e = Entity.entity(resource, MediaType.TEXT_PLAIN_TYPE);
    	result = request.post(e).readEntity(String.class);
    	LOGGER.debug(request);
    }
    
    @Test
    public void testReader() {
    	ClassLoader classLoader = getClass().getClassLoader();
    	final Reader resource = new InputStreamReader(classLoader.getResourceAsStream("gua.txt"));
    	final Builder request = target(path).path("cio").request();
    	Entity<Reader> e = Entity.entity(resource, MediaType.TEXT_PLAIN_TYPE);
    	final Response response = request.post(e,Response.class);
    	result = response.readEntity(String.class);
    	LOGGER.debug(result);
    }
}
