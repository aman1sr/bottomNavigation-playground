package com.aman.bottomnavplayground

import android.content.res.ColorStateList
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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




        lifecycleScope.launch {
            getBottomNavData().collect { menuDataList ->
                setupDynamicBottomNavigation(menuDataList)
                //  Assign unique text colors to menu items
                assignTextColorsToMenu(txtCheckColor = R.color.primaryColor, txtUncheckedColor = R.color.black)

            }
        }
    }

    // Function to dynamically set BottomNavigationView menu
    fun setupDynamicBottomNavigation(menuDataList: List<NavMenuData>) {
        val bottomNavView = binding?.bottomNavigation

//        bottomNavView?.itemIconTintList = null
//        bottomNavView?.itemTextColor = null

        // Clear existing menu
        bottomNavView?.menu?.clear()

        // Create and set menu items dynamically
        menuDataList.forEachIndexed { index, navMenuData ->
            val menuItem = bottomNavView?.menu?.add(0, index, index, navMenuData.title)  // is this the way to add menu in bottom Nav, define the role of each parameter

            // Set custom icon selector
//            menuItem?.icon = createIconSelector(
//                navMenuData.activeIcon,
//                navMenuData.inactiveIcon
//            )

            menuItem?.icon = DrawableCompat.wrap(ContextCompat.getDrawable(this, navMenuData.activeIcon)!!)      // when u want to set single icon for a drawable ( for which u set the IconTint )


            // Ensure the item is checkable
            menuItem?.isCheckable = true
        }

        val iconTintList = createIconTintStateList(
            activeColor = ContextCompat.getColor(this, R.color.primaryColor),
            inactiveColor = ContextCompat.getColor(this, R.color.black)
        )
        bottomNavView?.itemIconTintList = iconTintList


        // Set default selected item
        bottomNavView?.selectedItemId = 0

        // Set item selection listener
        bottomNavView?.setOnItemSelectedListener { menu ->
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

    private fun createIconTintStateList(
        activeColor: Int,
        inactiveColor: Int
    ): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked), // Active state
            intArrayOf()                              // Inactive state
        )
        val colors = intArrayOf(
            activeColor,  // Active icon color
            inactiveColor // Inactive icon color
        )
        return ColorStateList(states, colors)
    }



    private fun createIconSelector(activeIcon: Int, inactiveIcon: Int): StateListDrawable {    //todo: why used  StateListDrawable when our menuItem?.icon  requires a Drawable icon
        val drawable = StateListDrawable()
        drawable.addState(intArrayOf(android.R.attr.state_checked), ContextCompat.getDrawable(this, activeIcon))
        drawable.addState(intArrayOf(), ContextCompat.getDrawable(this, inactiveIcon))
        return drawable
    }

    // Function to assign unique text colors for each menu item
    private fun assignTextColorsToMenu(txtCheckColor: Int, txtUncheckedColor: Int) {
        val bottomNavView = binding?.bottomNavigation

            val states = arrayOf(
                intArrayOf(android.R.attr.state_checked),  // Active state
                intArrayOf()                              // Inactive state
            )

            val colors = intArrayOf(
                ContextCompat.getColor(this, txtCheckColor),   // Active text color
                ContextCompat.getColor(this, txtUncheckedColor) // Inactive text color
            )

            // Create a ColorStateList for the current menu item
            val colorStateList = ColorStateList(states, colors)

            bottomNavView?.setItemTextColor(colorStateList)
        }



    fun getBottomNavData() = flow {
        val list = listOf(
            NavMenuData(
                activeIcon = R.drawable.ic_active,
                inactiveIcon = R.drawable.ic_rose_inactive,
                title = "Rose",
            ),
            NavMenuData(
                activeIcon = R.drawable.ic_lily_active,
                inactiveIcon = R.drawable.ic_lilly_inactive,
                title = "Lily",
            ),
            NavMenuData(
                activeIcon = R.drawable.ic_tulip_active,
                inactiveIcon = R.drawable.ic_tulip_inactive,
                title = "Tulip",
            ),

        )
        emit(list)
    }

}