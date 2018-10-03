package Controller;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

import com.example.zaid.assignment1.FriendListActivity;
import com.example.zaid.assignment1.Settings;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class NotificationHelper {


    public static int ALARM_TYPE_ELAPSED = 101;
    private static AlarmManager alarmManagerElapsed;
    private static PendingIntent alarmIntentElapsed;


    public static void scheduleRepeatingElapsedNotification(Context context) {

        Intent intent = new Intent(context, AlarmReceiver.class);


        alarmIntentElapsed = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        alarmManagerElapsed = (AlarmManager)context.getSystemService(ALARM_SERVICE);


        int min = 1;
        SharedPreferences sharedPref = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String minutes = sharedPref.getString("username","");
        if(minutes == ""){
            minutes = "1";
        }
        min = Integer.parseInt(minutes);

        // 3*60*1000
        alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                min*60*1000, alarmIntentElapsed);
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
