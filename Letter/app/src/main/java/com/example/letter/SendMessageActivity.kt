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
import java.text.SimpleDateFormat
import java.util.*


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
        var hour:Int = 0
        var min:Int = 0

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
        val arrayPicker= arrayOf("5명", "6명", "7명", "8명", "9명", "10명", "1명", " 2명", "3명", "4명", "제한없음")
        binding.PersonPicker.displayedValues= arrayPicker

        binding.HourPicker.minValue=0
        binding.HourPicker.maxValue=30

        binding.MinPicker.minValue=0
        binding.MinPicker.maxValue=59
        binding.MinPicker.value=30

        binding.PersonPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            when{
                i2 == 0 -> cnt = 5;
                i2 == 1 -> cnt = 6;
                i2 == 2 -> cnt = 7;
                i2 == 3 -> cnt = 8;
                i2 == 4 -> cnt = 9;
                i2 == 5 -> cnt = 10;
                i2 == 6 -> cnt = 1;
                i2 == 7 -> cnt = 2;
                i2 == 8 -> cnt = 3;
                i2 == 9 -> cnt = 4;
                i2 == 10 -> cnt = 100000;
            }
        }

        binding.HourPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            hour =i2
        }
        binding.MinPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            min =i2
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

            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale("ko","KR"))
            val calendar = Calendar.getInstance()
            calendar.setTime(date)

            calendar.add(Calendar.HOUR,hour)
            calendar.add(Calendar.MINUTE,min)

            val eti :String = dateFormat.format(calendar.time)//서버에 넘어갈 시간 변수 string


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