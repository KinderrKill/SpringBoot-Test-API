package com.kinder.FirstAPI.exception;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException() {
        super("Email adress already used!");
    }
}
