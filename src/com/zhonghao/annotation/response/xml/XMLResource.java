package com.zhonghao.annotation.response.xml;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.zhonghao.annotation.domain.Book;

@Path("xml-resource")
public class XMLResource {
	private static final Logger LOGGER = Logger.getLogger(XMLResource.class);

	// JAXP ��׼�е� StAX ��ʽ����������
	@POST
	@Path("stream")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public StreamSource getStreamSource(StreamSource streamSource) {
		return streamSource;
	}
	
	// JAXP ��׼�е� SAX �¼�����������������
	@POST
	@Path("sax")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public SAXSource getSAXSource(SAXSource saxSource) {
		return saxSource;
	}
	
	// JAXP ��׼�е� DOM �����ĵ���������
	@POST
	@Path("dom")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public DOMSource getDOMSource(DOMSource domSource) {
		return domSource;
	}
	
	// JAXP ��׼�е� DOM �����ĵ���������
	@POST
	@Path("doc")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Document getDocument(Document document) {
		return document;
	}
	
	// JAXB ��׼��ʹ�� JAXBElement ��Ϊ REST �����Ĳ���
	@POST
	@Path("jaxb")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Book getEntity(JAXBElement<Book> bookElement) {
		Book book = bookElement.getValue();
		LOGGER.debug(book.getBookName());
		return book;
	}
	
	// JAXB ��׼��ʹ�� POJO ��Ϊ REST �����Ĳ���
	@POST
	@Consumes({MediaType.APPLICATION_XML , MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_XML)
	public Book getEntity(Book book) {
		LOGGER.debug(book.getBookName());
		return book;
	}
}
