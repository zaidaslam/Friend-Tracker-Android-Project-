package com.example.zaid.assignment1;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import Controller.FriendListController;
import Controller.NotificationHelper;
import model.AutoSuggest;
import model.AutoSuggestModel;
import model.Database;
import model.DatabaseHelperMeeting;
import model.Friend;
import model.FriendModel;
import model.Meeting;
import model.MeetingModel;
import model.MyLocationModel;
import viewmodel.FriendListAdapter;

public class FriendListActivity extends Activity implements AsyncResponse{

    private static final String TAG = "FriendListActivity";
    private   String LOG_TAG = this.getClass().getName();
    private FriendListAdapter adapter;
    public static final int EDIT_FRIEND_REQUEST = 0;
    public static final String ID_EXTRA = "friend_id";
    public static final String FRIEND_RESULT_EXTRA = "friend_result";
    private ListView listView;
    double latitude ;
    double longitude ;
    DummyLocationService dummyLocationService;
    List<DummyLocationService.FriendLocation> matched = null;
    double midpointLat;
    double midpointLong;
    Location location;
    String midpoint;
    String guestLocation;
    String myLocation;
    protected static final int PICK_CONTACTS = 100;
    private static float time;
    MyLocationModel Location;
    private FusedLocationProviderClient mFusedLocationClient;
    String provider;
    Button buttn;
    Button suggestMeeting;
    Friend friend;
    NotificationCompat.Builder notification;
    private  static final int uniqueID = 45612;
    public static final String KEY_NOTIFICATION_REPLY = "KEY_NOTIFICATION_REPLY";
    private Button button;
    private Button settings;
    private int notificationId = 1;
    public static int NOTIFICATION_ID = 1;
    public static final String details = "details";

    boolean notResumed;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Database obj = new Database();
        String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
        obj.start(db);
        Location = new MyLocationModel(FriendListActivity.this);

        Log.d(LOG_TAG,"Created New Activity");

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

       // button = (Button) findViewById(R.id.notification);
       // button.setOnClickListener(buttonClickListener);

        settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(gotosettings);


//        listView = (ListView) findViewById(R.id.list);
//        adapter = new FriendListAdapter(this);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new FriendListController(this,adapter));
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                           int position, long arg3) {
//
//                friend = adapter.getItem(position);
//                adapter.remove(friend);
//                FriendModel.getInstance().getFriends().remove(friend);
//                Database obj = new Database();
//                String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
//                obj.stop(db);
//
//                adapter.notifyDataSetChanged();
//
//                return false;
//            }
//
//        });



        if(FriendModel.getInstance().getFriends().size() > 0){
            Log.d("Suggest Now","===>>>===>>>>");
            suggestNow();

        }
        // if(location != null)
        buttn = (Button) findViewById(R.id.btnMeetingList);
        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(FriendListActivity.this, MeetingListActivity.class);
                //finish();
               startActivity(intent);
            }
        });



        suggestMeeting = (Button) findViewById(R.id.dummy);

        suggestMeeting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)  {
                    builder = new AlertDialog.Builder(FriendListActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(FriendListActivity.this);
                    // Toast.makeText(FriendListActivity.this,"Friend List Empty",Toast.LENGTH_SHORT).show();
                }




                if(FriendModel.getInstance().getFriends().size()  != 0) {
                    Log.d(LOG_TAG,"TTTTTT>>>"+FriendModel.getInstance().getFriends().get(0).getMeetingTime());
                    final String meetingTime = NotificationUtils.calculateMeetingTime(FriendListActivity.this);
                    //String displayNotification = AutoSuggestModel.getInstance().getAutoSuggest().get(0).getName() + "  " + AutoSuggestModel.getInstance().getAutoSuggest().get(0).getMeetingTime();
                    String displayNotification = FriendModel.getInstance().getFriends().get(0).getName() + "  " + meetingTime;
                    builder.setTitle("Nearest Friend")
                            .setMessage(displayNotification)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    MeetingModel.getInstance().getMeetings().add(new Meeting("id",FriendModel.getInstance().getFriends().get(0).getName(),meetingTime,FriendModel.getInstance().getFriends().get(0).getName(),FriendModel.getInstance().getFriends().get(0).getMidpoint()));
                                    //AutoSuggestModel.getInstance().getAutoSuggest().remove(0);
                                    Intent Nintent = new Intent(FriendListActivity.this, MeetingListActivity.class);
                                    startActivity(Nintent);
                                    // Log.d("Received notification"," "+ action);
                                    if(AutoSuggestModel.getInstance().getAutoSuggest().size()== 0 ) {

                                        for (int i = 0; i< FriendModel.getInstance().getFriends().size();i++) {
                                            AutoSuggestModel.getInstance().getAutoSuggest().add(new AutoSuggest(FriendModel.getInstance().getFriends().get(i).getId(), FriendModel.getInstance().getFriends().get(i).getName(), FriendModel.getInstance().getFriends().get(i).getEmail(), FriendModel.getInstance().getFriends().get(i).getDOB(), FriendModel.getInstance().getFriends().get(i).getLatitude(), FriendModel.getInstance().getFriends().get(i).getLongitude(), FriendModel.getInstance().getFriends().get(i).getMidpoint(), FriendModel.getInstance().getFriends().get(i).getFriendWD(), FriendModel.getInstance().getFriends().get(i).getyourWD(), FriendModel.getInstance().getFriends().get(i).getTotalWD(), "4:30:10"));
                                        }
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {  Toast.makeText(FriendListActivity.this,"Friend List Empty",Toast.LENGTH_SHORT).show();}
            }

        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        listView = (ListView) findViewById(R.id.list);
        adapter = new FriendListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new FriendListController(this,adapter));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {

                friend = adapter.getItem(position);
                adapter.remove(friend);
                FriendModel.getInstance().getFriends().remove(friend);
                Database obj = new Database();
                String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
                obj.stop(db);

                adapter.notifyDataSetChanged();

                return false;
            }

        });
    }




    public View.OnClickListener gotosettings = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(FriendListActivity.this,com.example.zaid.assignment1.Settings.class);
            startActivity(intent);

        }
    };

