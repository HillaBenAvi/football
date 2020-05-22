package com.football.Domain.Users;

import java.util.Date;

public abstract class Member extends Role {
    private String userMail;
    private String password;


    public Member(String name, String userMail, String password , Date birthDate) {
        super(name, birthDate);
        this.userMail = userMail;
        this.password = password;
    }

    public Member(String name, String userMail, Date birthDate) {
        super(name, birthDate);
        this.userMail = userMail;
    }
    public Member(String[] details) {
        super(details[2],new Date(Integer.parseInt(details[3].split("-")[0]),
                                Integer.parseInt(details[3].split("-")[1]),
                                Integer.parseInt(details[3].split("-")[2])));
        this.userMail = details[0];
        this.password = details[1];
    }


    public String getUserMail()
    {
        return userMail;
    }
    public String getPassword()
    {
        return password;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName (String name){
        super.setName(name);
    }

    public void setMail (String mail){
        this.userMail = mail;
    }

    public abstract String getType();
}
