package com.football.Domain.Game;
import java.util.ArrayList;

public class Account {
    private ArrayList<Transaction> transactions;
    private double amountOfTeam;

    public Account( ArrayList<Transaction> transactions, double amountOfTeam) {
        this.transactions = transactions;
        this.amountOfTeam = amountOfTeam;
    }

    public void setAmountOfTeam(double amountOfTeam){
        this.amountOfTeam =amountOfTeam;
    }

    //todo!
    public Account() {
        this.transactions = new ArrayList<Transaction>();
        amountOfTeam = 1000; //default->need to update!
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public double getAmountOfTeam() {
        return amountOfTeam;
    }
}
