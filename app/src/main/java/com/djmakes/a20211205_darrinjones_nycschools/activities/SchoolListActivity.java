package com.djmakes.a20211205_darrinjones_nycschools.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.djmakes.a20211205_darrinjones_nycschools.BaseActivity;
import com.djmakes.a20211205_darrinjones_nycschools.R;
import com.djmakes.a20211205_darrinjones_nycschools.adapter.OnSchoolListener;
import com.djmakes.a20211205_darrinjones_nycschools.adapter.SchoolRecyclerViewAdapter;
import com.djmakes.a20211205_darrinjones_nycschools.models.School;
import com.djmakes.a20211205_darrinjones_nycschools.viewModels.MainListViewModel;

import java.util.List;

public class SchoolListActivity extends BaseActivity implements OnSchoolListener {
    private static final String TAG = "SchoolListActivity";

    private MainListViewModel mMainListViewModel;
    private RecyclerView mRecyclerView;
    private SchoolRecyclerViewAdapter mSchoolRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        mRecyclerView = findViewById(R.id.rv_school_list);

        mMainListViewModel = new ViewModelProvider(this).get(MainListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        testRetrofitReqs();

      setSupportActionBar(findViewById(R.id.tool_bar));
    }

    private void subscribeObservers(){
        mSchoolRecyclerViewAdapter.displayLoading();
        mMainListViewModel.setIsPerformingQuery(false);

        mMainListViewModel.getSchoolList().observe(this, new Observer<List<School>>() {
            @Override
            public void onChanged(List<School> schoolLists) {
                if (schoolLists != null){
                //    Testing.printSchools(TAG, schoolLists);
                    mSchoolRecyclerViewAdapter.setSchoolList(schoolLists);
                }
            }
        });
        mMainListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "onChanged: Query Exhausted");

                if (aBoolean){
                    mSchoolRecyclerViewAdapter.setQueryExhausted();
                }
            }
        });
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSchoolRecyclerViewAdapter = new SchoolRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mSchoolRecyclerViewAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mRecyclerView.canScrollVertically(1)) {
                    //search next page
                    mMainListViewModel.searchNextPage();
                }
            }
        });
    }



    private void searchSchoolsApi(int limit, int offset) {
        mMainListViewModel.searchSchoolsApi(limit, offset);
    }

    private void testRetrofitReqs() {
        searchSchoolsApi(20, 1);

        //TestClient.getInstance().checkSchoolListRetrofit();
        //TestClient.getInstance().checkSingleItemFromListRetrofit();
        //TestClient.getInstance().checkSchoolSATRetrofit();

    }

    @Override
    public void onSchoolClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("schoolList" , mSchoolRecyclerViewAdapter.getSelectedSchool(position));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mMainListViewModel.onBackPressed()){
            super.onBackPressed();
        }else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_schools){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.school_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
