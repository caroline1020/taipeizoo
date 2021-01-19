package com.caroline.taipeizoo.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.viewmodel.LoadingState
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_error_panel.*

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainAdapter = AreaAdapter(AreaAdapter.OnClickListener { v, area ->
            viewModel.selectArea(area)
            v.findNavController().navigate(R.id.action_mainFragment_to_areaDetailFragment)
        })

        recyclerView.adapter = mainAdapter
        retryBtn.setOnClickListener {
            viewModel.loadIntroduction()
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadIntroduction()
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            errorPanel.visibility =
                if (it == LoadingState.ERROR) View.VISIBLE else View.GONE

            swipeRefreshLayout.isRefreshing = it == LoadingState.LOADING
        })

        viewModel.loadIntroduction()
        viewModel.data.observe(viewLifecycleOwner, Observer {
            mainAdapter.update(it)
        })

    }
}
