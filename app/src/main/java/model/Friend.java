package model;

import java.util.ArrayList;

/**
 * Created by Zaid on 9/2/2017.
 */

public class Friend {

    private String id;
    private String name;
    private  String email;
    private String DOB;
    private double latitude;
    private double longitude;
    private  String Midpoint;
    private float friendWD;
    private float yourWD;
    private int totalWD;
    private String meetingTime;

    public Friend(String id, String name, String email, String DOB, double latitude, double longitude,String Midpoint, float friendWD, float yourWD, int totalWD,String meetingTime){

        this.id = id;
        this.name = name;
        this.email = email;
        this.DOB = DOB;
        this.latitude = latitude;
        this.longitude = longitude;
        this.Midpoint = Midpoint;
        this.friendWD = friendWD;
        this.yourWD = yourWD;
        this.totalWD = totalWD;
        this.meetingTime = meetingTime;
    }



    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }


    public String getDOB(){
        return DOB;
    }

    public  void setDOB(String DOB){
        this.DOB = DOB;
    }

    public Double getLatitude(){ return latitude; }

    public  void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public  void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public String getMidpoint(){
        return Midpoint;
    }

    public  void setMidpoint(String Midpoint){
        this.Midpoint = Midpoint;
    }

    public float getFriendWD(){ return friendWD; }

    public  void setFriendWD(float friendWD){
        this.friendWD = friendWD;
    }

    public float getyourWD(){
        return yourWD;
    }

    public  void setYourWD(float yourWD){
        this.yourWD = yourWD;
    }

    public int getTotalWD(){
        return totalWD;
    }

    public  void setTotalWD(int totalWD){ this.totalWD = totalWD;}

    public String getMeetingTime(){
        return meetingTime;
    }

    public  void setMeetingTime(String meetingTime){ this.meetingTime = meetingTime;}
}
