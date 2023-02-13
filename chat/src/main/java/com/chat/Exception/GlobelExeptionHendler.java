package com.chat.Exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.webjars.NotFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobelExeptionHendler {

    //specific exception
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementExeption(NoSuchElementException ex, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails("no element found" , request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails("Not found element" , request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleNotFoundException(EmptyResultDataAccessException ex, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails("Not found element to delete" , request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
   /* @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<Void> handleStorageFileNotFound(StorageFileNotFoundException e) {
        return ResponseEntity.notFound().build();
    }*/



}

