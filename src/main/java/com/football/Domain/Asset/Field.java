package com.football.Domain.Asset;

import com.football.Domain.Game.Team;

public class Field{
    private String nameOfField;
    private Team team;

    public Field(String nameOfField) {
        this.nameOfField = nameOfField;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getNameOfField() {
        return nameOfField;
    }

    public Object getName() {
        return this.nameOfField;
    }

    @Override
    public String toString() {
        String str="";
        if(team!=null) {
            str = "\'" + this.nameOfField + "\',\'" + this.team.toString() + "\'";
        }
        else
        {
            str = "\'" + this.nameOfField + "\'";
        }
        return str;
    }
}
