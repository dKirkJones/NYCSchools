package com.djmakes.a20211205_darrinjones_nycschools.request;

import static com.djmakes.a20211205_darrinjones_nycschools.utils.Constants.APP_TOKEN;
import static com.djmakes.a20211205_darrinjones_nycschools.utils.Constants.LIMIT;
import static com.djmakes.a20211205_darrinjones_nycschools.utils.Constants.NETWORK_TIMEOUT;
import static com.djmakes.a20211205_darrinjones_nycschools.utils.Constants.OFFSET;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.djmakes.a20211205_darrinjones_nycschools.models.School;
import com.djmakes.a20211205_darrinjones_nycschools.models.SchoolSAT;
import com.djmakes.a20211205_darrinjones_nycschools.utils.AppExecutors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class SchoolApiClient {

    private static final String TAG = "SchoolApiClient";
    private static SchoolApiClient instance;
    private final MutableLiveData<List<School>> mSchoolList;
    private final MutableLiveData<List<School>> mSchool;
    private final MutableLiveData<List<SchoolSAT>> mSchoolSAT;
    private final MutableLiveData<Boolean> mSchoolRequestTimeout = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSchoolSatRequestTimeout = new MutableLiveData<>();
    private RetrieveSchoolListRunnable mRetrieveSchoolListRunnable;
    private RetrieveSchoolRunnable mRetrieveSchoolRunnable;
    private RetrieveSchoolSatRunnable mRetrieveSchoolSatRunnable;


    public static SchoolApiClient getInstance() {
        if (instance == null) {
            instance = new SchoolApiClient();
        }
        return instance;
    }

    private SchoolApiClient() {
        mSchoolList = new MutableLiveData<>();
        mSchool = new MutableLiveData<>();
        mSchoolSAT = new MutableLiveData<>();
    }

    public LiveData<List<School>> getSchoolList() {
        return mSchoolList;
    }

    public LiveData<List<School>> getSchool() {
        return mSchool;
    }
    public LiveData<List<SchoolSAT>> getSchoolSat() {
        return mSchoolSAT;
    }
    public LiveData<Boolean> isSchoolRequestTimedOut() {
        return mSchoolRequestTimeout;
    }
    public LiveData<Boolean> isSchoolSatRequestTimedOut() {
        return mSchoolSatRequestTimeout;
    }

    public void searchSchoolsApi(int pageNumber) {
        if (mRetrieveSchoolListRunnable != null) {
            mRetrieveSchoolListRunnable = null;
        }
        mRetrieveSchoolListRunnable = new RetrieveSchoolListRunnable(pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveSchoolListRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Let user know The network timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void searchSingleSchoolApi(String dbn) {
        if (mRetrieveSchoolRunnable != null) {
            mRetrieveSchoolRunnable = null;
        }
        mRetrieveSchoolRunnable = new RetrieveSchoolRunnable(dbn);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveSchoolRunnable);

        mSchoolRequestTimeout.setValue(false);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                mSchoolRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void searchSchoolSatApi(String dbn){
        if (mRetrieveSchoolSatRunnable != null){
            mRetrieveSchoolSatRunnable = null;
        }
        mRetrieveSchoolSatRunnable = new RetrieveSchoolSatRunnable(dbn);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveSchoolSatRunnable);

        mSchoolSatRequestTimeout.setValue(false);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                mSchoolSatRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveSchoolListRunnable implements Runnable {

        private final int offset;
        boolean cancelRequest;

        public RetrieveSchoolListRunnable(int offset) {
            this.offset = offset;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

                //TestClient.getInstance().checkSchoolListRetrofit();
                Response<List<School>> response = getSchools(offset).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    Log.d(TAG, "<<onResponse List>>: Code: " + response.code());

                    List<School> schoolLists = new ArrayList<>(response.body());
                    if (offset >= 1) {
                        mSchoolList.postValue(schoolLists);
                    } else {
                        List<School> currentSchools = mSchoolList.getValue();
                        //currentSchools.add((SchoolList) schoolLists);
                        currentSchools.addAll(schoolLists);
                        mSchoolList.postValue(currentSchools);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "runError: " + error);
                    mSchoolList.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mSchoolList.postValue(null);
            }

        }

        private Call<List<School>> getSchools(int offset) {
            return ServiceGenerator.getSchoolApi().searchSchools(
                    APP_TOKEN,
                    LIMIT,
                    OFFSET
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "CancelRequest: cancelling search request");
            cancelRequest = true;
        }
    }

    private class RetrieveSchoolRunnable implements Runnable {

        private final String dbn;
        boolean cancelRequest;

        public RetrieveSchoolRunnable(String dbn) {
            this.dbn = dbn;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

                //TestClient.getInstance().checkSchoolListRetrofit();
                Response<List<School>> response = getSchool(dbn).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: Server: " + response.toString());
                    Log.d(TAG, "<<onResponse>>: Code: " + response.code());
                    Log.d(TAG, "Response: " + response.body());
                    List<School> schoolLists = new ArrayList<>(response.body());
                    for (School schoolList : schoolLists) {
                        Log.d(TAG, " School Dbn: " + schoolList.getDbn());
                        Log.d(TAG, " School Name: " + schoolList.getSchool_name());
                    }
                    mSchool.postValue(schoolLists);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "runError: " + error);
                    mSchool.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mSchool.postValue(null);
            }

        }

        private Call<List<School>> getSchool(String dbn) {
            return ServiceGenerator.getSchoolApi().searchOneSchool(
                    APP_TOKEN,
                    dbn
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "CancelRequest: cancelling search request");
            cancelRequest = true;
        }
    }

    private class RetrieveSchoolSatRunnable implements Runnable{
        private final String dbn;
        boolean cancelRequest;

        public RetrieveSchoolSatRunnable(String dbn) {
            this.dbn = dbn;
        }

        @Override
        public void run() {
            try {
                Response<List<SchoolSAT>> response = getSchoolSat(dbn).execute();
                if (cancelRequest){
                    return;
                }
                if (response.code() == 200){
                    Log.d(TAG, "<<onResponse>>: SATCode: " + response.code());
                    Log.d(TAG, "ResponseSAT: " + response.body());
                    List<SchoolSAT> schoolSATS = new ArrayList<>(response.body());
                    for (SchoolSAT schoolSAT: schoolSATS){
                        Log.d(TAG, " SchoolSat Dbn: " + schoolSAT.getDbn());
                        Log.d(TAG, "SchoolSAT Name: " + schoolSAT.getSchool_Name());
                    }
                    mSchoolSAT.postValue(schoolSATS);
                }else {
                    String error = response.errorBody().string();
                    Log.d(TAG, "satError: " + error);
                    mSchoolSAT.postValue(null);
                }
            }catch (IOException eSat){
                eSat.printStackTrace();
                mSchoolSAT.postValue(null);
            }
        }

        private Call<List<SchoolSAT>> getSchoolSat(String dbn){
            return ServiceGenerator.getSchoolApi().getSchoolSat(
                    APP_TOKEN,
                    dbn
            );
        }
        private void cancelRequest(){
            Log.d(TAG, "CancelRequest: cancelling SAT request");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (mRetrieveSchoolListRunnable != null) {
            mRetrieveSchoolListRunnable.cancelRequest();
        }
        if (mRetrieveSchoolRunnable != null) {
            mRetrieveSchoolRunnable.cancelRequest();
        }
        if (mRetrieveSchoolSatRunnable != null) {
            mRetrieveSchoolSatRunnable.cancelRequest();
        }
    }
}

