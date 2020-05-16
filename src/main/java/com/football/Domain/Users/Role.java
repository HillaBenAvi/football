package com.football.Domain.Users;

import java.util.Date;

public abstract class Role {
    private String name;
    private Date birthDate;


    public Role(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
