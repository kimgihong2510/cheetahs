package com.example.letter

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.letter.databinding.LoginBinding

class LoginActivity : AppCompatActivity()
{
    private lateinit var binding: LoginBinding//LoginActivity & login.xml를 bind
    var name : String? = ""
    var number : String? = ""
    var major : String? = ""
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        val view = binding.root
        binding.login.setEnabled(false)//이름, 학번, 학과를 다 입력하기 전에 버튼 못누르게 함
        setContentView(view)//화면 출력

        (binding.NAME).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                name=p0.toString()
                if((name.equals("")||number.equals("")||major.equals(""))==false)//타이핑시 마지막으로 작동
                    binding.login.setEnabled(true)
                else
                    binding.login.setEnabled(false)

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        (binding.NUMBER).addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                number=p0.toString()
                if((name.equals("")||number.equals("")||major.equals(""))==false)
                    binding.login.setEnabled(true)
                else
                    binding.login.setEnabled(false)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        (binding.MAJOR).addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                major=p0.toString()
                if((name.equals("")||number.equals("")||major.equals(""))==false)
                    binding.login.setEnabled(true)
                else
                    binding.login.setEnabled(false)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        binding.login.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))//버튼이 눌리면 로그인 이후 화면으로 이동
        }
    }

}