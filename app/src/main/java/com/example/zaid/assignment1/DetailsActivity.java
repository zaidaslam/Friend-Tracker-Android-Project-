package com.example.zaid.assignment1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Zaid on 10/4/2017.
 */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PLAYGROUND", "Details ID: " + getIntent().getIntExtra("EXTRA_DETAILS_ID", -1));
    }
}
