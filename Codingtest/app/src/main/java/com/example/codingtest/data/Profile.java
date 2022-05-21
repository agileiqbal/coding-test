package com.example.codingtest.data;

public class Profile {
    String id = "justinmc";
    String name = "Justin McCandless";

    public Profile(){
       id = "justinmc";
       name = "Justin McCandless";
    }
    public void setId(String id){
       this.id = id;
    }
    public String getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
