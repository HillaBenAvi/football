package com.football.Exception;

public class PasswordDontMatchException extends Exception {
    public PasswordDontMatchException(){
        super("incorrect password");
    }
}
