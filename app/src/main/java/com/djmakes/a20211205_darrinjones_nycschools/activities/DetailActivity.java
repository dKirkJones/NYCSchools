package com.djmakes.a20211205_darrinjones_nycschools.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.djmakes.a20211205_darrinjones_nycschools.BaseActivity;
import com.djmakes.a20211205_darrinjones_nycschools.R;
import com.djmakes.a20211205_darrinjones_nycschools.models.School;
import com.djmakes.a20211205_darrinjones_nycschools.viewModels.DetailViewModel;

import java.util.List;

public class DetailActivity extends BaseActivity {

    private static final  String TAG = "DetailActivity";
    private TextView detailSchoolName;
    private TextView detailSchoolAddress;
    private TextView detailSchoolCity;
    private TextView detailSchoolPhone;
    private TextView detailSchoolOverView;
    private Button satButton;
    private ScrollView mScrollView;
    private DetailViewModel mDetailViewModel;
    private School mSchoolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        showProgressBar(true);

        mDetailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        initViews();
        getIncomingIntent();
        subscribeObserver();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("schoolList")) {
            mSchoolList = getIntent().getParcelableExtra("schoolList");
            Log.d(TAG, "getIncomingIntent: " + mSchoolList.getDbn());
            Log.d(TAG, "getIncomingIntent: " + mSchoolList.getOverview_paragraph());
            mDetailViewModel.searchSingleSchoolApi(mSchoolList.getDbn());
        }
    }

    private void subscribeObserver() {
        mDetailViewModel.getSchool().observe(this, new Observer<List<School>>() {
            @Override
            public void onChanged(List<School> schoolLists) {
                if (schoolLists != null) {
                    for (School schoolList : schoolLists) {
                        if (schoolList.getDbn().equals(mDetailViewModel.getDbn())) {
                            Log.d(TAG, "Detail onChanged: ---><----------------------><---");
                            Log.d(TAG, "onChanged: " + schoolList.getSchool_name());
                            Log.d(TAG, "onChanged: " + schoolList.getOverview_paragraph());
                            setSchoolProperties(schoolLists);
                            mDetailViewModel.setDidRetrieveSchool(true);
                        }
                    }
                }
            }
        });
        mDetailViewModel.isSchoolRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !mDetailViewModel.didRetrieveSchool()) {
                    Log.d(TAG, "<<onChanged>>: timed out");
                    displayErrorMessage("Error retrieving data." + "\nCheck Network Connection.");
                }
            }
        });
    }

    private void initViews() {
        detailSchoolName = findViewById(R.id.detail_school_name);
        detailSchoolAddress = findViewById(R.id.detail_school_address);
        detailSchoolCity = findViewById(R.id.detail_school_city);
        detailSchoolPhone = findViewById(R.id.detail_school_phone);
        detailSchoolOverView = findViewById(R.id.detail_school_overview);
        satButton = findViewById(R.id.sat_button);
        mScrollView = findViewById(R.id.parent);

    }

    private void setSchoolProperties(List<School> schoolLists) {
        if (schoolLists != null) {
            for (School schoolList : schoolLists) {
                detailSchoolName.setText(schoolList.getSchool_name());
                detailSchoolAddress.setText(schoolList.getPrimary_address_line_1());
                detailSchoolCity.setText(schoolList.getCity());
                detailSchoolPhone.setText(schoolList.getPhone_number());
                detailSchoolOverView.setText(schoolList.getOverview_paragraph());
            }
        }
        clickForSchoolPhone();
        clickForSatActivity();
        showParent();
        showProgressBar(false);
    }

    private void showParent() {
        mScrollView.setVisibility(View.VISIBLE);
    }

    private void displayErrorMessage(String errorMessage){
        detailSchoolName.setText("ERROR!");
        detailSchoolAddress.setText("");
        detailSchoolCity.setText("Could not retrieve selected school...");
        detailSchoolPhone.setText("");
        satButton.setText("");
        detailSchoolOverView.setText(errorMessage);
        showParent();
        showProgressBar(false);
    }

    private void clickForSchoolPhone(){
        detailSchoolPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Phone Button", "Clicked: " + mSchoolList.getPhone_number().toString());

                String uri = "tel:" + mSchoolList.getPhone_number();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });
    }

    private void clickForSatActivity(){
        satButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent satIntent = new Intent(DetailActivity.this, SatActivity.class);
                Log.d(TAG, "SchoolExtraSend dbn: " + mSchoolList.getDbn());
                satIntent.putExtra("schoolDbn", mSchoolList);
                DetailActivity.this.startActivity(satIntent);
            }
        });
    }
}
