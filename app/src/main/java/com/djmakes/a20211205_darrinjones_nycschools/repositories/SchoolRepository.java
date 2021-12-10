package com.djmakes.a20211205_darrinjones_nycschools.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.djmakes.a20211205_darrinjones_nycschools.models.School;
import com.djmakes.a20211205_darrinjones_nycschools.models.SchoolSAT;
import com.djmakes.a20211205_darrinjones_nycschools.request.SchoolApiClient;

import java.util.List;

public class SchoolRepository {

    private static SchoolRepository instance;
    private SchoolApiClient mSchoolApiClient;
    private int mOffset;
    private int mLimit;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<School>> mSchoolList = new MediatorLiveData<>();

    public static SchoolRepository getInstance(){
        if (instance == null){
            instance = new SchoolRepository();
        }
        return instance;
    }

    private SchoolRepository() {
        mSchoolApiClient = SchoolApiClient.getInstance();
        initMediators();
    }
    private void initMediators(){
        LiveData<List<School>> schoolListApiSource = mSchoolApiClient.getSchoolList();
        mSchoolList.addSource(schoolListApiSource, new Observer<List<School>>() {
            @Override
            public void onChanged(List<School> schoolLists) {
                if (schoolLists != null){
                    mSchoolList.setValue(schoolLists);
                    doneQuery(schoolLists);
                }else {
                    //search database
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<School> list){
        if (list != null){
            if (list.size() % 10 != 0){
                mIsQueryExhausted.setValue(true);
            }
        }else {
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

    public LiveData<List<School>> getSchoolList(){
        return mSchoolList;
    }
    public LiveData<List<School>> getSchool(){
        return mSchoolApiClient.getSchool();
    }
    public LiveData<List<SchoolSAT>> getSchoolSat(){
        return mSchoolApiClient.getSchoolSat();
    }
    public LiveData<Boolean> isSchoolRequestTimedOut() {
        return mSchoolApiClient.isSchoolRequestTimedOut();
    }
    public LiveData<Boolean> isSchoolSatRequestTimedOut() {
        return mSchoolApiClient.isSchoolSatRequestTimedOut();
    }

    public void searchSingleSchoolApi(String dbn){
        mSchoolApiClient.searchSingleSchoolApi(dbn);
    }
    public void searchSchoolSATApi(String dbn){
        mSchoolApiClient.searchSchoolSatApi(dbn);
    }

    public void searchSchoolsApi(int limit, int offset){
        if (offset == 0){
            offset = 1;
        }
        mLimit = limit;
        mOffset = offset;
        mIsQueryExhausted.setValue(false);
        mSchoolApiClient.searchSchoolsApi(offset);
    }

    public void searchNextPage(){
        searchSchoolsApi(mLimit, mOffset + 19);
    }

    public void cancelRequest(){
        mSchoolApiClient.cancelRequest();
    }

}
