package Controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.example.zaid.assignment1.FriendListActivity;
import com.example.zaid.assignment1.MeetingListActivity;
import com.example.zaid.assignment1.NotificationUtils;


import model.AutoSuggest;
import model.AutoSuggestModel;


public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentToRepeat = new Intent(context, FriendListActivity.class);

        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_ELAPSED, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

       Notification repeatedNotification = NotificationUtils.displayNotification(context).build();

        NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_ELAPSED, repeatedNotification);

    }
}
