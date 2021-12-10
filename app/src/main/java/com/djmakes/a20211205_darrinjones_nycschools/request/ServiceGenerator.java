package com.djmakes.a20211205_darrinjones_nycschools.request;

import static com.djmakes.a20211205_darrinjones_nycschools.utils.Constants.BASE_URL;

import com.djmakes.a20211205_darrinjones_nycschools.api.SchoolApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder.build();

    private static final SchoolApi schoolApi = retrofit.create(SchoolApi.class);

    public static SchoolApi getSchoolApi(){
        return schoolApi;
    }
}
