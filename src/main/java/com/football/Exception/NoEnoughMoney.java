package com.football.Exception;

public class NoEnoughMoney extends Exception {
    public NoEnoughMoney(){
        super("this team does not have enough money");
    }
}
