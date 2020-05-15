package com.football.Exception;

public class MemberNotExist extends Exception{
    public MemberNotExist(){
        super("this mail member doesnt exist in the system");
    }
}
