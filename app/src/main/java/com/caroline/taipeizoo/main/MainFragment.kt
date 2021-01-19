package com.caroline.taipeizoo.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.viewmodel.LoadingState
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val mainAdapter = AreaAdapter(AreaAdapter.OnClickListener { v, area ->
            viewModel.selectArea(area)
            v.findNavController().navigate(R.id.action_mainFragment_to_areaDetailFragment)
        })

        view.recyclerView.adapter = mainAdapter

        view.swipeRefreshLayout.setOnRefreshListener {
            viewModel.reloadArea()
        }

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it == LoadingState.LOADING
            if (it == LoadingState.ERROR) {
                Toast.makeText(context, R.string.api_error_msg, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loadIntroduction()
        viewModel.data.observe(viewLifecycleOwner, Observer {
            mainAdapter.update(it)
        })

        return view
    }

}
