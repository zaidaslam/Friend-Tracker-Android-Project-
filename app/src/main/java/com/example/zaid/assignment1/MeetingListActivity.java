package com.example.zaid.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Collections;
import java.util.Comparator;
import Controller.MeetingListController;
import Controller.MeetingNotificationHelper;
import Controller.NotificationHelper;
import model.Database;
import model.DatabaseHelperMeeting;
import model.FriendModel;
import model.Meeting;
import model.MeetingModel;
import viewmodel.MeetingListAdapter;


public class MeetingListActivity extends Activity{

    private static final String TAG = "MeetingListActivity";


    private String LOG_TAG = this.getClass().getName();
    private MeetingListAdapter adapter;
    public static final int EDIT_MEETING_REQUEST = 0;
    public static final String ID_EXTRA = "meeting_id";
    public static final String MEETING_RESULT_EXTRA = "meeting_result";
    private ListView listView;
    Button back;
    Button Schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_list);
        DatabaseHelperMeeting obj = new DatabaseHelperMeeting();
        String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
        obj.start(db);
        listView = (ListView) findViewById(R.id.list);
        adapter = new MeetingListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MeetingListController(this,adapter));

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {

                Meeting meeting = adapter.getItem(position);
                adapter.remove(meeting);
                DatabaseHelperMeeting obj = new DatabaseHelperMeeting();
                String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
                obj.stop(db);
                adapter.notifyDataSetChanged();
                return false;
            }

        });

        back = (Button) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatabaseHelperMeeting obj = new DatabaseHelperMeeting();
                String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
                obj.stop(db);

                //MeetingModel.getInstance().getMeetings().clear();



                finish();

            }
        });





        Schedule = (Button) findViewById(R.id.btnAddMeeting);
        Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MeetingListActivity.this, ScheduleMeeting.class);
                startActivity(intent);


            }
        });

        if(MeetingModel.getInstance().getMeetings().size() > 0){
            Log.d("Suggest Now Meeting","===>>>===>>>>");
            meetingReminder();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        DatabaseHelperMeeting obj = new DatabaseHelperMeeting();
        String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
        obj.stop(db);

        //MeetingModel.getInstance().getMeetings().clear();
    }

    public void meetingReminder(){



        if(MeetingModel.getInstance().getMeetings().size() > 0) {
            MeetingNotificationHelper.scheduleRepeatingElapsedNotification(this);
            MeetingNotificationHelper.enableBootReceiver(this);
        }else
        {
            MeetingNotificationHelper.cancelAlarmElapsed();
            MeetingNotificationHelper.disableBootReceiver(this);
            Toast.makeText(MeetingListActivity.this,"Meeting List Empty",Toast.LENGTH_SHORT).show();
        }
    }

    public void onToggleClicked(View view) {


        boolean on = ((ToggleButton) view).isChecked();

        if (on) {

            Collections.sort(MeetingModel.getInstance().getMeetings(), new Comparator<Meeting>() {

                public int compare(Meeting o1, Meeting o2) {

                    return o2.getStartTime().compareTo(o1.getStartTime());
                }
            });

            adapter.notifyDataSetChanged();
        } else {
            Collections.reverse(MeetingModel.getInstance().getMeetings());

            adapter.notifyDataSetChanged();

        }
    }
}
