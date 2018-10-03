package com.example.zaid.assignment1;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import Controller.MeetingNotificationHelper;
import Controller.NotificationHelper;
import model.FriendModel;
import model.Meeting;
import model.MeetingModel;

import static com.example.zaid.assignment1.FriendListActivity.KEY_NOTIFICATION_REPLY;

/**
 * Created by Zaid on 10/9/2017.
 */

public class MeetingNotificationUtils extends Activity {


    public static final int NOTIFICATION_ID = 1;
    public static final String KEY_NOTIFICATION_REPLY = "KEY_NOTIFICATION_REPLY";
    public static final String DISMISS = "dismiss";
    public static final String CANCEL = "cancel";
    public static final String REMIND = "remind";

    Context context;



    public static NotificationCompat.Builder displayMeetingNotification(Context context) {


        NotificationCompat.Builder notificationBuilder;


        Intent dismissIntent = new Intent(context, MeetingNotificationUtils.NotificationActionService.class)
                .setAction(DISMISS);

        Intent cancelIntent = new Intent(context, MeetingNotificationUtils.NotificationActionService.class)
                .setAction(CANCEL);


        Intent remindIntent = new Intent(context, MeetingNotificationUtils.NotificationActionService.class)
                .setAction(REMIND);

//        Intent dismissIntent = new Intent(context, NotificationUtils.NotificationActionService.class)
//                .setAction(DISMISS);



        PendingIntent dismissPendingIntent = PendingIntent.getService(context, 0,

                dismissIntent, PendingIntent.FLAG_ONE_SHOT);


        PendingIntent cancelPendingIntent = PendingIntent.getService(context, 0,
                cancelIntent, PendingIntent.FLAG_ONE_SHOT);


        PendingIntent remindPendingIntent = PendingIntent.getService(context, 0,
                remindIntent, PendingIntent.FLAG_ONE_SHOT);

//        PendingIntent dismissPendingIntent = PendingIntent.getService(context, 0,
//                dismissIntent, PendingIntent.FLAG_ONE_SHOT);

        PendingIntent replyPendingIntent = null;

        String time =  calculateMeetingTime(context);





        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent detailsIntent = new Intent(context, DetailsActivity.class);
        detailsIntent.putExtra("EXTRA_DETAILS_ID", 42);
        PendingIntent detailsPendingIntent = PendingIntent.getActivity(
                context,
                0,
                detailsIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        if (MeetingModel.getInstance().getMeetings().size() > 0 ) {
            MeetingModel.getInstance().getMeetings().get(0).setStartTime(time);
//            Intent detailsIntent = new Intent(context, DetailsActivity.class);
//            detailsIntent.putExtra("EXTRA_DETAILS_ID", 42);
//            PendingIntent detailsPendingIntent = PendingIntent.getActivity(
//                    context,
//                    0,
//                    detailsIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT
//            );

            if (Build.VERSION.SDK_INT < 24) {
                replyPendingIntent = detailsPendingIntent;
            } else {
                        replyPendingIntent = PendingIntent.getBroadcast(
                        context,
                        0,
                        new Intent(context, ReplyReceiver.class),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
            }
            RemoteInput remoteInput = new RemoteInput.Builder(KEY_NOTIFICATION_REPLY)
                    .setLabel("Remind me in")
                    .build();
            NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_save, "Remind Me", replyPendingIntent)
                    .addRemoteInput(remoteInput)
                    .build();

            String displayNotification = MeetingModel.getInstance().getMeetings().get(0).getTitle() + "  " + time;
            notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setSound(alarmSound)
                            .setSmallIcon(R.drawable.ic_timer)
                            .setContentTitle("Upcoming Meeting")
                            .setContentText(displayNotification)
                            .setAutoCancel(true)
                            .setContentIntent(detailsPendingIntent)
                            .addAction(replyAction)
                            .addAction(new NotificationCompat.Action(R.drawable.ic_timer,
                                    "Dismiss", dismissPendingIntent))
                            .addAction(new NotificationCompat.Action(R.drawable.ic_timer,
                                    "Cancel", cancelPendingIntent));
//                            .addAction(new NotificationCompat.Action(R.drawable.ic_timer,
//                                    "Remind", remindPendingIntent))
//                            .setContentIntent(detailsPendingIntent)
//                            .addAction(replyAction)
//                            .setAutoCancel(true);


        } else {
            MeetingNotificationHelper.cancelAlarmElapsed();
            MeetingNotificationHelper.disableBootReceiver(context);
            notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_timer)
                            .setContentTitle("RELOAD")
                            .setContentText("Meeting List Empty! add Friend")
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

        boolean nextFriend;
        Context context;
        public NotificationActionService() {
            super(NotificationUtils.NotificationActionService.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();


            Log.d("Received notification"," "+ action);


            if (DISMISS.toUpperCase().equals(action.toUpperCase())) {
                // TODO: handle action 1.

                NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();
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


            else if (REMIND.toUpperCase().equals(action.toUpperCase())) {
                // TODO: handle action 1.

//                NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//                notifManager.cancelAll();
//                Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//                this.sendBroadcast(it);

            }


        }
    }

}



