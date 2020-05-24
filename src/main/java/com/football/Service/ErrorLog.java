package com.football.Service;

public class ErrorLog {

    private int id;
    private String topic;
    private String timeStamp;

    public ErrorLog(int id, String topic, String timeStamp){
        this.id = id;
        this.topic = topic;
        this.timeStamp = timeStamp;
    }
    @Override
    public String toString()
    {
        String str="";
        str="\'"+this.id+"\',\'"+this.topic+"\',\'"+this.timeStamp;
        return str;
    }

    public String getId() {
        return String.valueOf(id);
    }
}
