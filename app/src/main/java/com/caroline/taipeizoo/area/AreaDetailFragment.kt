package com.caroline.taipeizoo.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Area
import com.caroline.taipeizoo.viewmodel.AreaViewModel
import com.caroline.taipeizoo.viewmodel.LoadingState
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_area.*
import kotlinx.android.synthetic.main.view_error_panel.*

class AreaDetailFragment : Fragment() {
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

        var area = mainViewModel.selectedArea.value!!

        val plantAdapter = PlantAdapter(PlantAdapter.OnClickListener { v, plant ->
            mainViewModel.selectedPlant(plant)
            v.findNavController().navigate(R.id.action_areaDetailFragment_to_plantDetailFragment)
        })
        recyclerView.adapter = plantAdapter
        retryBtn.setOnClickListener { areaViewModel.loadFilteredPlants(area.E_Name) }


        areaViewModel.filteredPlant.observe(viewLifecycleOwner, Observer {
            plantAdapter.update(it)

        })

        areaViewModel.loadingState.observe(viewLifecycleOwner, Observer {
            progressBar.visibility =
                if (it == LoadingState.LOADING) View.VISIBLE else View.GONE
            errorPanel.visibility =
                if (it == LoadingState.ERROR) View.VISIBLE else View.GONE
        })
        updateContent(area)
    }

    private fun updateContent(area: Area) {

        (activity as AppCompatActivity).supportActionBar?.title = area.E_Name
        Glide.with(this).load(area.E_Pic_URL).into(areaIcon)
        areaDescText.text = area.E_Info
        areaViewModel.loadFilteredPlants(area.E_Name)
    }

}
