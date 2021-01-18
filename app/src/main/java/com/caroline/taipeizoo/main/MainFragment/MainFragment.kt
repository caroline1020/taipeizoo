package com.caroline.taipeizoo.main.MainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.main.AreaAdapter
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainAdapter = AreaAdapter(AreaAdapter.OnClickListener { it ->
            viewModel.selectArea(it)

        })
        recyclerView.adapter = mainAdapter
        viewModel.loading.observe(this, Observer {
            progressBar.visibility =
                if (it == MainViewModel.LoadingState.LOADING) View.VISIBLE else View.GONE
            errorPanel.visibility =
                if (it == MainViewModel.LoadingState.ERROR) View.VISIBLE else View.GONE
        })
        viewModel.loadIntroduction()
        viewModel.data.observe(this, Observer { it ->
            mainAdapter.update(it)
        })

    }
}
