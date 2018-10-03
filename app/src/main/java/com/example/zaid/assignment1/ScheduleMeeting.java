package com.example.zaid.assignment1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import model.Friend;
import model.Meeting;
import model.MeetingModel;
import model.Schedule;
import model.ScheduleModel;
import viewmodel.FriendListAdapter;
import viewmodel.ScheduleListAdapter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Zaid on 9/2/2017.
 */



public class ScheduleMeeting extends Activity {


    private String LOG_TAG = this.getClass().getName();
    DatePickerDialog datePickerDialog;
    private FriendListAdapter Fadapter;
    private ScheduleListAdapter Sadapter;
    EditText title;
    EditText startTime;
    EditText endTime;
    EditText location;
    Button temp;
    int beginning;
    int ending;
    private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_meeting);
        listView1 = (ListView) findViewById(R.id.list1);
        Fadapter = new FriendListAdapter(this);
        listView1.setAdapter(Fadapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = Fadapter.getItem(position);

                ScheduleModel.getInstance().getSchedule().add(new Schedule(friend.getId(),friend.getName(),friend.getEmail(),friend.getDOB()));

            }
        });

        title = (EditText) findViewById(R.id.meeting_title);
        startTime = (EditText) findViewById(R.id.meeting_StartTime);
        endTime = (EditText) findViewById(R.id.meeting_EndTime);
        location = (EditText) findViewById(R.id.meeting_location);
        startTime = (EditText) findViewById(R.id.meeting_StartTime);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                // date picker dialog
                datePickerDialog = new DatePickerDialog(ScheduleMeeting.this,
                        new DatePickerDialog.OnDateSetListener() {
                            Date date = new java.util.Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            @Override
                             public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                startTime.setText((monthOfYear + 1) + " "
                                        + dayOfMonth + "th,   " + year+",  "+sdf.format(date));
                                //   Toast.makeText(getApplicationContext(), date.getText(), Toast.LENGTH_LONG).show();
                                beginning   = dayOfMonth+(monthOfYear+1)+year;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day


                // date picker dialog
                datePickerDialog = new DatePickerDialog(ScheduleMeeting.this,
                        new DatePickerDialog.OnDateSetListener() {

                            Date date = new java.util.Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                endTime.setText((monthOfYear + 1) + " "
                                        + dayOfMonth + "th,  " + year+",   "+sdf.format(date));

                                ending   = dayOfMonth+(monthOfYear+1)+year;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        title.setHint("Title");
        startTime.setHint("Start Time");
        endTime.setHint("END Time");
        location.setHint("Location");
        temp = (Button) findViewById(R.id.btn);
        temp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(beginning > ending){
                    Toast.makeText(getApplicationContext(),"Please change end date" , Toast.LENGTH_LONG).show();
                }else{
                    run();
                }
            }
        });

    }

    public void run(){

        String Title = title.getText().toString();
        String Start = startTime.getText().toString();
        String end = endTime.getText().toString();
        String Location = location.getText().toString();
        Random rand = new Random();
        int num = rand.nextInt(100);
        MeetingModel.getInstance().getMeetings().add(new Meeting(Integer.toString(num),Title,Start,end,Location));
        Intent intent = new Intent(ScheduleMeeting.this, MeetingListActivity.class);
        startActivity(intent);

    }

}

