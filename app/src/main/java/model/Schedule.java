package model;

import java.util.ArrayList;

/**
 * Created by Zaid on 9/2/2017.
 */

public class Schedule {

    private String id;
    private String name;
    private  String email;
    private String DOB;

    public Schedule(String id, String name, String email, String DOB){

        this.id = id;
        this.name = name;
        this.email = email;
        this.DOB = DOB;
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

}
