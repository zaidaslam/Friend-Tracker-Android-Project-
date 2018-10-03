package com.example.zaid.assignment1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import model.Database;
import model.Friend;
import model.FriendModel;
import viewmodel.FriendListAdapter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Zaid on 9/2/2017.
 */



public class EditContactActivity extends Activity {
      private String LOG_TAG = this.getClass().getName();
      DatePickerDialog datePickerDialog;
      private FriendListAdapter adapter;
      EditText date;
      EditText idView;
      EditText emailView;
      EditText nameView;
      Button btn;
      String Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);
        String id = getIntent().getStringExtra(FriendListActivity.ID_EXTRA);
        Log.d(LOG_TAG,String.format("FriendID: %s",id));
        final Friend friend = FriendModel.getInstance().getFriendById(id);
        idView = (EditText) findViewById(R.id.friend_id);
        nameView = (EditText) findViewById(R.id.friend_name);
        emailView = (EditText) findViewById(R.id.friend_email);


        date = (EditText) findViewById(R.id.friend_DOB);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(EditContactActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

           idView.setText(friend.getId());
           nameView.setText(friend.getName());
           emailView.setText(friend.getEmail());
           date.setText(friend.getDOB());

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                friend.setId(idView.getText().toString());
                friend.setName(nameView.getText().toString());
                friend.setEmail(emailView.getText().toString());
                friend.setDOB(date.getText().toString());
                Database obj = new Database();
                String db = "jdbc:sqldroid:" + getFilesDir() + "/test.db";
                obj.stop(db);
                Intent intent = new Intent(EditContactActivity.this, FriendListActivity.class);
                finish();
                //startActivity(intent);
            }
        });

    }
    @Override

     public void finish(){

        Intent resultntent = new Intent();
        resultntent.putExtra(FriendListActivity.FRIEND_RESULT_EXTRA,"Something");
        setResult(RESULT_OK, resultntent);
        super.finish();
    }
}

