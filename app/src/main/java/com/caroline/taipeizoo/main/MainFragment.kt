package com.caroline.taipeizoo.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.area.AreaDetailFragment
import com.caroline.taipeizoo.viewmodel.LoadingState
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val mainAdapter = AreaAdapter(AreaAdapter.OnClickListener { v, area ->
            val bundle = Bundle()
            bundle.putSerializable(AreaDetailFragment.KEY_AREA, area)
            v.findNavController().navigate(R.id.action_mainFragment_to_areaDetailFragment, bundle)
        })

        view.recyclerView.adapter = mainAdapter

        view.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshAreas()
        }

        registerObservers(mainAdapter)
        return view
    }

    private fun registerObservers(mainAdapter: AreaAdapter) {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it == LoadingState.LOADING
            if (it == LoadingState.ERROR) {
                Toast.makeText(context, R.string.api_error_msg, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.areaList.observe(viewLifecycleOwner, Observer {
            mainAdapter.update(it)
            if (it.isEmpty()) {
                viewModel.loadAreas()
            }
        })
    }

}
