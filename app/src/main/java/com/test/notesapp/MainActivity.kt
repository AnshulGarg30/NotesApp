package com.test.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.notesapp.Fragments.Login_Fragment
import com.test.notesapp.Fragments.Notes_Fragment
import com.test.notesapp.Utils.UserSharedPrefrences

class MainActivity : AppCompatActivity() {
    lateinit var pref: UserSharedPrefrences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref= UserSharedPrefrences.getInstance(this)


        if(pref.getlogin()!!)
            supportFragmentManager.beginTransaction().replace(R.id.MainFrame,Notes_Fragment()).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.MainFrame,Login_Fragment()).commit()
    }
}