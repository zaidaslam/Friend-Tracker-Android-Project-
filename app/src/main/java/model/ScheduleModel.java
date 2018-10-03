package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zaid on 9/2/2017.
 */

public class ScheduleModel

{


    private static ScheduleModel instance;

    List<Schedule> schedules;

    Random randomGenerator = new Random();
    String[] name = {"James","Robin","Sam","Parker","Josh","Clark","Tim","Bill","Trump","Bond","tony"};
    String[] email = {"@gmail.com","@gmail.com","@gmail.com","@gmail.com","@gmail.com","@gmail.com","@gmail.com","@gmail.com","@gmail.com","@gmail.com","csdcsdc"};
    String[] date = {"6/8/2015","4/2/2016","18/2/2000","9/6/2016","19/2/2016","29/1/2018","9/9/2019","9/2/2006","5/4/2006","9/2/1988","3/11/2001"};





    private ScheduleModel(){
        schedules = new ArrayList<Schedule>();

        for (int i = 0 ; i < 0 ; i++){
            int randomInt = randomGenerator.nextInt(100);
            schedules.add(new Schedule(Integer.toString(randomInt),name[i],email[i],date[i]));
        }
    }

    public static ScheduleModel getInstance()
    {   if(instance == null ){
        instance = new ScheduleModel();
    }
        return instance;
    }

    public Schedule getScheduleForPosition(int Position){
        return schedules.get(Position);
    }

    public Schedule getScheduleById(String id){
        for (Schedule sched : schedules)
            if (sched.getId().equals(id))
                return sched;
        return null;
    }

    public List<Schedule> getSchedule(){
        return schedules;
    }

}
