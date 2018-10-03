package com.example.zaid.assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Zaid on 10/6/2017.
 */

public class Settings extends AppCompatActivity {

    EditText min;
    Button save;
    TextView detail;
    Button show;
    EditText reminder;
    Button remind;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        min=(EditText)findViewById(R.id.editText);

        save=(Button)findViewById(R.id.button);

        detail=(TextView)findViewById(R.id.text);

        show =(Button)findViewById(R.id.show);

        reminder=(EditText)findViewById(R.id.remindertext);

        remind=(Button)findViewById(R.id.reminder);

    }

    public void saveInfo(View view){

        SharedPreferences sharedPref = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username",min.getText().toString());
        editor.apply();

        Toast.makeText(Settings.this,"Saved",Toast.LENGTH_LONG).show();
    }

    public void saveReminder(View view){

        SharedPreferences sharedPref = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("reminder",reminder.getText().toString());
        editor.apply();

        Toast.makeText(Settings.this,"Reminder Saved",Toast.LENGTH_LONG).show();
    }

    public void displayData(View view){
        SharedPreferences sharedPref = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String minutes = sharedPref.getString("reminder","");

        detail.setText(minutes);
    }


}
