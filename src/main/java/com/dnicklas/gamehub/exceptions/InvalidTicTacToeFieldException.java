package com.dnicklas.gamehub.exceptions;

public class InvalidTicTacToeFieldException extends RuntimeException {
    public InvalidTicTacToeFieldException(String message) {
        super(message);
    }

    public InvalidTicTacToeFieldException() {
    }
}
