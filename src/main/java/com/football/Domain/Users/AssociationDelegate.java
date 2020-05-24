package com.football.Domain.Users;

import java.util.Date;

public class AssociationDelegate extends Member {



    public AssociationDelegate(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);

    }

    public AssociationDelegate(String[] associationDelegateDetails) {
        super(associationDelegateDetails);
    }

    @Override
    public String getType() {
        return "0AssociationDelegate";
    }
    @Override
    public String toString() {
        String str="";
        str="\'"+this.getUserMail()+"\',\'"+this.getPassword()+"\',\'"+this.getName()+"\',\'"+this.getBirthDateString()+"\'";
        return str;
    }
}

