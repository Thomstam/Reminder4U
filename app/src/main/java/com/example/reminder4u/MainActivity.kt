package com.example.reminder4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNewForm()
    }

    private fun setNewForm(){
        val newFormButton = findViewById<FloatingActionButton>(R.id.activity_main_new_form_button)
        newFormButton.setOnClickListener {
            val intent = Intent(this, FormSetup::class.java)
            startActivity(intent)
        }
    }



}