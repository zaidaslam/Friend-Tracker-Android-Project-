package Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Zaid on 10/9/2017.
 */

public class MeetingAlarmBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            NotificationHelper.scheduleRepeatingElapsedNotification(context);
        }
    }
}
