package com.example.zaid.assignment1;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import Controller.NotificationHelper;
import model.AutoSuggest;
import model.AutoSuggestModel;
import model.DatabaseHelperMeeting;
import model.FriendModel;
import model.Meeting;
import model.MeetingModel;

/**
 * Created by Zaid on 10/4/2017.
 */

public class AlertDialog extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        showAlertDialog(true);

    }

    boolean temp;

    public void showAlertDialog(boolean check) {


        final String meetingTime;


        if (AutoSuggestModel.getInstance().getAutoSuggest().size() == 0 && FriendModel.getInstance().getFriends().size() > 1 && check) {

            for (int i = 1; i < FriendModel.getInstance().getFriends().size(); i++) {
                AutoSuggestModel.getInstance().getAutoSuggest().add(new AutoSuggest(FriendModel.getInstance().getFriends().get(i).getId(), FriendModel.getInstance().getFriends().get(i).getName(), FriendModel.getInstance().getFriends().get(i).getEmail(), FriendModel.getInstance().getFriends().get(i).getDOB(), FriendModel.getInstance().getFriends().get(i).getLatitude(), FriendModel.getInstance().getFriends().get(i).getLongitude(), FriendModel.getInstance().getFriends().get(i).getMidpoint(), FriendModel.getInstance().getFriends().get(i).getFriendWD(), FriendModel.getInstance().getFriends().get(i).getyourWD(), FriendModel.getInstance().getFriends().get(i).getTotalWD(),FriendModel.getInstance().getFriends().get(i).getMeetingTime() ));

            }
        } else if (AutoSuggestModel.getInstance().getAutoSuggest().size() == 0) {

            noAlertDialog();
        }



        String displayNotification = null;



        if (AutoSuggestModel.getInstance().getAutoSuggest().size() > 0) {
            meetingTime = calculateMeetingTime(this);
            AutoSuggestModel.getInstance().getAutoSuggest().get(0).setMeetingTime(meetingTime);
            displayNotification = AutoSuggestModel.getInstance().getAutoSuggest().get(0).getName() + "  " + AutoSuggestModel.getInstance().getAutoSuggest().get(0).getMeetingTime();


            setBoolean(true);
            if(AutoSuggestModel.getInstance().getAutoSuggest().size() == 0) {
                setBoolean(false);
            }

            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle(displayNotification);
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {



                    AutoSuggestModel.getInstance().getAutoSuggest().remove(0);
                    showAlertDialog(getBoolean());



                }
            });
            builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {


                    Random rand = new Random();
                    int num = rand.nextInt(100);

                    MeetingModel.getInstance().getMeetings().add(new Meeting(Integer.toString(num),AutoSuggestModel.getInstance().getAutoSuggest().get(0).getName(),AutoSuggestModel.getInstance().getAutoSuggest().get(0).getMeetingTime(),AutoSuggestModel.getInstance().getAutoSuggest().get(0).getName(),AutoSuggestModel.getInstance().getAutoSuggest().get(0).getMidpoint()));
//                    DatabaseHelperMeeting obj = new DatabaseHelperMeeting();
//                    String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
//                    obj.start(db);
                    Intent Nintent = new Intent(AlertDialog.this, MeetingListActivity.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(Nintent);
                    overridePendingTransition(0, 0);

                    AutoSuggestModel.getInstance().getAutoSuggest().clear();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intentDialog = new Intent(AlertDialog.this, FriendListActivity.class);
                    dialog.cancel();
                    finish();
                    overridePendingTransition(0, 0);

                    overridePendingTransition(0, 0);
                }
            });
            final android.app.AlertDialog alert = builder.create();
            alert.show();
        }
    }
    public void noAlertDialog(){

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("No More Friends");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intentDialog = new Intent(AlertDialog.this, FriendListActivity.class);
                dialog.cancel();
                finish();
                overridePendingTransition( 0, 0);

                overridePendingTransition( 0, 0);
            }
        });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean getBoolean(){
        return temp;
    }

    public void setBoolean(boolean temp){
        temp = temp;
    }

    public static String calculateMeetingTime(Context context){

        long myWalkingTime = 0;
        long friendWalkingTime = 0;

//        if (FriendModel.getInstance().getFriends().size() == 0){
//            NotificationHelper.cancelAlarmElapsed();
//            NotificationHelper.disableBootReceiver(context);
//            Toast.makeText(context,"FriendList Empty",Toast.LENGTH_SHORT).show();
//        }

       // else {

            myWalkingTime = (long) (AutoSuggestModel.getInstance().getAutoSuggest().get(0).getyourWD());

            friendWalkingTime = (long) (AutoSuggestModel.getInstance().getAutoSuggest().get(0).getFriendWD());

     //   }
        long meetingtime;





        if (myWalkingTime > friendWalkingTime) {


            meetingtime = myWalkingTime;

        } else {
            meetingtime = friendWalkingTime;

        }

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, (int) meetingtime);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        String time = df.format(now.getTime());

        return time;

    }

}
