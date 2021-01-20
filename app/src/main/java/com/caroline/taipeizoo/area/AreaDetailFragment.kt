package com.caroline.taipeizoo.area

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.NetworkArea
import com.caroline.taipeizoo.viewmodel.AreaViewModel
import com.caroline.taipeizoo.viewmodel.LoadingState
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_area.*
import kotlinx.android.synthetic.main.fragment_area.view.*

class AreaDetailFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val areaViewModel: AreaViewModel by lazy {
        ViewModelProvider(this).get(AreaViewModel::class.java)
    }


    private lateinit var area: NetworkArea
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_area, container, false)
        area = requireArguments().get("Area") as NetworkArea
        (activity as AppCompatActivity).supportActionBar?.title = area.E_Name
        val plantAdapter = AreaDetailAdapter(AreaDetailAdapter.OnClickListener { v, plant ->
            mainViewModel.selectedPlant(plant)
            v.findNavController().navigate(R.id.action_areaDetailFragment_to_plantDetailFragment)
        })
        view.recyclerView.adapter = plantAdapter
        plantAdapter.update(area)

        view.swipeRefreshLayout.setOnRefreshListener {
            areaViewModel.reloadFilteredPlants(area.E_Name)
        }

        areaViewModel.filteredPlant.observe(viewLifecycleOwner, Observer {
            plantAdapter.update(it)
        })

        areaViewModel.loadingState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it == LoadingState.LOADING
            if (it == LoadingState.ERROR) {
                Toast.makeText(context, R.string.api_error_msg, Toast.LENGTH_SHORT).show()
            }
        })
        areaViewModel.loadFilteredPlants(area.E_Name)


        return view
    }


}

fun NetworkArea.getHoliday(context: Context): String {
    if (E_Memo.isEmpty()) {
        return context.getString(R.string.no_holiday_info)
    }
    return E_Memo
}
