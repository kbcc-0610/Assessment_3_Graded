package com.example.assignment_3.Database;

import com.example.assignment_3.Model.Record;
import com.example.assignment_3.Model.Setting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RemoteGameDB {
    //GUI for Records table
    @POST("Records")
    Call<Record> CreateRecord(@Body Record record);

    @GET("Records")
    Call<List<Record>> getAllRecords();

    @GET("Records/{id}")
    Call<Record> GetContactByID(@Path("id") int id);

    @PUT("Contacts/{id}")
    Call<Void> UpdateRecordByID(@Path("id") int id, @Body Record record);

    @DELETE("Contacts/{id}")
    Call<Record> DeleteContactByID(@Path("id") int id);

    //GUI for Setting table
    @POST("Settings")
    Call<Setting> CreateSetting(@Body Setting setting);

    @GET("Settings")
    Call<List<Setting>> getAllSetting();

    @GET("Settings/{id}")
    Call<Setting> GetSettingByID(@Path("id") int id);

    @PUT("Settings/{id}")
    Call<Void> UpdateSettingByID(@Path("id") int id, @Body Setting setting);

    @DELETE("Settings/{id}")
    Call<Setting> DeleteSettingByID(@Path("id") int id);
}
