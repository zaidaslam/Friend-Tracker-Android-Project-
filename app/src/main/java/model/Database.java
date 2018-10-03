package model;

import android.app.Activity;

import com.example.zaid.assignment1.R;


/**
 * Created by Zaid on 9/17/2017.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class Database{

    private String LOG_TAG = this.getClass().getName();
    List<Friend> friendList;
    List<Meeting> mlist;



    public void start(final String dfFile){

        //new Thread(new Runnable() {

          //  public void run() {


                //String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";

                 String db = dfFile;

                try {
                    Class.forName("org.sqldroid.SQLDroidDriver");

                    Connection con = DriverManager.getConnection(db);
                    friendList = FriendModel.getInstance().getFriends();

                    Statement st = con.createStatement(); // Step 3
                    //st.executeUpdate("Drop table friends");
                    // Create table: friends //Step 4
                   // st.executeUpdate("CREATE table IF NOT EXISTS friends (ID VARCHAR(12), name VARCHAR(25), email VARCHAR(25),DOB VARCHAR(25), latitude double, longitude double,Midpoint VARCHAR(25),friendWD float,yourWD float,totalWD INT)");
                   // Log.i(LOG_TAG, "*** Created table: friends");

       //             st.executeUpdate("INSERT INTO friends VALUES ('" + "sdfsd" + "','" + "sdfsd" + "','" + "dsfsdf" + "','" + "sdf" + "','" + 23.2 + "','" + 2.22 + "','" + "sdfs" + "','" + 0.255 + "','" + 2.2 + "','" + 2 + "')");

                    // for(int i = 0 ; i< list.size();i++) {
                    // Add records to employee
                   //}
                    //st.executeUpdate("Drop table friends");
                    // Query and display results //Step 5
                    ResultSet rs = st.executeQuery("SELECT * FROM friends");
                    Log.i(LOG_TAG, "*** Query results:");
                    while (rs.next()) {
//                        Log.i(LOG_TAG, "ID=" + rs.getString(1) + ", ");
//                        Log.i(LOG_TAG, "NAME=" + rs.getString(2));
//                        Log.i(LOG_TAG, "Email=" + rs.getString(3));
//                        Log.i(LOG_TAG, "DOB=" + rs.getString(4));
//                        Log.i(LOG_TAG, "Latitude=" + rs.getDouble(5));
//                        Log.i(LOG_TAG, "Longitude=" + rs.getDouble(6));
//                        Log.i(LOG_TAG, "Midpoint=" + rs.getString(7));
//                        Log.i(LOG_TAG, "friendWT=" + rs.getFloat(8));
//                        Log.i(LOG_TAG, "MyWalkingTime=" + rs.getFloat(9));
//                        Log.i(LOG_TAG, "TotalWalkingTime=" + rs.getInt(10));

                        FriendModel.getInstance().getFriends().add(new Friend(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getDouble(6),rs.getString(7),rs.getFloat(8),rs.getFloat(9),rs.getInt(10),rs.getString(11)));
                     //   Log.i(LOG_TAG, "ID=" + FriendModel.getInstance().getFriends().get(0).getId() + ", ");
                    //    st.executeUpdate("Drop table friends");

                    }

//                    Log.i(LOG_TAG, "ID=" + FriendModel.getInstance().getFriends().get(0).getId() + ", ");
//                    Log.i(LOG_TAG, "NAME=" + FriendModel.getInstance().getFriends().get(0).getName() + " ,");
//                    Log.i(LOG_TAG, "Email=" +FriendModel.getInstance().getFriends().get(0).getEmail() + " ,");
//                    Log.i(LOG_TAG, "DOB=" +FriendModel.getInstance().getFriends().get(0).getDOB() + " ,");
//                    Log.i(LOG_TAG, "Latitude=" + FriendModel.getInstance().getFriends().get(0).getLatitude() + " ,");
//                    Log.i(LOG_TAG, "Longitude=" + FriendModel.getInstance().getFriends().get(0).getLongitude() + " ,");
//                    Log.i(LOG_TAG, "Midpoint=" + FriendModel.getInstance().getFriends().get(0).getMidpoint() + " ,");
//                    Log.i(LOG_TAG, "friendWT=" + FriendModel.getInstance().getFriends().get(0).getFriendWD() + " ,");
//                    Log.i(LOG_TAG, "MyWalkingTime=" + FriendModel.getInstance().getFriends().get(0).getyourWD() + " ,");
//                    Log.i(LOG_TAG, "TotalWalkingTime=" + FriendModel.getInstance().getFriends().get(0).getTotalWD() + " ,");



//                    Log.i(LOG_TAG, "***  table: Meetings List======================");
//                    mlist = MeetingModel.getInstance().getMeetings();
//                    st.executeUpdate("CREATE table IF NOT EXISTS meetings (ID INT, Title VARCHAR(12), startTime VARCHAR(12),endTime VARCHAR(12),location VARCHAR(12))");
//                    Log.i(LOG_TAG, "*** Created table: meetings");
//                    Log.i(LOG_TAG, "*** Inserted records");
//
//                    // Query and display results //Step 5
//                    ResultSet rs2 = st.executeQuery("SELECT * FROM meetings");
//                    Log.i(LOG_TAG, "*** Query results:");
//                    while (rs2.next()) {
//                        Log.i(LOG_TAG, "ID=" + rs2.getString(1) + ", ");
//                        Log.i(LOG_TAG, "Title=" + rs2.getString(2));
//                        Log.i(LOG_TAG, "StartTime=" + rs2.getString(3));
//                        Log.i(LOG_TAG, "EndTime=" + rs2.getString(4));
//                        Log.i(LOG_TAG, "Location=" + rs2.getString(5));
//                    }
//                    rs2.close();
                    rs.close();
                    st.close();
                    con.close();

                } catch (SQLException sqlEx) {
                    while (sqlEx != null) {
                        Log.i(LOG_TAG,
                                "[SQLException] " + "SQLState: " + sqlEx.getSQLState()
                                        + ", Message: " + sqlEx.getMessage()
                                        + ", Vendor: " + sqlEx.getErrorCode());
                        sqlEx = sqlEx.getNextException();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

        //    }
      //  }).start();

    }


    public void DropTable(String db){

        try {
            Log.d(LOG_TAG,"=========>>>>>>>>"+"Database");
            // see https://github.com/SQLDroid/SQLDroid/downloads
            Class.forName("org.sqldroid.SQLDroidDriver");

            Connection con = DriverManager.getConnection(db);
            //


            Statement st = con.createStatement(); // Step 3
            st.executeUpdate("Drop table friends");

            st.close();
            con.close();

    }
                catch (SQLException sqlEx)
    {
        while (sqlEx != null)
        {
            Log.i(LOG_TAG,
                    "[SQLException] " + "SQLState: " + sqlEx.getSQLState()
                            + ", Message: " + sqlEx.getMessage()
                            + ", Vendor: " + sqlEx.getErrorCode());
            sqlEx = sqlEx.getNextException();
        }
    }
                catch (Exception ex)
    {
        ex.printStackTrace();
    }

    }


    public void stop(final String dfFile){


        Log.i(LOG_TAG, "Stopping....................."+FriendModel.getInstance().getFriends().size());
       // new Thread(new Runnable() {


       //     public void run()
         //   {


                String db = dfFile;
               // String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";

                try
                {
                    Log.d(LOG_TAG,"=========>>>>>>>>"+"Database");
                    // see https://github.com/SQLDroid/SQLDroid/downloads
                    Class.forName("org.sqldroid.SQLDroidDriver");

                    Connection con = DriverManager.getConnection(db);
                  //
                    friendList = FriendModel.getInstance().getFriends();
                   // Statement stdrop = con.createStatement();
//                    stdrop.executeUpdate("Drop table friends");
                    Statement st = con.createStatement(); // Step 3

                    DropTable(db);


                    // Create table: employee //Step 4
                    st.executeUpdate("CREATE table IF NOT EXISTS friends (ID VARCHAR(12), name VARCHAR(25), email VARCHAR(25),DOB VARCHAR(25), latitude double, longitude double,Midpoint VARCHAR(25),friendWD float,yourWD float,totalWD INT,meetingTime VARCHAR(25))");
                    Log.i(LOG_TAG, "*** Created table: onstop");
                    Log.i(LOG_TAG, "*** Created table: "+FriendModel.getInstance().getFriends().size());
                    for(int i = 0 ; i< friendList.size();i++) {
                        // Add records to employee
                        st.executeUpdate("INSERT INTO friends VALUES ('" + friendList.get(i).getId() + "','" + friendList.get(i).getName() + "','" + friendList.get(i).getEmail() + "','" + friendList.get(i).getDOB() + "','" + friendList.get(i).getLatitude() + "','" + friendList.get(i).getLongitude() + "','" + friendList.get(i).getMidpoint() + "','" + friendList.get(i).getFriendWD() + "','" + friendList.get(i).getyourWD() + "','" + friendList.get(i).getTotalWD() + "','" + friendList.get(i).getMeetingTime() + "')");
                        Log.i(LOG_TAG, "*** Inserted records");
                    }
                   // st.executeUpdate("Drop table friends");

                    // st.executeUpdate("CREATE table IF NOT EXISTS meetings (ID INT, Title VARCHAR(12), startTime VARCHAR(12),endTime VARCHAR(12),location VARCHAR(12))");
                    //Log.i(LOG_TAG, "*** Created table: meetings");
//                    for(int i = 0; i < mlist.size(); i++) {
//                        st.executeUpdate("INSERT INTO meetings VALUES ('" + mlist.get(i).getId() + "','" + mlist.get(i).getTitle() + "','" + mlist.get(i).getStartTime() + "','" + mlist.get(i).getEndTime() + "','" + mlist.get(i).getLocation() + "')");
//                    }
                   // Log.i(LOG_TAG, "*** Inserted Meetings records");
                    st.close();
                    con.close();

                }
                catch (SQLException sqlEx)
                {
                    while (sqlEx != null)
                    {
                        Log.i(LOG_TAG,
                                "[SQLException] " + "SQLState: " + sqlEx.getSQLState()
                                        + ", Message: " + sqlEx.getMessage()
                                        + ", Vendor: " + sqlEx.getErrorCode());
                        sqlEx = sqlEx.getNextException();
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

           }
       // }).start();}

}