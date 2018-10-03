package Controller;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.zaid.assignment1.FriendListActivity;
import com.example.zaid.assignment1.MeetingListActivity;
import com.example.zaid.assignment1.MeetingNotificationUtils;
import com.example.zaid.assignment1.NotificationUtils;

/**
 * Created by Zaid on 10/9/2017.
 */

public class MeetingAlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentToRepeat = new Intent(context, MeetingListActivity.class);

        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, MeetingNotificationHelper.ALARM_TYPE_ELAPSED, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification repeatedNotification = MeetingNotificationUtils.displayMeetingNotification(context).build();


        NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_ELAPSED, repeatedNotification);

    }
}