package com.zhonghao.annotation.param.test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.annotation.param.PathResource;

public class PathTest extends JerseyTest {
	
	private static final Logger LOGGER = Logger.getLogger(PathTest.class);
	private static final String path = "path-resource";
	
	@Override
	protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(PathResource.class);
	}
	
	@Test
	public void testUserInfo() {
		String result;
		WebTarget path1 = target(path).path("ZhongHao");
        /*http://localhost:9998/path-resource/ZhongHao*/
		result = path1.request().get().readEntity(String.class);
		Assert.assertEquals("ZhongHao:China", result);
		
        /*http://localhost:9998/path-resource/ZhongHao?hometown=Mei Zhou*/
        /*http://localhost:9998/path-resource/ZhongHao?hometown=Mei+Zhou*/
		WebTarget queryPath = path1.queryParam("hometown", "Mei Zhou");
		result = queryPath.request().get().readEntity(String.class);
		Assert.assertEquals("ZhongHao:Mei Zhou", result);
	}
	
	@Test
	public void testPunctuation() {
		String result;
        /*http://localhost:9998/path-resource/199-1999*/
		result = target(path).path("199-1999").request().get().readEntity(String.class);
		PathTest.LOGGER.debug(result);
		Assert.assertEquals("from=199:to=1999", result);
		
        /*http://localhost:9998/path-resource/01,2012-12,2014*/
        result = target(path).path("01,2012-12,2014").request().get().readEntity(String.class);
        PathTest.LOGGER.debug(result);
        Assert.assertEquals("2012.01~2014.12", result);
	}
	
	@Test
	public void testQ() {
		String result;
        /*http://localhost:9998/path-resource/q/restful;program=java;type=web*/
		result = target(path).path("q").path("restful;program=java;type=web").request().get().readEntity(String.class);
		PathTest.LOGGER.debug(result);
		Assert.assertEquals("restful program=[java] type=[web] ", result);

        /*http://localhost:9998/path-resource/q2/restful;program=java;type=web*/
		result = target(path).path("q2").path("restful;program=java;type=web").request().get().readEntity(String.class);
        PathTest.LOGGER.debug(result);
        Assert.assertEquals("restful program=[java] type=[web] ", result);
	}
	
	@Test
	public void testRegion() {
		String result;
		/*http://localhost:9998/path-resource/ZhongHao/China/southern/GuangDong/meizhou/pingyuan*/
		final WebTarget pathTarget = target(path).path("ZhongHao").path("China").path("southern").path("GuangDong").path("meizhou").path("pingyuan");
		result = pathTarget.request().get().readEntity(String.class);
		PathTest.LOGGER.debug(result);
		Assert.assertEquals("ZhongHao-China-southern-GuangDong-meizhou-pingyuan", result);
	}
}

