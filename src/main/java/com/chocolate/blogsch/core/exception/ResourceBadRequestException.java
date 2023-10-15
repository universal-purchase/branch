package com.chocolate.blogsch.core.exception;

public class ResourceBadRequestException extends RuntimeException {

    public ResourceBadRequestException(){
        super();
    }

    public ResourceBadRequestException(String message){
        super(message);
    }
}
