package com.football.Exception;

public class IncorrectPasswordInputException extends IncorrectInputException {

    public IncorrectPasswordInputException() {
        super("you entered incorrect password");
    }
}
