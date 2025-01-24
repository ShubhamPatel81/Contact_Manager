package com.example.Contact_manager_web.Exceptions;

public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(){
        super("Resource not Found!!! ");
    }
}
