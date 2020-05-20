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
}
