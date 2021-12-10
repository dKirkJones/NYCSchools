package com.djmakes.a20211205_darrinjones_nycschools.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.djmakes.a20211205_darrinjones_nycschools.BaseActivity;
import com.djmakes.a20211205_darrinjones_nycschools.R;
import com.djmakes.a20211205_darrinjones_nycschools.models.School;
import com.djmakes.a20211205_darrinjones_nycschools.models.SchoolSAT;
import com.djmakes.a20211205_darrinjones_nycschools.viewModels.SatViewModel;

import java.util.List;

public class SatActivity extends BaseActivity {

    private static final String TAG = "SatActivity";
    public static final String EXTRA = "schoolDbn";

    private TextView satSchoolName;
    private TextView satNumberOfTests;
    private TextView satReadingAverage;
    private TextView satWritingAverage;
    private TextView satMathAverage;
    private TextView satErrorContainer;
    private ConstraintLayout constraintLayout;

    private SchoolSAT mSchoolSat;
    private School mSchoolList;
    private SatViewModel mSatViewModel;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sat);
        showProgressBar(true);

        mSatViewModel = new ViewModelProvider(this).get(SatViewModel.class);
        initViews();
        getIncomingIntent();
        subscribeObserver();
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("schoolDbn")){
            mSchoolList = getIntent().getParcelableExtra("schoolDbn");
            Log.d(TAG, "getIncomingIntent for SAT : " + mSchoolList.getDbn());
            Log.d(TAG, "getIncomingIntent for SAT : " + mSchoolList.getSchool_name());
            mSatViewModel.searchSchoolSat(mSchoolList.getDbn());
        }
    }

    private void subscribeObserver(){
        mSatViewModel.getSchoolSat().observe(this, new Observer<List<SchoolSAT>>() {
            @Override
            public void onChanged(List<SchoolSAT> schoolSATS) {
                if (schoolSATS != null){
                    for (SchoolSAT schoolSAT: schoolSATS){
                        if (schoolSAT.getDbn().equals(mSatViewModel.getDBn())){
                            Log.d(TAG, "SAT onChanged: _-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
                            Log.d(TAG, "onChanged: " + schoolSAT.getDbn());
                            Log.d(TAG, "onChanged: " + schoolSAT.getSchool_Name());
                            setSatProperties(schoolSATS);
                            mSatViewModel.setDidRetrieveSat(true);
                        }
                    }
                }
            }
        });
        mSatViewModel.isSchoolSatRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !mSatViewModel.didRetrieveSat()) {
                    Log.d(TAG, " >>> onChanged SAT <<<<: timed out...");
                    displayErrorScreen("Error Retrieving data." );
                }
            }
        });
    }

    private void displayErrorScreen(String errorMessage){
        satSchoolName.setText("Error");
        satNumberOfTests.setText("");
        satReadingAverage.setText("");
        satWritingAverage.setText("");
        satMathAverage.setText("");
        if (!errorMessage.equals("")){
            satErrorContainer.setText(errorMessage);
        }

        showParent();
        showProgressBar(false);
    }

    private void setSatProperties(List<SchoolSAT> schoolSATS){
        if (schoolSATS != null){
            for (SchoolSAT schoolSAT: schoolSATS){
                satSchoolName.setText(schoolSAT.getSchool_Name());
                satNumberOfTests.setText(schoolSAT.getNumOfSatTestTakers());
                satReadingAverage.setText(schoolSAT.getSatCriticalReadingAvgScore());
                satWritingAverage.setText(schoolSAT.getSatWritingAvgScore());
                satMathAverage.setText(schoolSAT.getSatMathAvgScore());
            }
        }else {
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent(){
        constraintLayout.setVisibility(View.VISIBLE);
    }

    private void initViews(){
        satSchoolName = findViewById(R.id.sat_school_name);
        satNumberOfTests = findViewById(R.id.sat_number_of_tests);
        satReadingAverage = findViewById(R.id.sat_reading_average);
        satWritingAverage = findViewById(R.id.sat_writing_average);
        satMathAverage = findViewById(R.id.sat_math_average);
        satErrorContainer = findViewById(R.id.sat_error_container);
        constraintLayout = findViewById(R.id.constraint_layout);
    }
}

