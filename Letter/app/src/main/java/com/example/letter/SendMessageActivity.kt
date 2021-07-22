package com.example.letter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.letter.databinding.ActivitySendMessageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SendMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendMessageBinding
    private lateinit var number:String
    private lateinit var major:String
    private lateinit var name:String
    private lateinit var tact:String
    private lateinit var lat:String
    private lateinit var lon:String

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        this.name = intent.getStringExtra("name").toString()
        this.number = intent.getStringExtra("number").toString()
        this.major = intent.getStringExtra("major").toString()
        this.tact = intent.getStringExtra("tact").toString()
        this.lat = intent.getStringExtra("lat").toString()
        this.lon = intent.getStringExtra("lon").toString()

        var cat : String = ""
        var cnt : Int = 0
        var saw : Int = 0
        var eti : String = ""
        var tex : String = ""

        if(major.contains("컴퓨터"))
            major = "컴학"
        else if(major.contains("전기"))
            major = "전기"
        else
            major = "전자"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)

        var toprint=number.substring(2,4)+"_"+name.substring(0,3)+"_"+major

        binding = ActivitySendMessageBinding.inflate(layoutInflater)
        val view=binding.root
        binding.Profile.text=toprint
        setContentView(view)

        //인원수, 시간, 분 롤
        binding.PersonPicker.minValue=0
        binding.PersonPicker.maxValue=10
        val arrayPicker= arrayOf("제한 없음", "1명", "2명", "3명", "4명", "5명", "6명", " 7명", "8명", "9명", "10명")
        binding.PersonPicker.displayedValues= arrayPicker

        binding.HourPicker.minValue=0
        binding.HourPicker.maxValue=30

        binding.MinPicker.minValue=0
        binding.MinPicker.maxValue=59

        binding.PersonPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            when{
                i2 == 0 -> cnt = 0;
                i2 == 1 -> cnt = 1;
                i2 == 2 -> cnt = 2;
                i2 == 3 -> cnt = 3;
                i2 == 4 -> cnt = 4;
                i2 == 5 -> cnt = 5;
                i2 == 6 -> cnt = 6;
                i2 == 7 -> cnt = 7;
                i2 == 8 -> cnt = 8;
                i2 == 9 -> cnt = 9;
                i2 == 10 -> cnt = 10;
            }
        }

        binding.HourPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            //eti의 시 부분 : 현재 시 + i2           유준호 생각: 3600*i2 해서 시간 전체 다 잡고, db에 올리기 전에 원하는 형태로 바꾸는 것이 쉬울듯
        }
        binding.MinPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            //eti의 분 부분 : 현재 분 + i2
        }

        //쪽찌 종류
        val purpose= arrayOf("친목", "멘토", "멘티", "취준")
        val myAdapter=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, purpose)
        binding.SetColor.adapter=myAdapter

        //쪽찌마다 색깔바뀌게 만들기
        binding.SetColor.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(id.toInt()==0){
                    binding.letter.setBackgroundResource(R.color.purpose1)
                    cat = "친목"
                }
                if(id.toInt()==1){
                    binding.letter.setBackgroundResource(R.color.purpose2)
                    cat = "멘토"
                }
                if(id.toInt()==2){
                    binding.letter.setBackgroundResource(R.color.purpose3)
                    cat = "멘티"
                }
                if(id.toInt()==3){
                    binding.letter.setBackgroundResource(R.color.purpose4)
                    cat = "취준"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        //프로필 사진
        binding.ProfilePicture.setImageResource(R.drawable.ic_launcher_background)

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
        binding.btnBack.setOnClickListener{
            val nextIntent = Intent(this, MapsActivity::class.java)
            nextIntent.putExtra("name",name)
            nextIntent.putExtra("number",number)
            nextIntent.putExtra("major",major)
            nextIntent.putExtra("tact",tact)
            startActivity(nextIntent)
        }
        binding.btnThrow.setOnClickListener{
            val nextIntent = Intent(this, MapsActivity::class.java)

            //맵에 메시지 띄우기 추가

            nextIntent.putExtra("name",name)
            nextIntent.putExtra("number",number)
            nextIntent.putExtra("major",major)
            nextIntent.putExtra("tact",tact)
            startActivity(nextIntent)



            val retrofit = Retrofit.Builder()
                .baseUrl("http://25.61.78.177:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create(Connect.POSTsendMessage::class.java)!!

            tex = binding.letter.text.toString();

            var letters=api.sendMessage(lat, lon, cat, cnt, saw, eti, tex, name,number,major,tact) /*eti 형식은 "2020.01.01 06:01:22"*/

            println(lat)
            println(lon)
            println(cat)
            println(cnt)
            println(saw)
            println(eti)
            println(tex)
            println(name)
            println(number)
            println(major)
            println(tact)

            letters.enqueue(object : Callback<Connect.MessageParams> {
                override fun onResponse(
                    call: Call<Connect.MessageParams>,
                    response: Response<Connect.MessageParams>
                ) {
                    Toast.makeText(this@SendMessageActivity, "전송되었습니다.", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<Connect.MessageParams>, t: Throwable) {
                    Toast.makeText(this@SendMessageActivity, "메세지가 전송되지 않았습니다.", Toast.LENGTH_LONG).show()
                    //////////////
                }
            })
        }
    }
}