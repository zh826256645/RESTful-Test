package com.zhonghao.annotation.domain.jackson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonBook3 {
    private String bookId;
    private String bookName;

    public JsonBook3() {
        bookId = "1";
        bookName = "Java Restful Web Servicesʵս";
    }

    @XmlElement
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @XmlElement
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
