package com.zhonghao.client.test;

import org.junit.Test;

import com.zhonghao.client.GizzlyClient;
import com.zhonghao.client.Jaxrs2Client;

public class TestGrizzlyClient extends BasicTest {

	@Test
	public void testTalk() {
		final Jaxrs2Client one = new GizzlyClient();
		one.test();
	}
}
