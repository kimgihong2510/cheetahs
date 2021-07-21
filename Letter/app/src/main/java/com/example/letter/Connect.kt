package com.example.letter

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class Connect {
    val Urlbase="naver.com"


    data class MessageParams(
        var id: Int =0,
        var lat: String ="",
        var lon: String ="",
        var cat: String ="",
        var cnt: Int = 0, // 제한 없음일 때는 -1
        var saw: Int = 0,
        var eti: String ="",
        var tex: String ="",
        var name: String ="",
        var number: String ="",
        var major: String ="",
        var tact: String =""
    )
    data class dataforallMessage(
        var id: Int =0,
        var lat: String ="",
        var lon: String ="",
    )
    data class dataforshowMessage(
        var id: Int =0,
    )



    interface GETallMessage{
        @GET("/allMessage")
        fun allMessage(): Call<dataforallMessage>
    }

    interface POSTsendMessage{
        @POST("/sendMessage")
        fun sendMessage(
            @Field("lat") lat: String,
            @Field("lon") lon: String,
            @Field("cat") cat: Int,
            @Field("cnt") cmt: Int,
            @Field("saw") saw: String,
            @Field("eti") eti: String,
            @Field("text") text: String,
            @Field("name") name: String,
            @Field("number") number: String,
            @Field("major") major: String,
            @Field("tact") tact: String,
        ): Call<String>
    }

    interface GETshowMessage{
        @GET("/showMessage")
        fun showMessage(
            @Query("id") id: Int,
        ): Call<MessageParams>
    }
}