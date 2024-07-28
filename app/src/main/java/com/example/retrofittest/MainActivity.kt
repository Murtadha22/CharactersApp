package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.retrofittest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize toolbar options
        toolBarOptions()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_bottom_navigation, fragment)
        fragmentTransaction.commit()
    }

    private fun toolBarOptions() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Toast.makeText(this, "Home!", Toast.LENGTH_SHORT).show()
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.favorite -> {
                    Toast.makeText(this, "Favorite!", Toast.LENGTH_SHORT).show()
                    replaceFragment(FavoriteFragment())
                    true
                }

                R.id.settings -> {
                    Toast.makeText(this, "Setting!", Toast.LENGTH_SHORT).show()
                    replaceFragment(SettingsFragment())
                    true
                }

                else -> false
            }
        }
    }
}
