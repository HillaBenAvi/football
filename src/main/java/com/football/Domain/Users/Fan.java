package com.football.Domain.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Fan extends Member implements Observer {

    private ArrayList<String> updates;

    public Fan(String name, String mail, String password, Date birthDate) {
        super(name, mail, password, birthDate);
        updates = new ArrayList<>();
    }

    public Fan(String[] fanDetails) {
        super(fanDetails);
        this.updates = new ArrayList<>();
        if(fanDetails[4]!=null && !fanDetails[4].equals("null")&& !fanDetails[4].equals("")){
            String[] updatesList = fanDetails[4].split(";");
            for( int i = 0 ; i<updatesList.length ; i++){
                this.updates.add(updatesList[i]);
            }
        }
    }

    public ArrayList<String> getUpdates(){
        return updates;
    }

    @Override
    // the arg is the message recieved from the observable
    public void update(Observable o, Object message) {
        updates.add("new update:" + message.toString());
    }

    @Override
    public String getType() {
        return "0Fan";
    }

    @Override
    public String toString() {
        String str="";
        str="\'"+this.getUserMail()+"\',\'"+this.getPassword()+"\',\'"+this.getName()+"\',\'"+this.getBirthDateString()+"\',\'"+this.getUpdatesString()+"\'";
        return str;
    }

    private String getUpdatesString() {
        String str="";
        if(updates!=null && updates.size()!=0){
            for (String update:updates) {
                str+=update+";";
            }
            if(str.length()>0)
            {
                str=str.substring(0,str.length()-1);
            }
        }
        else{
            str+=null;
        }
        return str;
    }
}
