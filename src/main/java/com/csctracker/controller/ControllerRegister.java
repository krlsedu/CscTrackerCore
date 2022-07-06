package com.csctracker.controller;

import com.csctracker.service.ServiceRegister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ControllerRegister<E> {
    private final ServiceRegister<E> serviceRegister;

    public ControllerRegister(ServiceRegister<E> serviceRegister) {
        this.serviceRegister = serviceRegister;
    }

    @PostMapping(value = "/save", produces = "application/json", consumes = "application/json")
    public ResponseEntity<E> register(@RequestBody E entity) {
        return new ResponseEntity<>(serviceRegister.save(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", produces = "application/json", consumes = "application/json")
    public ResponseEntity<E> delete(@RequestBody E entity) {
        serviceRegister.delete(entity);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

}
