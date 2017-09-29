package com.sergey.prykhodko.exception;

public class UsersException extends RuntimeException{
    public UsersException() {
    }

    public UsersException(String message) {
        super(message);
    }
}
