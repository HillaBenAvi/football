package com.football.Domain.Users;
import com.football.DataBase.DBController;
import java.util.Date;

public class AssociationDelegate extends Member {

    public AssociationDelegate(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);
    }

    @Override
    public String getType() {
        return "AssociationDelegate";
    }
}


