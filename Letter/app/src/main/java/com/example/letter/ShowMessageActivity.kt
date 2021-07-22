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
    private var id : Int = 0;
    private var lat: String = ""
    private var lon: String = ""
    private var cat: String = ""
    private var cnt: Int = 0
    private var saw: Int = 0
    private var eti: String = ""
    private var text: String = ""
    private var name: String = ""
    private var number: String = ""
    private var major: String = ""
    private var tact: String = ""
    private lateinit var cpyname: String
    private lateinit var cpynumber: String
    private lateinit var cpymajor: String
    private lateinit var cpytact: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowMessageBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        id = Integer.parseInt(intent.getStringExtra("id").toString())
        cpyname = intent.getStringExtra("cpyname").toString()
        cpynumber = intent.getStringExtra("cpynumber").toString()
        cpymajor = intent.getStringExtra("cpymajor").toString()
        cpytact = intent.getStringExtra("cpytact").toString()

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
            val nextIntent = Intent(this, MapsActivity::class.java)
            nextIntent.putExtra("name", cpyname)
            nextIntent.putExtra("number", cpynumber)
            nextIntent.putExtra("major", cpymajor)
            nextIntent.putExtra("tact", cpytact)
            startActivity(nextIntent)
            finish()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://25.61.78.177:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        println(id)
        val api = retrofit.create(Connect.GETshowMessage::class.java)!!
        var letters=api.showMessage(id.toString()) // message ID            //id 값 받아오는걸로 수정

        letters.enqueue(object : Callback<Connect.ShowMessageStruct> {
            override fun onResponse(
                call: Call<Connect.ShowMessageStruct>,
                response: Response<Connect.ShowMessageStruct>
            ) {
                println(response.body())
                println(response)
                var tmp=response.body() as Connect.ShowMessageStruct
                //println(tmp.data[1].id)                                   //인덱스 값 확인
                lat = tmp.data[0].lat
                lon = tmp.data[0].lon
                cat = tmp.data[0].cat
                cnt = tmp.data[0].cnt
                saw = tmp.data[0].saw+1
                eti = tmp.data[0].eti
                text = tmp.data[0].text
                name = tmp.data[0].name
                number = tmp.data[0].number
                major = tmp.data[0].major
                tact = tmp.data[0].tact

                //get한 data를 화면에 표시
                binding.PersonPicker.setText("$saw / $cnt")
                binding.SetColor.setText(cat)
                when(cat){
                    "친목" -> binding.letter.setBackgroundColor(getResources().getColor(R.color.purpose1))
                    "멘토" -> binding.letter.setBackgroundColor(getResources().getColor(R.color.purpose2))
                    "멘티" -> binding.letter.setBackgroundColor(getResources().getColor(R.color.purpose3))
                    "취준" -> binding.letter.setBackgroundColor(getResources().getColor(R.color.purpose4))
                }
                binding.letter.setText("$text");
                println("text : $text")
                binding.Profile.setText(number.substring(2,4)+"_"+name.substring(0,3)+"_"+major)
            }

            override fun onFailure(call: Call<Connect.ShowMessageStruct>, t: Throwable) {
                println("안됨")
                //////////////
            }
        })

        val retrofit2 = Retrofit.Builder()
            .baseUrl("http://25.61.78.177:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var api2 = retrofit.create(Connect.POSTupdatesaw::class.java)!!
        var letters2=api2.updateSaw(id.toString()) // message ID            //id 값 받아오는걸로 수정

        letters2.enqueue(object : Callback<Connect.POSTupdatestruct> {
            override fun onResponse(
                call: Call<Connect.POSTupdatestruct>,
                response: Response<Connect.POSTupdatestruct>
            ) {
                println(response.body())
                println(response)
            }

            override fun onFailure(call: Call<Connect.POSTupdatestruct>, t: Throwable) {
                println("안됨")
                //////////////
            }
        })
    }
}