//    public void getPermissionToReadUserContacts() {
//
//        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PICK_CONTACTS);
//            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
//        } else {
//            // Android version is lesser than 6.0 or the permission is already granted.
//          //  List<String> contacts = getContactNames();
//           // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
//         //   lstNames.setAdapter(adapter);
//        }
//    }


    public   void pickContact(View v) {


        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, PICK_CONTACTS);


    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        double empty = 0.0;

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }

        latitude = Location.getLatitude();
        longitude = Location.getLongitude();

        if(latitude == empty){

            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return  ;
            }

            LocationManager locationManager = (LocationManager) this
                    .getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }else{

            latitude = Location.getLatitude();
            longitude = Location.getLongitude();

        }

        if (requestCode == PICK_CONTACTS) {
            if (resultCode == RESULT_OK) {

                ContactDataManager contactsManager = new ContactDataManager(this, data);
                String name = "";
                String email = "";

                try {
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();


                    dummyLocationService = DummyLocationService.getSingletonInstance(this);

                    try {

                        matched = dummyLocationService.getFriendLocationsForTime(DateFormat.getTimeInstance(
                                DateFormat.MEDIUM).parse("9:46:18 AM"), 4,0);
                        Log.d(LOG_TAG,"tetsiijclns============>"+name);
                        for(int i =0; i < matched.size() ; i++) {

                            Random rand = new Random();
                            int num = rand.nextInt(100);
                            if(name.equals(matched.get(i).name)) {

                                midpointLat = (matched.get(i).latitude + latitude)/2;
                                midpointLong = (matched.get(i).longitude + longitude)/2;

                                midpoint = midpointLat+","+midpointLong;

                                myLocation = latitude+","+longitude;

                                guestLocation = matched.get(i).latitude+","+matched.get(i).longitude;


                                DistanceApi(midpoint, guestLocation,myLocation,name);


                                if(FriendModel.getInstance().getFriends().size() > 0){
                                    boolean temp = true;
                                    for (int j = 0; j < FriendModel.getInstance().getFriends().size(); j++){
                                        if(name.equals(FriendModel.getInstance().getFriends().get(j).getName())) {

                                            FriendModel.getInstance().getFriends().get(j).setLatitude(matched.get(i).latitude);
                                            FriendModel.getInstance().getFriends().get(j).setLongitude(matched.get(i).longitude);
                                            // adapter.notifyDataSetChanged();
                                            Toast.makeText(FriendListActivity.this, "Friend Updated", Toast.LENGTH_SHORT).show();
                                            temp = false;
                                            return;
                                        }

                                    }if(temp) {
                                        FriendModel.getInstance().getFriends().add(new Friend(Integer.toString(num), name, email, "3/11/2007", matched.get(i).latitude, matched.get(i).longitude, midpoint, 0.0f, 0.0f, 0,"15:20"));
                                       // AutoSuggestModel.getInstance().getAutoSuggest().add(new AutoSuggest(Integer.toString(num), name, email, "3/11/2007", matched.get(i).latitude, matched.get(i).longitude, midpoint, 0.0f, 0.0f, 0,"4:30:10"));
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(FriendListActivity.this,"Friend added",Toast.LENGTH_SHORT).show();

                                    }
                                }else {
                                    FriendModel.getInstance().getFriends().add(new Friend(Integer.toString(num), name, email, "3/11/2007", matched.get(i).latitude, matched.get(i).longitude, midpoint, 0.0f, 0.0f, 0,"15:20"));
                                   // AutoSuggestModel.getInstance().getAutoSuggest().add(new AutoSuggest(Integer.toString(num), name, email, "3/11/2007", matched.get(i).latitude, matched.get(i).longitude, midpoint, 0.0f, 0.0f, 0,"4:30:10"));
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(FriendListActivity.this,"First Entry",Toast.LENGTH_SHORT).show();
                                    suggestNow();
                                }

                            }


                        }

                    }catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }

            }
        }
    }

    public void suggestNow(){



        if(FriendModel.getInstance().getFriends().size() > 0) {
            NotificationHelper.scheduleRepeatingElapsedNotification(this);
            NotificationHelper.enableBootReceiver(this);
        }else
        {
            NotificationHelper.cancelAlarmElapsed();
            NotificationHelper.disableBootReceiver(this);
            Toast.makeText(FriendListActivity.this,"FriendList Empty",Toast.LENGTH_SHORT).show();
        }
    }

    public void DistanceApi(String midpoint, String guestLocation, String myLocation,String name){

        String destination = midpoint;
        String myOrigin = myLocation;
        String origin = guestLocation;

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+destination+"&mode=walking&language=fr-FR&avoid=tolls&key=AIzaSyDDHuRNpf1BekSFSjqE-ce8yJZhNW_USng";
        String myUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+myOrigin+"&destinations="+destination+"&mode=walking&language=fr-FR&avoid=tolls&key=AIzaSyDDHuRNpf1BekSFSjqE-ce8yJZhNW_USng";

        new GeoTask(FriendListActivity.this).execute(url,myUrl,name,destination);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        NotificationHelper.cancelAlarmElapsed();
        NotificationHelper.disableBootReceiver(this);
    }

    @Override
    public void setDouble(String result) {

        int counter;
        String res[]=result.split(",");
        double fMin=Double.parseDouble(res[0])/60;
        double myMin=Double.parseDouble(res[1])/60;
        String name = res[2];
        String midpointlat = res[3];
        String midpointlong = res[4];
        long friendWalkingTime = (long) fMin;
        long myWalkingTime = (long) myMin;
        int totalWalkingTime = (int) (friendWalkingTime + myWalkingTime);

        Log.d("Test SetDouble","---------->"+myWalkingTime);
        //   int index = 0 ;
        for (int i = 0; i< FriendModel.getInstance().getFriends().size();i++){

            if(name.equals(FriendModel.getInstance().getFriends().get(i).getName())) {

                FriendModel.getInstance().getFriends().get(i).setYourWD(myWalkingTime);
                FriendModel.getInstance().getFriends().get(i).setFriendWD(friendWalkingTime);
                FriendModel.getInstance().getFriends().get(i).setTotalWD(totalWalkingTime);
                String autoName = FriendModel.getInstance().getFriends().get(i).getName();

                adapter.notifyDataSetChanged();
                Toast.makeText(FriendListActivity.this, "Friend time Updated", Toast.LENGTH_SHORT).show();

                break;
            }
        }

        Collections.sort(FriendModel.getInstance().getFriends(), new CustomComparator());

        Database obj = new Database();
        String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
        obj.stop(db);


    }

    public class CustomComparator implements Comparator<Friend> {
        @Override
        public int compare(Friend o1, Friend o2) {
            Integer price1 = o1.getTotalWD();
            Integer price2 = o2.getTotalWD();
            return price1.compareTo(price2);
        }
    }

}
