package com.zhonghao.annotation.param.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.annotation.param.FormResource;


public class FormTest extends JerseyTest {

    private static final Logger LOGGER = Logger.getLogger(FormTest.class);

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(FormResource.class);
    }
    
    @Test
    public void testPost2() {
    	final Form form = new Form();
    	form.param(FormResource.PW , "����" );
        form.param(FormResource.NPW, "�Ϻ�");
        form.param(FormResource.VNPW, "�Ϻ�");
        final String result = target("form-resource")
        		.request().post(Entity.entity(
        				form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),String.class);
        FormTest.LOGGER.debug(result);
        Assert.assertEquals("encoded should let it to disable decoding", "ZhongHao:%E5%8C%97%E4%BA%AC:%E4%B8%8A%E6%B5%B7:�Ϻ�", result);
        
    }
}
