package com.aman.bottomnavplayground

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aman.bottomnavplayground.databinding.ActivityMainBinding
import com.aman.bottomnavplayground.fragments.LilyFragment
import com.aman.bottomnavplayground.fragments.RoseFragment
import com.aman.bottomnavplayground.fragments.TulipFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
/*
*
* Navigation wrt Jetpack Navigation Lib
* dynamic menu items
*
* */
class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding?.bottomNavigation?.setOnItemSelectedListener {menu ->
            when (menu.itemId) {
                R.id.item_rose -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, RoseFragment())
                        .commit()
                     true
                }

                R.id.item_tulip -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, TulipFragment())
                        .commit()
                     true
                }

                R.id.item_lilly -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, LilyFragment())
                        .commit()
                     true
                }

                else ->  false

            }
        }
        binding?.bottomNavigation?.selectedItemId = R.id.item_rose

    }
}


