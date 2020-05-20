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
    protected String getBirthDateString() {
        int year=birthDate.getYear();
        int month=birthDate.getMonth();
        int day=birthDate.getDate();
        return year+"-"+month+"-"+day;
       // return birthDate.getYear()+"-"+birthDate.getMonth()+"-"+birthDate.getDay();
    }
}
