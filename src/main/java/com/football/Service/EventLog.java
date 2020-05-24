package com.football.Service;

public class EventLog {

    private int id;
    private String timeStamp;
    private String userId;
    private String actionName;

    public EventLog(int id, String timeStamp, String userId, String actionName) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.actionName = actionName;
    }

    @Override
    public String toString()
    {
        String str="";
        str="\'"+this.id+"\',\'"+this.timeStamp+"\',\'"+this.userId+"\',\'"+this.actionName;
        return str;
    }

    public String getId() {
        return String.valueOf(id);
    }
}
