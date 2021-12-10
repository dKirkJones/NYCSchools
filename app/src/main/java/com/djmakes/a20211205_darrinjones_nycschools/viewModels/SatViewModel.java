package com.djmakes.a20211205_darrinjones_nycschools.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.djmakes.a20211205_darrinjones_nycschools.models.SchoolSAT;
import com.djmakes.a20211205_darrinjones_nycschools.repositories.SchoolRepository;

import java.util.List;

public class SatViewModel extends ViewModel {
    private SchoolRepository mSchoolRepository;
    private String mDBn;
    private boolean mDidRetrieveSat;

    public SatViewModel(){
        mSchoolRepository = SchoolRepository.getInstance();
    }

    public LiveData<List<SchoolSAT>> getSchoolSat(){
        return mSchoolRepository.getSchoolSat();
    }

    public LiveData<Boolean> isSchoolSatRequestTimedOut() {
        return mSchoolRepository.isSchoolSatRequestTimedOut();
    }

    public void searchSchoolSat(String dbn){
        mDBn = dbn;
        mSchoolRepository.searchSchoolSATApi(dbn);
    }

    public String getDBn() {
        return mDBn;
    }

    public boolean didRetrieveSat() {
        return mDidRetrieveSat;
    }

    public void setDidRetrieveSat(boolean mDidRetrieveSat) {
        this.mDidRetrieveSat = mDidRetrieveSat;
    }
}
