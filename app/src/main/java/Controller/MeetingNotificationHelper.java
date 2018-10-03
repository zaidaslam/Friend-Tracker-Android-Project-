package Controller;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Zaid on 10/9/2017.
 */

public class MeetingNotificationHelper {

    public static int ALARM_TYPE_ELAPSED = 101;
    private static AlarmManager alarmManagerElapsed;
    private static PendingIntent alarmIntentElapsed;


    public static void scheduleRepeatingElapsedNotification(Context context) {

        Intent intent = new Intent(context, MeetingAlarmReceiver.class);
        alarmIntentElapsed = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManagerElapsed = (AlarmManager)context.getSystemService(ALARM_SERVICE);




        SharedPreferences sharedPref = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String reminder = sharedPref.getString("reminder","");

        if(reminder == ""){
            reminder = "1";
        }
       int rem = Integer.parseInt(reminder);

        // 3*60*1000
        alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                rem*60*1000, alarmIntentElapsed);
    }



    public static void cancelAlarmElapsed() {
        if (alarmManagerElapsed!= null) {
            alarmManagerElapsed.cancel(alarmIntentElapsed);
        }
    }

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }


    public static void disableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}