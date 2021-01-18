package com.caroline.taipeizoo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caroline.taipeizoo.area.AreaDetailFragment
import com.caroline.taipeizoo.main.MainFragment.MainFragment
import com.caroline.taipeizoo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, MainFragment())
        transaction.commit()

        viewModel.selectedArea.observe(this, Observer {
            if (it != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragment_container, AreaDetailFragment())
                transaction.addToBackStack("Area")
                transaction.commit()
            }

        })
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}