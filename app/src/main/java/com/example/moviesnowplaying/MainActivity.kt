package com.example.moviesnowplaying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesnowplaying.ui.main.MainFragment
import com.example.moviesnowplaying.utils.newInstance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {

            val mainFragment = supportFragmentManager.fragmentFactory.newInstance<MainFragment>()

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()
        }
    }
}