package com.example.letter

import android.os.Message
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

class Connect {
    val Urlbase="naver.com"

    data class messagemessage(
        val `data`: List<dataforallMessage>,
        val message: String
    )

    data class MessageParams(
        var lat: String ="",
        var lon: String ="",
        var cat: String ="",
        var cnt: Int = 0, // 제한 없음일 때는 -1
        var saw: Int = 0,
        var eti: String ="",
        var text: String ="",
        var name: String ="",
        var number: String ="",
        var major: String ="",
        var tact: String =""
    )
    data class dataforallMessage(

        var id: Int =0,
        var lat: String ="",
        var lon: String ="",
        var cat: String="",
        var cnt: Int=0,
        var saw: Int=0,
        var eti: String
    )

    data class ShowMessageStruct(
        val `data`: List<ALLMessageParams>,
        val message: String
    )
    data class ALLMessageParams(
        var lat: String ="",
        var lon: String ="",
        var cat: String ="",
        var cnt: Int = 0, // 제한 없음일 때는 -1
        var saw: Int = 0,
        var eti: String ="",
        var text: String ="",
        var name: String ="",
        var number: String ="",
        var major: String ="",
        var tact: String =""
    )


    interface POSTupdateMessage{
        @FormUrlEncoded
        @POST("/updateMessage")
        fun updateMessage(
            @Field("userID") userID:String,
            @Field("messageID") messageID:Int
        ): Call<POSTupdatestruct>
    }

    interface GETallMessage{
        @GET("allMessage/{userId}")
        fun allMessage(
            @Path("userId") userId:String
        ): Call<messagemessage>
    }

    interface POSTsendMessage{
        @FormUrlEncoded
        @POST("/sendMessage")
        fun sendMessage(
            @Field("lat") lat: String,
            @Field("lon") lon: String,
            @Field("cat") cat: String,
            @Field("cnt") cmt: Int,
            @Field("saw") saw: Int,
            @Field("eti") eti: String,
            @Field("text") text: String,
            @Field("name") name: String,
            @Field("number") number: String,
            @Field("major") major: String,
            @Field("tact") tact: String,
        ): Call<MessageParams>
    }

    interface GETshowMessage{
        @GET("/showMessage/{messageId}")
        fun showMessage(
            @Path("messageId") messageId:String
        ): Call<ShowMessageStruct>
    }

    interface POSTupdatesaw{
        @POST("/updateSaw/{messageID}")
        fun updateSaw(
            @Path("messageID") messageID:String
        ): Call<POSTupdatestruct>
    }
    data class POSTupdatestruct(
        val message: String
    )
}