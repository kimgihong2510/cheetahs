package com.example.letter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letter.databinding.ActivitySendMessageBinding
import com.example.letter.databinding.ActivityShowMessageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowMessageBinding

    var lat: String = ""
    var lon: String = ""
    var cat: String = ""
    var cnt: Int = 0
    var saw: Int = 0
    var eti: String = ""
    var tex: String = ""
    var name: String = ""
    var number: String = ""
    var major: String = ""
    var tact: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowMessageBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //프로필 사진, 프로필 눌리면 프로필 페이지로 연결'
        binding.ProfilePicture.setOnClickListener{
            val nextIntent= Intent(this, ShowProfileActivity::class.java)
            startActivity(nextIntent)
        }
        binding.Profile.setOnClickListener{
            val nextIntent= Intent(this, ShowProfileActivity::class.java)
            startActivity(nextIntent)
        }



        val retrofit = Retrofit.Builder()
            .baseUrl("http://25.61.78.177:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Connect.GETshowMessage::class.java)!!
        var letters=api.showMessage(2) // message ID

        letters.enqueue(object : Callback<Connect.ShowMessageStruct> {
            override fun onResponse(
                call: Call<Connect.ShowMessageStruct>,
                response: Response<Connect.ShowMessageStruct>
            ) {
                var tmp=response.body() as Connect.ShowMessageStruct
                println(tmp.data[1].id)
                lat = tmp.data[1].lat
                lon = tmp.data[1].lon
                cat = tmp.data[1].cat
                cnt = tmp.data[1].cnt
                saw = tmp.data[1].saw
                eti = tmp.data[1].eti
                tex = tmp.data[1].tex
                name = tmp.data[1].name
                number = tmp.data[1].number
                major = tmp.data[1].major
                tact = tmp.data[1].tact

            }

            override fun onFailure(call: Call<Connect.ShowMessageStruct>, t: Throwable) {
                println("안됨")
                //////////////
            }
        })

    }


}