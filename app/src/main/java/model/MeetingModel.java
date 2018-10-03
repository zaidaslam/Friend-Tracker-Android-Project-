package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Zaid on 9/2/2017.
 */

public class MeetingModel

{


    private static MeetingModel instance;

    List<Meeting> meetings;
//
//    Random randomGenerator = new Random();
//    String[] title = {"Long Time no see","Culture","Openday","Business","Business","Business","Business","Business","Business","Business","Business"};
//    String[] startTime = {"7:30PM","7:30PM","7:30PM","2:32PM","5:33PM","2:30PM","3:30PM","4:30PM","5:30PM","7:43PM","10:30PM"};
//    String[] endTime = {"8:30PM","9:30PM","10:30PM","3:32PM","6:33PM","11:30PM","10:30PM","5:30PM","7:30PM","8:43PM","11:30PM"};
//    String[] location = {"-37.805631, 144.963053","-37.805631, 144.963053","-37.805631, 144.963053","-37.805631, 144.963053","-37.805631, 144.963053","-37.805631, 144.963053","-37.805631, 144.963053","-37.805631, 144.963053"};
//





    private MeetingModel(){
        meetings = new ArrayList<Meeting>();

//        for (int i = 0 ; i < 7 ; i++){
//            int randomInt = randomGenerator.nextInt(100);
//            meetings.add(new Meeting(Integer.toString(randomInt),title[i],startTime[i],endTime[i],location[i]));
//        }
    }

    public static MeetingModel getInstance()
    {   if(instance == null ){
        instance = new MeetingModel();
    }
        return instance;
    }

    public Meeting getMeetingForPosition(int Position){
        return meetings.get(Position);
    }

    public Meeting getMeetingById(String id){
        for (Meeting Meet : meetings)
            if (Meet.getId().equals(id))
                return Meet;
        return null;
    }

    public List<Meeting> getMeetings(){
        return meetings;
    }

}
