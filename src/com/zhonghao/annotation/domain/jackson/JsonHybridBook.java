package com.zhonghao.annotation.domain.jackson;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * �� JAXB ��ע��� Jackson �ṩ��ע����ʹ�õ� POJO
 * 
 * @author ZhongHao
 * Create on 2016��9��5�� ����11:05:23
 *
 */

// JAXB ע��
@XmlRootElement
public class JsonHybridBook {


	// Jackson ע��
	@JsonProperty("bookId")
	private String bookId;
	
	@JsonProperty("bookName")
	private String bookName;

	// �ڹ������г�ʼ�� JsonHybridBook ʵ��
    public JsonHybridBook() {
        bookId = "2";
        bookName = "Java Restful Web Servicesʵս";
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
