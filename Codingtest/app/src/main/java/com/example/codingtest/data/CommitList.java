package com.example.codingtest.data;
import android.util.Log;
public class CommitList
{
    String name;
    String message;
    String avatar;
    String date;
    String id;
    public CommitList(String name, String message){

        this.name = name;
        this.message = message;
    }
    public CommitList(String name, String message, String avatar, String date, String id){
        Log.d("iqbal", "called3");
        this.name = name;
        this.message = message;
        this.avatar = avatar;
        this.date = date;
        this.id = id;

    }

    public String getMessage(){ return message;}
    public String getName(){ return name;}
    public String getAvatar(){ return avatar;}
    public String getDate(){ return date;}
    public String getId(){return id; }
}

