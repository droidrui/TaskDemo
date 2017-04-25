package com.droidrui.taskdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.droidrui.taskdemo.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void jumpCustomGank(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void jumpCustom(View view) {
        startActivity(new Intent(this, CustomActivity.class));
    }

    public void jumpLeak(View view) {
        startActivity(new Intent(this, LeakActivity.class));
    }

    public void jumpFragment(View view) {
        startActivity(new Intent(this, NewActivity.class));
    }
}
