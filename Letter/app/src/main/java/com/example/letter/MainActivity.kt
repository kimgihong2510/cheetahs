package com.example.letter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letter.databinding.MainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class MainActivity : AppCompatActivity()
{
    lateinit var name:String//로그인 화면에서 가져옴
    lateinit var number:String//로그인 화면에서 가져옴
    lateinit var major:String//로그인 화면에서 가져옴
    lateinit var tact:String//로그인 화면에서 가져옴
    private lateinit var binding: MainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        binding = MainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
        this.name = intent.getStringExtra("name").toString()
        this.major = intent.getStringExtra("major").toString()
        this.number = intent.getStringExtra("number").toString()
        this.tact = intent.getStringExtra("tact").toString()

        binding.Test.setOnClickListener{
            val ToProfile = Intent(this,ProfileActivity::class.java)
            ToProfile.putExtra("name",name)//프로필로 정보 전달
            ToProfile.putExtra("number",number)//프로필로 정보 전달
            ToProfile.putExtra("major",major)//프로필로 정보 전달
            ToProfile.putExtra("tact",tact)//프로필로 정보 전달
            startActivity(ToProfile)
        }
    }
}