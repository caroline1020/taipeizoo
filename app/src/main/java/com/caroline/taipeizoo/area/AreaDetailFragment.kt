package com.caroline.taipeizoo.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.bumptech.glide.Glide
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Area
import com.caroline.taipeizoo.viewmodel.AreaViewModel
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_area.*

class AreaDetailFragment : Fragment() {
    private lateinit var area: Area
    private val mainViewModel: MainViewModel by activityViewModels()
    private val areaViewModel: AreaViewModel by lazy {
        ViewModelProvider(this).get(AreaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_area, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plantAdapter = PlantAdapter(PlantAdapter.OnClickListener {view,plant->
            mainViewModel.selectedPlant(plant)
            view.findNavController().navigate(R.id.action_areaDetailFragment_to_plantDetailFragment)
        })
        recyclerView.adapter = plantAdapter
        areaViewModel.filteredPlant.observe(this, Observer {
            plantAdapter.update(it)

        })
        val selectedArea = mainViewModel.selectedArea.value
        selectedArea?.let {
            area=selectedArea
            mainViewModel.selectArea(null)
        }

        area?.let {
            activity?.title = area.E_Name
            Glide.with(this).load(area.E_Pic_URL).into(areaIcon)
            areaDescText.text = area.E_Info
            areaViewModel.filterPlant(area.E_Name)
        }


    }

}
