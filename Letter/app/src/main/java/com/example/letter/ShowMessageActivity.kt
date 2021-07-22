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
            nextIntent.putExtra("name",name)
            nextIntent.putExtra("number",number)
            nextIntent.putExtra("major",major)
            nextIntent.putExtra("tact",tact)

            startActivity(nextIntent)
        }

        binding.Profile.setOnClickListener{
            val nextIntent= Intent(this, ShowProfileActivity::class.java)
            nextIntent.putExtra("name",name)
            nextIntent.putExtra("number",number)
            nextIntent.putExtra("major",major)
            nextIntent.putExtra("tact",tact)
            startActivity(nextIntent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }


        val retrofit = Retrofit.Builder()
            .baseUrl("http://25.61.78.177:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Connect.GETshowMessage::class.java)!!
        var letters=api.showMessage(2) // message ID            //id 값 받아오는걸로 수정

        letters.enqueue(object : Callback<Connect.ShowMessageStruct> {
            override fun onResponse(
                call: Call<Connect.ShowMessageStruct>,
                response: Response<Connect.ShowMessageStruct>
            ) {
                var tmp=response.body() as Connect.ShowMessageStruct
                println(tmp.data[1].id)                                   //인덱스 값 확인
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

                //get한 data를 화면에 표시
                binding.PersonPicker.setText("$saw / $cnt")
                binding.SetColor.setText(cat)
                when(cat){
                    "친목" -> binding.letter.setBackgroundColor(getResources().getColor(R.color.purpose1))
                    "멘토" -> binding.letter.setBackgroundColor(getResources().getColor(R.color.purpose2))
                    "멘티" -> binding.letter.setBackgroundColor(getResources().getColor(R.color.purpose3))
                    "취준" -> binding.letter.setBackgroundColor(getResources().getColor(R.color.purpose4))
                }
                binding.letter.setText(tex)
                binding.Profile.setText(number.substring(2,4)+"_"+name.substring(0,3)+"_"+major)
            }

            override fun onFailure(call: Call<Connect.ShowMessageStruct>, t: Throwable) {
                println("안됨")
                //////////////
            }
        })



    }


}