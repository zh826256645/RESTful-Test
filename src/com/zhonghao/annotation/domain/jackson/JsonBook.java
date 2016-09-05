package com.zhonghao.annotation.domain.jackson;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 仅使用 JAXB 注解的普通 POJO
 * 
 * @author ZhongHao
 * Create on 2016年9月5日 上午11:03:46
 *
 */
@XmlRootElement
@XmlType(propOrder = {"bookId" , "bookName" , "chapters"})
public class JsonBook {

	private String[] chapters;
	
	private String bookId;
	
	private String bookName;
	
	// 在构造器中初始化 JsonBook 实例
    public JsonBook() {
        bookId = "1";
        bookName = "Java Restful Web Services实战";
        chapters = new String[0];
    }

	public String[] getChapters() {
		return chapters;
	}

	public void setChapters(String[] chapters) {
		this.chapters = chapters;
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
