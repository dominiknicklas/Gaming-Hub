package com.dnicklas.gamehub.exceptions;

public class DuplicateUserException extends Exception {
    public DuplicateUserException(String message) {
        super(message);
    }
}
