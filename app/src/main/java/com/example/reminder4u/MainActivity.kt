package com.example.reminder4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawer : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDrawer()
    }

    fun setDrawer(){
        drawer = findViewById(R.id.activity_main_drawer)
        val tollBarMain : androidx.appcompat.widget.Toolbar = findViewById(R.id.activity_main_toolbar)
        toggle = ActionBarDrawerToggle(this, drawer ,tollBarMain, R.string.drawer_open, R.string.drawer_close)
        toggle.isDrawerIndicatorEnabled = true
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }


}