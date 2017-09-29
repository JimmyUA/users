package com.sergey.prykhodko.exception;

public class DataNotFoundException extends UsersException{
    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
