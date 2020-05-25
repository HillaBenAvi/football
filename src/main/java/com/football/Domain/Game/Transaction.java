package com.football.Domain.Game;

public class Transaction {
    private String description;
    private double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString(){
        return description+"_"+amount;
    }

}
