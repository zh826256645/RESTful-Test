package com.zhonghao.annotation.source.impl;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.zhonghao.annotation.domain.Book;
import com.zhonghao.annotation.domain.Books;
import com.zhonghao.annotation.source.BookResource;

public class EBookResourceImpl implements BookResource {
    private final static Logger LOGGER = Logger.getLogger(EBookResourceImpl.class);
    public static AtomicLong serverBookSequence = new AtomicLong();

	// 实现类中无须使用 @GET 注解
	@Override
	public String getWeight() {
		return "150M";
	}

	@Override
	public String newBook(Book book) {
		SimpleDateFormat f = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		Date lastUpdate = Calendar.getInstance().getTime();
		LOGGER.debug(book.getBookId());
		return f.format(lastUpdate);
	}

	@Override
	public void delete(long bookId) {
        LOGGER.debug(bookId);
	}

	@Override
	public Book createBook(Book book) {
		book.setBookId(serverBookSequence.incrementAndGet());
		return book;
	}

	@Override
	public boolean moveBooks(Books books) {
		// TODO Auto-generated method stub
		return false;
	}

}
