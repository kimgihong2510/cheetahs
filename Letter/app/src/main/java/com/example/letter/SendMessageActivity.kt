package com.example.letter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.example.letter.databinding.ActivitySendMessageBinding
import java.time.chrono.JapaneseEra.values


class SendMessageActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySendMessageBinding

    @SuppressLint("ResourceAsColor")
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

        //쪽찌 종류
        val purpose= arrayOf("친목", "멘토", "멘티", "취준")
        val myAdapter=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, purpose)
        binding.SetColor.adapter=myAdapter

        //쪽찌마다 색깔바뀌게 만들기
        binding.SetColor.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(id.toInt()==0){
                    binding.letter.setBackgroundResource(R.color.purpose1)}
                if(id.toInt()==1){
                    binding.letter.setBackgroundResource(R.color.purpose2)}
                if(id.toInt()==2){
                    binding.letter.setBackgroundResource(R.color.purpose3)}
                if(id.toInt()==3){
                    binding.letter.setBackgroundResource(R.color.purpose4)}
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        //프로필 사진
        binding.ProfilePicture.setImageResource(R.drawable.ic_launcher_background)
    }
}