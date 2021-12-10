package com.djmakes.a20211205_darrinjones_nycschools.utils;

import com.djmakes.a20211205_darrinjones_nycschools.api.SchoolApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static SchoolApi getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SchoolApi.class);
    }
}

