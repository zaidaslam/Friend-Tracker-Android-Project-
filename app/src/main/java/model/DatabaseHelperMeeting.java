package model;

import android.app.Activity;

import com.example.zaid.assignment1.R;



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

/**
 * Created by Zaid on 10/7/2017.
 */

public class DatabaseHelperMeeting {


    private String LOG_TAG = this.getClass().getName();

    List<Meeting> mlist;



    public void start(final String dfFile){



        String db = dfFile;

        try {
            Class.forName("org.sqldroid.SQLDroidDriver");

            Connection con = DriverManager.getConnection(db);
            mlist = MeetingModel.getInstance().getMeetings();

            Statement st = con.createStatement();

            //DropTable(db);
            ResultSet rs = st.executeQuery("SELECT * FROM meetings");
            Log.i(LOG_TAG, "*** Query results:");
            while (rs.next()) {
            MeetingModel.getInstance().getMeetings().add(new Meeting(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
           }

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


    }


    public void DropTable(String db){

        try {

            Class.forName("org.sqldroid.SQLDroidDriver");

            Connection con = DriverManager.getConnection(db);
            Statement st = con.createStatement();
            st.executeUpdate("Drop table meetings");

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


        String db = dfFile;

        try
        {

         Class.forName("org.sqldroid.SQLDroidDriver");

            Connection con = DriverManager.getConnection(db);

            mlist = MeetingModel.getInstance().getMeetings();
            Statement st = con.createStatement();

            DropTable(db);


            st.executeUpdate("CREATE table IF NOT EXISTS meetings (ID VARCHAR(12), title VARCHAR(25), startTime VARCHAR(25),endTime VARCHAR(25) ,location VARCHAR(25))");
            Log.i(LOG_TAG, "*** Created table: onstop");

            for(int i = 0 ; i< mlist.size();i++) {

                st.executeUpdate("INSERT INTO meetings VALUES ('" + mlist.get(i).getId() + "','" + mlist.get(i).getTitle() + "','" + mlist.get(i).getStartTime() + "','" + mlist.get(i).getEndTime() + "','" + mlist.get(i).getLocation() + "')");

            }

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

}