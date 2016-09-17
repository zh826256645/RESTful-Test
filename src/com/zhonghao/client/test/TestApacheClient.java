package com.zhonghao.client.test;

import org.junit.Test;

import com.zhonghao.client.ApacheClient;
import com.zhonghao.client.Jaxrs2Client;

public class TestApacheClient extends BasicTest {
	
    @Test
    public void testTalk() {
        final Jaxrs2Client one = new ApacheClient();
        one.test();
    }
}
