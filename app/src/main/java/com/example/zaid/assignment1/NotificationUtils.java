package com.example.zaid.assignment1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.icu.util.Calendar;
import java.util.ArrayList;
import java.util.Calendar;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import Controller.NotificationHelper;
import model.AutoSuggest;
import model.AutoSuggestModel;
import model.DatabaseHelperMeeting;
import model.Friend;
import model.FriendModel;
import model.Meeting;
import model.MeetingModel;
import viewmodel.MeetingListAdapter;

/**
 * Created by Zaid on 10/1/2017.
 */

public class NotificationUtils {


    public static final int NOTIFICATION_ID = 1;

    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String CANCEL = "cancel";
    public static final String DISMISS = "dismiss";

//    public static final String Previous = "previous";
    Context context;



    public static NotificationCompat.Builder displayNotification(Context context) {


        NotificationCompat.Builder notificationBuilder;


        Intent yesIntent = new Intent(context, NotificationActionService.class)
                .setAction(YES);

        Intent noIntent = new Intent(context, NotificationActionService.class)
                .setAction(NO);


        Intent cancelIntent = new Intent(context, NotificationActionService.class)
                .setAction(CANCEL);

        Intent dismissIntent = new Intent(context, NotificationActionService.class)
                .setAction(DISMISS);



        PendingIntent yesPendingIntent = PendingIntent.getService(context, 0,

                yesIntent, PendingIntent.FLAG_ONE_SHOT);


        PendingIntent noPendingIntent = PendingIntent.getService(context, 0,
                noIntent, PendingIntent.FLAG_ONE_SHOT);


        PendingIntent cancelPendingIntent = PendingIntent.getService(context, 0,
                cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        PendingIntent dismissPendingIntent = PendingIntent.getService(context, 0,
                dismissIntent, PendingIntent.FLAG_ONE_SHOT);



        String time =  calculateMeetingTime(context);



        NotificationUtils ob = new NotificationUtils();
        Log.d("Test time", "---------->" + time);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (FriendModel.getInstance().getFriends().size() > 0 ) {
            FriendModel.getInstance().getFriends().get(0).setMeetingTime(time);
            String displayNotification = FriendModel.getInstance().getFriends().get(0).getName() + "  " + time;
            notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setSound(alarmSound)
                            .setSmallIcon(R.drawable.ic_timer)
                            .setContentTitle("Nearest Friend")
                            .setContentText(displayNotification)
                            .addAction(new NotificationCompat.Action(R.drawable.ic_timer,
                                    "Yes", yesPendingIntent))
                            .addAction(new NotificationCompat.Action(R.drawable.ic_timer,
                                    "No", noPendingIntent))
                            .addAction(new NotificationCompat.Action(R.drawable.ic_timer,
                                    "Cancel", cancelPendingIntent))
                            .setAutoCancel(true);

        } else {
            NotificationHelper.cancelAlarmElapsed();
            NotificationHelper.disableBootReceiver(context);
            notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_timer)
                            .setContentTitle("RELOAD")
                            .setContentText("Friend List Empty! add Friend")
                            .addAction(new NotificationCompat.Action(R.drawable.ic_timer,
                                    "Dismiss", dismissPendingIntent))
                            .setAutoCancel(true);
        }
            return notificationBuilder;

    }


    public static String calculateMeetingTime(Context context){

        long myWalkingTime = 0;
        long friendWalkingTime = 0;

        if (FriendModel.getInstance().getFriends().size() == 0){
            NotificationHelper.cancelAlarmElapsed();
            NotificationHelper.disableBootReceiver(context);
            Toast.makeText(context,"FriendList Empty",Toast.LENGTH_SHORT).show();
        }

        else {

            myWalkingTime = (long) (FriendModel.getInstance().getFriends().get(0).getyourWD());

            friendWalkingTime = (long) (FriendModel.getInstance().getFriends().get(0).getFriendWD());

        }
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


    public static class NotificationActionService extends IntentService {
       // private MeetingListAdapter adapter;
        boolean nextFriend;
        Context context;
        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();


            Log.d("Received notification"," "+ action);


            if (YES.toUpperCase().equals(action.toUpperCase())) {
                // TODO: handle action 1.
                Random rand = new Random();
                int num = rand.nextInt(100);

                MeetingModel.getInstance().getMeetings().add(new Meeting(Integer.toString(num),FriendModel.getInstance().getFriends().get(0).getName(),FriendModel.getInstance().getFriends().get(0).getMeetingTime(),FriendModel.getInstance().getFriends().get(0).getName(),FriendModel.getInstance().getFriends().get(0).getMidpoint()));
                //adapter.notifyDataSetChanged();
                NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();
//                DatabaseHelperMeeting obj = new DatabaseHelperMeeting();
//                String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
//                obj.stop(db);
                Intent Nintent = new Intent(NotificationActionService.this, MeetingListActivity.class);
                startActivity(Nintent);

                Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                this.sendBroadcast(it);
                Log.d("Received notification"," "+ action);

            }
            else if (NO.toUpperCase().equals(action.toUpperCase())) {
                // TODO: handle action 1.



                NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();
                Intent intentDialog = new Intent(NotificationActionService.this, com.example.zaid.assignment1.AlertDialog.class);
                startActivity(intentDialog);
                Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                this.sendBroadcast(it);

            }


            else if (CANCEL.toUpperCase().equals(action.toUpperCase())) {
                // TODO: handle action 1.

                NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();
                Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                this.sendBroadcast(it);

            }

            else if (DISMISS.toUpperCase().equals(action.toUpperCase())) {
                // TODO: handle action 1.

                NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();

                Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                this.sendBroadcast(it);


            }
        }
    }

}



