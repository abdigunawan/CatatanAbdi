package com.abdigunawan.catatanabdi

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {

    @GET("data.php")
    fun data() : Call<NotesModel>

    @FormUrlEncoded
    @POST("create.php")
    fun create(
        @Field("judul")judul : String,
        @Field("catatan")catatan : String
    ) : Call<SubmitModel>

    @FormUrlEncoded
    @POST("update.php")
    fun update(
        @Field("id")id : String,
        @Field("judul")judul : String,
        @Field("catatan")catatan : String
    ) : Call<SubmitModel>

    @FormUrlEncoded
    @POST("delete.php")
    fun dalete(
        @Field("id")id : String
    ) : Call<SubmitModel>



}