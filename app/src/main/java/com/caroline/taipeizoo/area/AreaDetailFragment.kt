package com.caroline.taipeizoo.area

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Area
import com.caroline.taipeizoo.plant.PlantDetailFragment
import com.caroline.taipeizoo.viewmodel.AreaViewModel
import com.caroline.taipeizoo.viewmodel.LoadingState
import kotlinx.android.synthetic.main.fragment_area.*
import kotlinx.android.synthetic.main.fragment_area.view.*


class AreaDetailFragment : Fragment() {
    private val areaViewModel: AreaViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, AreaViewModel.Factory(activity.application))
            .get(AreaViewModel::class.java)
    }


    private lateinit var area: Area
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_area, container, false)
        area = requireArguments().get(Companion.KEY_AREA) as Area

        (activity as AppCompatActivity).supportActionBar?.title = area.E_Name

        val plantAdapter = AreaDetailAdapter(AreaDetailAdapter.OnClickListener { v, plant ->
            val bundle = Bundle()
            bundle.putSerializable(PlantDetailFragment.KEY_PLANT, plant)
            v.findNavController()
                .navigate(R.id.action_areaDetailFragment_to_plantDetailFragment, bundle)
        })
        view.recyclerView.adapter = plantAdapter
        plantAdapter.update(area)

        view.swipeRefreshLayout.setOnRefreshListener {
            areaViewModel.refreshPlants()
        }

        areaViewModel.plantList.observe(viewLifecycleOwner, Observer { it ->
            if (it.isEmpty()) {
                areaViewModel.refreshPlants()
            } else {
                plantAdapter.update(it.filter { plant -> plant.F_Location.contains(area.E_Name) })
            }
        })

        areaViewModel.loadingState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it == LoadingState.LOADING
            if (it == LoadingState.ERROR) {
                Toast.makeText(context, R.string.api_error_msg, Toast.LENGTH_SHORT).show()
            }
        })


        return view
    }

    companion object {
        public const val KEY_AREA = "Area"
    }


}

fun Area.getHoliday(context: Context): String {
    if (E_Memo.isEmpty()) {
        return context.getString(R.string.no_holiday_info)
    }
    return E_Memo
}
