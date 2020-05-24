package com.football.Domain.Asset;

import java.util.Observable;
import java.util.Observer;

public class PersonalPage extends Observable {

    public void addFollower (Observer follower){
        addObserver(follower);
    }

    public void notifyFollowers (String message){
        setChanged();
        notifyObservers(message);
    }

    @Override
    public String toString()
    {
        return "";
    }


}
