package com.aman.bottomnavplayground

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.aman.bottomnavplayground.databinding.ActivityMainBinding
import com.aman.bottomnavplayground.fragments.LilyFragment
import com.aman.bottomnavplayground.fragments.RoseFragment
import com.aman.bottomnavplayground.fragments.TulipFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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

        val bottomNavView = binding?.bottomNavigation

        lifecycleScope.launch {
            getBottomNavData().collect { menuDataList ->
                // Clear any existing menu items
                bottomNavView?.menu?.clear()

                // Add menu items dynamically
                menuDataList.forEachIndexed { index, navMenuData ->
                    bottomNavView?.menu?.add(0, index, index, navMenuData.title)?.apply {
                        setIcon(navMenuData.image) // Set the icon
                        isCheckable = true       // Ensure the item is checkable
                    }
                }
            }


            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, RoseFragment())
                .commit()

            bottomNavView?.selectedItemId = 0

            binding?.bottomNavigation?.setOnItemSelectedListener { menu ->
                when (menu.itemId) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, RoseFragment())
                            .commit()
                        true
                    }

                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, TulipFragment())
                            .commit()
                        true
                    }

                    2 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, LilyFragment())
                            .commit()
                        true
                    }

                    else -> false

                }
            }

        }
    }

    fun getBottomNavData() = flow<List<NavMenuData>> {
        val list = listOf(
            NavMenuData(R.drawable.ic_rose, "Rose"),
            NavMenuData(R.drawable.ic_tulip, "Tulip"),
            NavMenuData(R.drawable.ic_lilly, "Lily"),
        )
        return@flow emit(list)
    }
}