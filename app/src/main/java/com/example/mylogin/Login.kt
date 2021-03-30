package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mylogin.R
import com.example.mylogin.Register
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
//import kotlinx.android.synthetic.main.activity_register.buttlogin
import java.lang.reflect.Member

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var auth: FirebaseAuth? = null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null){
            val it = Intent(this, Member::class.java)
            startActivity(it)
            finish()
        }


        buttlogin.setOnClickListener {
            val Email = editEmail.getText().toString().trim()
            val Pass = editPassword.getText().toString().trim()

            if (Email.isEmpty()){
                Toast.makeText(this, "กรุณากรอก email.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (Pass.isEmpty()){
                Toast.makeText(this, "การุณากรอก password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth!!.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener{task ->
                if(!task.isSuccessful){
                    if(Pass.length < 8 ){
                        editPassword.error="กรอกรหัสมากกว่า 8 ตัว"
                    }
                    else{
                        Toast.makeText(this, "Login ล้มเหลวเนื่องจาก: "+task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                    val it = Intent(this,Member::class.java)
                    startActivity(it)
                    finish()
                }
            }
        }

        btRegister.setOnClickListener {
            val it = Intent(this, Register::class.java)
            startActivity(it)
        }

        cmdBack.setOnClickListener {
            onBackPressed()
        }
    }
}