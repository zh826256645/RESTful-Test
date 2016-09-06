package com.zhonghao.annotation.response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.zhonghao.exception.Jaxrs2GuideNotFoundException;

@Path("in-resource")
public class InResource {
	private static final Logger LOGGER = Logger.getLogger(InResource.class);
	
	// 基本类型
	// 当浏览器提交的数据为 text/plain 或者 text/html 时，可以使用字节数组接收
	@POST
	@Path("b")
	@Consumes({MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	public String postBytes(final byte[] bs) {
		System.out.println(bs.length);
		for (byte b : bs) {
			this.LOGGER.debug(b);
		}
		return "byte[]:" + new String(bs);
	}
	
	// 文件类型
	// 默认使用的媒体类型是 Content-type:text/html
	@POST
	@Path("f")
	public File postFile(final File f) throws FileNotFoundException, IOException {
		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
			String s;
			do {
				s = br.readLine();
				LOGGER.debug(s);
			} while (s != null);
			return f;
		}
	}
	
	// 字节流类型
	@POST
	@Path("bio")
	public String postStream(final InputStream in) throws IOException {
		try(BufferedReader br = new BufferedReader(
				// 需要设置编码
				new InputStreamReader(in,"utf-8"))) {
			StringBuilder result = new StringBuilder();
			String s = br.readLine();
			while(s != null) {
				result.append(s).append("\n");
				LOGGER.debug(s);
				s = br.readLine();
			}
			return result.toString();
		}
	}
	
	// 字符流类型
	@POST
	@Path("cio")
	public Response postChars(final Reader r) throws IOException {
		try(BufferedReader br = new BufferedReader(r)) {
            StringBuilder result = new StringBuilder();
			String s = br.readLine();
			if(s == null) {
				throw new Jaxrs2GuideNotFoundException("NOT FOUND FROM READER");
			}
			while(s != null) {
                result.append(s).append("\n");
				LOGGER.debug(s);
				s = br.readLine();
			}
			return Response.ok().entity(result.toString()).build();
		}
	}
}
