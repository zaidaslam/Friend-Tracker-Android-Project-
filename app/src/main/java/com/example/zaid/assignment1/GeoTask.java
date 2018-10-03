package com.example.zaid.assignment1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GeoTask extends AsyncTask<String, Void , String> {
    ProgressDialog pd;
    Context mContext;
    Double duration;
    AsyncResponse response;


    public GeoTask(Context mContext) {
        this.mContext = mContext;
        response = (AsyncResponse) mContext;
    }

   @Override
    protected void onPostExecute(String aDouble) {
        super.onPostExecute(aDouble);

        if(aDouble!=null)
        {
            response.setDouble(aDouble);


        }
        else
            Toast.makeText(mContext, "Error4!Please Try Again wiht proper values", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url=new URL(params[0]);
            String [] WT = new String[2];
            URL [] urlArray = new URL[2];
            urlArray[0] = new URL(params[0]);
            urlArray[1] = new URL(params[1]);
            int count = 0;



            String name = params[2];
            String midpoint = params[3];

            for (int i=0;i< urlArray.length;i++) {

            HttpsURLConnection con= (HttpsURLConnection) urlArray[i].openConnection();
            con.setRequestMethod("GET");

            con.connect();
            con.getInputStream();
            int statuscode=con.getResponseCode();

                if(statuscode==HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                String json = sb.toString();
                Log.d("JSON", json);
                JSONObject root = new JSONObject(json);
                JSONArray array_rows = root.getJSONArray("rows");
                Log.d("JSON", "array_rows:" + array_rows);
                JSONObject object_rows = array_rows.getJSONObject(0);
                Log.d("JSON", "object_rows:" + object_rows);
                JSONArray array_elements = object_rows.getJSONArray("elements");
                Log.d("JSON", "array_elements:" + array_elements);
                JSONObject object_elements = array_elements.getJSONObject(0);
                Log.d("JSON", "object_elements:" + object_elements);
                JSONObject object_duration = object_elements.getJSONObject("duration");
                JSONObject object_distance = object_elements.getJSONObject("distance");

                Log.d("JSON", "object_duration:" + object_duration);

                WT[i] =  object_duration.getString("value");
                count++;

           }

                if(count == 2) {

                    return WT[0] + "," + WT[1] + "," + name + "," + midpoint;
                }
            }

        } catch (MalformedURLException e) {
            Log.d("error", "error1");
        } catch (IOException e) {
            Log.d("error", "error2");
        } catch (JSONException e) {
            Log.d("error","error3");
        }


        return null;
    }


}

