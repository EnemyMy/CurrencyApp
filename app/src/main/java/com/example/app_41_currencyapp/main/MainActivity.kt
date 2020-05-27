package com.example.app_41_currencyapp.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import com.example.app_41_currencyapp.R
import com.example.app_41_currencyapp.addcurrency.AddCurrencyFragment
import com.example.app_41_currencyapp.application.CurrencyApplication
import com.example.app_41_currencyapp.databinding.ActivityMainBinding
import com.example.app_41_currencyapp.util.navigateToFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainActivityToolbar)
        if (savedInstanceState == null)
            supportFragmentManager.navigateToFragment(MainFragment::class.java, R.id.main_activity_fragment_container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_fragment_menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.main_fragment_menu_item_add_currency) {
            supportFragmentManager.navigateToFragment(AddCurrencyFragment::class.java, R.id.main_activity_fragment_container, true, "add_movie_fragment")
        }
        return super.onOptionsItemSelected(item)
    }
}