package com.djmakes.a20211205_darrinjones_nycschools.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.djmakes.a20211205_darrinjones_nycschools.BaseActivity;
import com.djmakes.a20211205_darrinjones_nycschools.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotToSchoolList();
            }
        });
    }
    private void gotToSchoolList(){
        Intent intent = new Intent(this, SchoolListActivity.class);
        startActivity(intent);
    }
}