package com.football.Service;

public class Notification {

    private String gameId;
    private String notificationID;
    private String registerMembers;

    public Notification(String gameId, String notificationID, String registerMembers) {
        this.gameId = gameId;
        this.notificationID = notificationID;
        this.registerMembers = registerMembers;
    }

    @Override
    public String toString()
    {
        String str="";
        str="\'"+this.gameId+"\',\'"+this.notificationID+"\',\'"+this.registerMembers;
        return str;
    }




}
