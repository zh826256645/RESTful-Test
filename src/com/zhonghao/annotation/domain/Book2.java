package com.zhonghao.annotation.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book2 {
    private Long bookId;
    private String bookName;
    private String isbn;
    private String publisher;
    private String publishTime;
    
    public Book2() {
    }

    public Book2(Long bookId, String bookName, String publisher) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.publisher = publisher;
    }

    public Book2(Long bookId, String bookName, String publisher, String isbn, String publishTime) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishTime = publishTime;
    }
    
    @XmlAttribute(name = "bookId")
	public Long getBookId() {
		return bookId;
	}
	
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	
	@XmlAttribute(name = "bookName")
	public String getBookName() {
		return bookName;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@XmlAttribute(name = "publisher")
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getPublishTime() {
		return publishTime;
	}
	
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
    
    @Override
    public String toString() {
        return String.format("%s[bookId=%d,isbn=%s,publishTime=%s,publisher=%s",
                bookName, bookId, isbn, publishTime, publisher);
    }
}
