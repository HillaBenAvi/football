package com.football.Domain.Asset;

public class Payment {

    String teamName;
    String date;
    double amount;


    public Payment(String teamName , String date , double amount)
    {
        this.teamName=teamName;
        this.date=date;
        this.amount=amount;
    }

    @Override
    public String toString ()
    {
        String str="";
        str="\'"+this.teamName+"\',\'"+this.date+"\',\'"+this.amount+"\'";
        return str;
    }
}
