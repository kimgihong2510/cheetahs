package com.example.letter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letter.databinding.ActivitySendMessageBinding
import com.example.letter.databinding.ActivityShowProfileBinding

class ShowProfileActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityShowProfileBinding
    private lateinit var number:String
    private lateinit var major:String
    private lateinit var name:String
    private lateinit var tact:String
    override fun onCreate(savedInstanceState: Bundle?) {

        this.name = intent.getStringExtra("name").toString()
        this.number = intent.getStringExtra("number").toString()
        this.major = intent.getStringExtra("major").toString()
        this.tact = intent.getStringExtra("tact").toString()

        super.onCreate(savedInstanceState)

        var toprint=number.substring(2,4)+"_"+name.substring(0,3)+"_"+major

        binding = ActivityShowProfileBinding.inflate(layoutInflater)
        val view=binding.root
        binding.SProfile.text=toprint
        binding.Cont.text=tact

        setContentView(view)

        binding.btnBack.setOnClickListener{
            val ToShowMessage= Intent(this,ShowMessageActivity::class.java)
            ToShowMessage.putExtra("name",name)
            ToShowMessage.putExtra("number",number)
            ToShowMessage.putExtra("major",major)
            ToShowMessage.putExtra("tact",tact)
            startActivity(ToShowMessage)
        };
    }
}