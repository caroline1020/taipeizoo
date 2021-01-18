package com.caroline.taipeizoo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caroline.taipeizoo.main.MainAdapter
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainAdapter = MainAdapter(MainAdapter.OnClickListener { })
        recyclerView.adapter = mainAdapter
        viewModel.loading.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.loadIntroduction()
        viewModel.data.observe(this, Observer { it ->
            mainAdapter.update(it)
        })


    }


}