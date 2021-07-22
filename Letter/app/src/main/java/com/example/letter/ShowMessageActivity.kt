package com.example.letter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letter.databinding.ActivitySendMessageBinding
import com.example.letter.databinding.ActivityShowMessageBinding

class ShowMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowMessageBinding

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
    }


}