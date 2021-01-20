package com.caroline.taipeizoo.zoneDetail

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
import com.caroline.taipeizoo.model.Zone
import com.caroline.taipeizoo.plant.PlantDetailFragment
import com.caroline.taipeizoo.viewmodel.ZoneDetailViewModel
import com.caroline.taipeizoo.viewmodel.LoadingState
import kotlinx.android.synthetic.main.fragment_zone_detail.*
import kotlinx.android.synthetic.main.fragment_zone_detail.view.*


class ZoneDetailFragment : Fragment() {
    private val zoneViewModel: ZoneDetailViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, ZoneDetailViewModel.Factory(activity.application))
            .get(ZoneDetailViewModel::class.java)
    }


    private lateinit var area: Zone
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_zone_detail, container, false)
        area = requireArguments().get(KEY_AREA) as Zone

        (activity as AppCompatActivity).supportActionBar?.title = area.E_Name

        val plantAdapter = AreaDetailAdapter(AreaDetailAdapter.OnClickListener { v, plant ->
            val bundle = Bundle()
            bundle.putSerializable(PlantDetailFragment.KEY_PLANT, plant)
            v.findNavController()
                .navigate(R.id.action_zoneDetailFragment_to_plantDetailFragment, bundle)
        })
        view.recyclerView.adapter = plantAdapter
        plantAdapter.update(area)

        view.swipeRefreshLayout.setOnRefreshListener {
            zoneViewModel.refreshPlants()
        }

        zoneViewModel.plantList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                zoneViewModel.refreshPlants()
            } else {
                plantAdapter.update(it.filter { plant -> plant.F_Location.contains(area.E_Name) })
            }
        })

        zoneViewModel.loadingState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it == LoadingState.LOADING
            if (it == LoadingState.ERROR) {
                Toast.makeText(context, R.string.api_error_msg, Toast.LENGTH_SHORT).show()
            }
        })


        return view
    }

    companion object {
        const val KEY_AREA = "Area"
    }


}

fun Zone.getHoliday(context: Context): String {
    if (E_Memo.isEmpty()) {
        return context.getString(R.string.no_holiday_info)
    }
    return E_Memo
}
