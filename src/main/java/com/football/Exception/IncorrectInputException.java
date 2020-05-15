package com.football.Exception;

public class IncorrectInputException extends Exception{
    public IncorrectInputException(){
        super("Incorrect input");
    }
    public IncorrectInputException(String errorMessage) {
        super(errorMessage);
    }
}
