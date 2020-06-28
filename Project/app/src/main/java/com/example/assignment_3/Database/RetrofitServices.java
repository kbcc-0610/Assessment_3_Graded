package com.example.assignment_3.Database;

import android.util.Log;

import com.example.assignment_3.Model.Record;
import com.example.assignment_3.Model.Setting;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServices {
    private static RetrofitServices retrofitServicesInstance = null;
    private static RemoteGameDB service;
    private String TAG = this.getClass().getSimpleName();

    private RetrofitServices() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.0.32:5000/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(RemoteGameDB.class);
    }

    public static RetrofitServices getInstance() {
        if (retrofitServicesInstance == null) {
            retrofitServicesInstance = new RetrofitServices();
        }
        return retrofitServicesInstance;
    }

    public void BackUpRecords(Record record){
        Call<Record> recordCall = service.CreateRecord(record);
        recordCall.enqueue((new Callback<Record>() {
            @Override
            public void onResponse(Call<Record> call, Response<Record> response) {
                Record r = response.body();
                return;
            }

            @Override
            public void onFailure(Call<Record> call, Throwable t) {
                Log.d(TAG, "Error with insert records to server");
            }
        }));
    }

    public void BackUpdSetting(Setting setting){
        Call<Setting> settingCall = service.CreateSetting(setting);
        settingCall.enqueue(new Callback<Setting>() {
            @Override
            public void onResponse(Call<Setting> call, Response<Setting> response) {
                Setting s = response.body();
                return;
            }

            @Override
            public void onFailure(Call<Setting> call, Throwable t) {
                Log.d(TAG, "Error with insert setting to server");
            }
        });
    }
}
