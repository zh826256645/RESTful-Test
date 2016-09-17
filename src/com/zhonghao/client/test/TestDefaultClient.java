package com.zhonghao.client.test;

import org.junit.Test;

import com.zhonghao.client.DefaultClient;
import com.zhonghao.client.Jaxrs2Client;

public class TestDefaultClient extends BasicTest {

	@Test
	public void testTalk() {
		final Jaxrs2Client one = new DefaultClient();
		one.test();
	}
}
