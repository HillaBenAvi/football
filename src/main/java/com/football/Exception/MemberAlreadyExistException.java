package com.football.Exception;

public class MemberAlreadyExistException extends Exception{
    public MemberAlreadyExistException(){
        super("this member already exist in the system with this mail");
    }
}
