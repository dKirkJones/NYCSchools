package com.djmakes.a20211205_darrinjones_nycschools.api;

import com.djmakes.a20211205_darrinjones_nycschools.models.School;
import com.djmakes.a20211205_darrinjones_nycschools.models.SchoolSAT;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SchoolApi {
    // GET SCHOOL, looks at the url end point
    @GET("resource/s3k6-pzi2.json")
    Call<List<School>> searchSchools(
            @Query("$$app_token") String app_token,
            @Query("$limit") String limit,
            @Query("$offset") int offset
    );


    //Get Single response for SAT
    @GET("resource/f9bf-2cp4.json")
    Call <List<SchoolSAT>> getSchoolSat(
            @Query("$$app_token") String app_token,
            @Query("dbn") String dbn
    );

    @GET("resource/s3k6-pzi2.json")
    Call<List<School>> searchOneSchool(
            @Query("$$app_token") String app_token,
            @Query("dbn") String dbn
    );

}
