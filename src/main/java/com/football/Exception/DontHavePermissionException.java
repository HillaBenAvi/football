package com.football.Exception;

public class DontHavePermissionException extends Exception{
    public DontHavePermissionException(){
        super("you dont have permission for this action");
    }
}
