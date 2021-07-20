package com.example.letter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letter.databinding.ActivitySendMessageBinding

class SendMessageActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySendMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)

        binding= ActivitySendMessageBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //인원수, 시간, 분 롤
        binding.PersonPicker.minValue=0
        binding.PersonPicker.maxValue=10
        val ArrayPicker= arrayOf("제한 없음", "1명", "2명", "3명", "4명", "5명", "6명", " 7명", "8명", "9명", "10명")
        binding.PersonPicker.displayedValues= ArrayPicker

        binding.HourPicker.minValue=0
        binding.HourPicker.maxValue=30

        binding.MinPicker.minValue=0
        binding.MinPicker.maxValue=59

    }

}