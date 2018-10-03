package com.example.zaid.assignment1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import model.DatabaseHelperMeeting;
import model.Meeting;
import model.MeetingModel;
import model.Schedule;
import viewmodel.ScheduleListAdapter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Zaid on 9/2/2017.
 */



public class EditMeetingActivity extends Activity {
    private String LOG_TAG = this.getClass().getName();
    DatePickerDialog datePickerDialog;
    private ScheduleListAdapter adapter;
    EditText title;
    EditText startTime;
    EditText endTime;
    EditText location;
    Button temp;
    Meeting meeting;
    int beginning;
    int ending;
    String Id;
    private ListView listView;
    Button map;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meeting);
        id = getIntent().getStringExtra(MeetingListActivity.ID_EXTRA);
        Log.d(LOG_TAG,String.format("MeetingID: %s",id));
        meeting = MeetingModel.getInstance().getMeetingById(id);
        title = (EditText) findViewById(R.id.meeting_title);
        listView = (ListView) findViewById(R.id.list);
        adapter = new ScheduleListAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {

                Schedule schedule = adapter.getItem(position);
                adapter.remove(schedule);
                adapter.notifyDataSetChanged();

                return false;
            }

        });
        endTime = (EditText) findViewById(R.id.meeting_EndTime);
        location = (EditText) findViewById(R.id.meeting_location);

        startTime = (EditText) findViewById(R.id.meeting_StartTime);

        map = (Button) findViewById(R.id.map);
        map.setOnClickListener(diaplaymap);

        // perform click event on edit text
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                // date picker dialog
                datePickerDialog = new DatePickerDialog(EditMeetingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            Date date = new java.util.Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                startTime.setText((monthOfYear + 1) + " "
                                        + dayOfMonth + "th,   " + year+",  "+sdf.format(date));
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
                datePickerDialog = new DatePickerDialog(EditMeetingActivity.this,
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

        title.setText(meeting.getTitle());
        startTime.setText(meeting.getStartTime());
        endTime.setText(meeting.getEndTime());
        location.setText(meeting.getLocation());
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


    public View.OnClickListener diaplaymap = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(EditMeetingActivity.this,Displaymap.class);

            intent.putExtra("DisplayMap",id);
            startActivity(intent);

        }
    };

    public void run(){
        meeting.setTitle(title.getText().toString());
        meeting.setStartTime(startTime.getText().toString());
        meeting.setEndTime(endTime.getText().toString());
        meeting.setLocation(location.getText().toString());

//        DatabaseHelperMeeting obj = new DatabaseHelperMeeting();
//        String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
//        obj.stop(db);
        Intent intent = new Intent(EditMeetingActivity.this, MeetingListActivity.class);
        //finish();
        startActivity(intent);
    }

    @Override

    public void finish(){

        Intent resultntent = new Intent();
        resultntent.putExtra(MeetingListActivity.MEETING_RESULT_EXTRA,"Something");
        setResult(RESULT_OK, resultntent);
        super.finish();
    }
}

