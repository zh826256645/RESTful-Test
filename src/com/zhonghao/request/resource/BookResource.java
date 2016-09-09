package com.zhonghao.request.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.zhonghao.request.domian.Book;
import com.zhonghao.request.domian.Books;


@Path("books")
public class BookResource {
	private static final Logger LOGGER = Logger.getLogger(BookResource.class);
	private static final HashMap<Long, Book> memoryBase;

	static {
		memoryBase = com.google.common.collect.Maps.newHashMap();
		BookResource.memoryBase.put(1L, new Book(1L, "Java Restful Web Service实战", "cmpbook"));
		BookResource.memoryBase.put(2L, new Book(2L, "JSF2和RichFaces4实战", "phei"));
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Books getBooks() {
		final List<Book> bookList = new ArrayList<Book>();
		final Collection<Book> bookCol = memoryBase.values();
		final Iterator<Book> iterator = bookCol.iterator();
		while (iterator.hasNext()) {
			bookList.add(iterator.next());
		}
		final Books books = new Books(bookList);
		LOGGER.debug(books);
		return books;
	}
	
	@Path("{bookId:[0-9]*}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Book getBookByPath(@PathParam("bookId") final Long bookId) {
		final Book book = BookResource.memoryBase.get(bookId);
		BookResource.LOGGER.debug(book);
		return book;
	}
	
    @Path("/book")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Book getBookByQuery(@QueryParam("id") final Long bookId) {
        final Book book = BookResource.memoryBase.get(bookId);
        BookResource.LOGGER.debug(book);
        return book;
    }
	
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Book saveBook(final Book book) {
        book.setBookId(System.nanoTime());
        BookResource.memoryBase.put(book.getBookId(), book);
        return book;
    }
}
