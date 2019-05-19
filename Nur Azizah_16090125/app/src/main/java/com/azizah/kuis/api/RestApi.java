package com.azizah.kuis.api;

import com.azizah.kuis.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

    public interface RestApi {
        //insert
        @FormUrlEncoded
        @POST("insert.php")
        Call<ResponseModel> senddata(@Field("nama") String nama,
                                        @Field("kode_listrik") String kode_listrik,
                                        @Field("kategori") String kategori);

        //read
        @GET("read.php")
        Call<ResponseModel> getdata();

        //update menggunakan 3 parameter
        @FormUrlEncoded
        @POST("update.php")
        Call<ResponseModel> updateData( @Field("id") String id,
                                        @Field("nama") String nama,
                                        @Field("kode_listrik") String kode_listrik,
                                        @Field("kategori") String kategori);
        //delete menggunakan parameter id
        @FormUrlEncoded
        @POST("delete.php")
        Call<ResponseModel> deleteData(@Field("id") String id);
}
