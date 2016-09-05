package com.zhonghao.annotation.domain.jackson;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ��ʹ�� JAXB ע�����ͨ POJO
 * 
 * @author ZhongHao
 * Create on 2016��9��5�� ����11:03:46
 *
 */
@XmlRootElement
@XmlType(propOrder = {"bookId" , "bookName" , "chapters"})
public class JsonBook {

	private String[] chapters;
	
	private String bookId;
	
	private String bookName;
	
	// �ڹ������г�ʼ�� JsonBook ʵ��
    public JsonBook() {
        bookId = "1";
        bookName = "Java Restful Web Servicesʵս";
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
