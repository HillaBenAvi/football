package com.football.Domain.Users;

import java.util.Date;

public class AssociationDelegate extends Member {



    public AssociationDelegate(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);

    }

    public AssociationDelegate(String[] associationDelegateDetails) {
        super(associationDelegateDetails[1],associationDelegateDetails[0],associationDelegateDetails[2],
                new Date(Integer.parseInt(associationDelegateDetails[3]),
                        Integer.parseInt(associationDelegateDetails[4]),
                        Integer.parseInt(associationDelegateDetails[5])));

    }

    @Override
    public String getType() {
        return "AssociationDelegate";
    }
    @Override
    public String toString() {
        String str="";
        str="\'"+this.getUserMail()+"\',\'"+this.getPassword()+"\',\'"+this.getName()+"\',\'"+this.getBirthDateString()+"\'";
        return str;
    }
}

