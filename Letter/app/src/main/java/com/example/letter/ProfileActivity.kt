package com.example.letter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letter.databinding.ProfileBinding

class ProfileActivity : AppCompatActivity()
{
    lateinit var name :String
    lateinit var number :String
    lateinit var major :String
    lateinit var tact :String
    lateinit var profile_binding :ProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        profile_binding = ProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        name = intent.getStringExtra("name").toString()
        number = intent.getStringExtra("number").toString()
        major = intent.getStringExtra("name").toString()
        tact = intent.getStringExtra("tact").toString()


        val view = profile_binding.root

        profile_binding.nameInProfile.text = name
        profile_binding.numberInProfile.text = number
        profile_binding.majorInProfile.text = major
        profile_binding.tactInProfile.text=tact

        setContentView(view)

    }
}