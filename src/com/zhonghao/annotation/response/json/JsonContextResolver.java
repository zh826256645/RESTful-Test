package com.zhonghao.annotation.response.json;

import org.glassfish.jersey.jettison.JettisonConfig;
import org.glassfish.jersey.jettison.JettisonJaxbContext;

import com.zhonghao.annotation.domain.Book2;
import com.zhonghao.annotation.domain.Books;
import com.zhonghao.annotation.domain.jackson.JsonBook2;
import com.zhonghao.annotation.domain.jackson.JsonBook3;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

@Provider
public class JsonContextResolver implements ContextResolver<JAXBContext> {
    private final JAXBContext context1;
    private final JAXBContext context2;

    @SuppressWarnings("rawtypes")
    public JsonContextResolver() throws Exception {
        Class[] clz = new Class[]{JsonBook2.class, JsonBook3.class, Books.class, Book2.class};
        this.context1 = new JettisonJaxbContext(JettisonConfig.DEFAULT, clz);
        this.context2 = new JettisonJaxbContext(JettisonConfig.badgerFish().build(), clz);
    }

    @Override
    public JAXBContext getContext(Class<?> objectType) {
        if (objectType == JsonBook3.class) {
            return context2;
        } else {
            return context1;
        }
    }
}