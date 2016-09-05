package com.zhonghao.annotation.domain.jackson;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 将 JAXB 的注解和 Jackson 提供的注解混合使用的 POJO
 * 
 * @author ZhongHao
 * Create on 2016年9月5日 上午11:05:23
 *
 */

// JAXB 注解
@XmlRootElement
public class JsonHybridBook {


	// Jackson 注解
	@JsonProperty("bookId")
	private String bookId;
	
	@JsonProperty("bookName")
	private String bookName;

	// 在构造器中初始化 JsonHybridBook 实例
    public JsonHybridBook() {
        bookId = "2";
        bookName = "Java Restful Web Services实战";
    }
    
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}
