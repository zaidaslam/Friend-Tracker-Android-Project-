package model;

import java.util.ArrayList;

/**
 * Created by Zaid on 9/2/2017.
 */

public class Meeting {

    private String id;
    private String title;
    private String startTime;
    private  String endTime;
    private String location;

    public Meeting(String id, String title, String startTime, String endTime, String location){

        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }


    public String getStartTime(){
        return startTime;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }


    public String getEndTime(){
        return endTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }


    public String getLocation(){
        return location;
    }

    public  void setLocation(String location){
        this.location = location;
    }

}
