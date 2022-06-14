package com.csctracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;
import java.util.stream.Collectors;

public class Conversor<E, D> {
    private final ObjectMapper objectMapper;
    private final Class<E> eClass;
    private final Class<D> dClass;

    public Conversor(Class<E> eClass, Class<D> dClass) {
        this.eClass = eClass;
        this.dClass = dClass;
        objectMapper = new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public List<E> toE(List<D> ds) {
        return ds.stream().map(this::toE).collect(Collectors.toList());
    }

    public E toE(D d) {
        return objectMapper.convertValue(d, eClass);
    }

    public List<D> toD(List<E> es) {
        return es.stream().map(this::toD).collect(Collectors.toList());
    }

    public D toD(E e) {
        return objectMapper.convertValue(e, dClass);
    }

    public List<D> toDList(String d) throws JsonProcessingException {
        return objectMapper.readValue(d, objectMapper.getTypeFactory().constructCollectionType(List.class, dClass));
    }

    public D toD(String d) throws JsonProcessingException {
        return objectMapper.readValue(d, dClass);
    }

    public D toD(String t, Class clazz) {
        return objectMapper.convertValue(t, dClass);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
