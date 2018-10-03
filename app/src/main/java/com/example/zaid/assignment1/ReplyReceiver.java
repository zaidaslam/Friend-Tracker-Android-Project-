package com.example.zaid.assignment1;

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.zaid.assignment1.FriendListActivity.NOTIFICATION_ID;
import static com.example.zaid.assignment1.FriendListActivity.NOTIFICATION_ID;
/**
 * Created by Zaid on 10/4/2017.
 */

    public class ReplyReceiver extends BroadcastReceiver {
    public ReplyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {

            CharSequence id = remoteInput.getCharSequence(FriendListActivity.KEY_NOTIFICATION_REPLY);

            Log.d("Notification ID", "====>>"+id);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Request received for ID: " + "Hello");

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }

}
}