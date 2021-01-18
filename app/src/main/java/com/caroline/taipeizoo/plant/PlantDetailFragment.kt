package com.caroline.taipeizoo.plant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Plant
import com.caroline.taipeizoo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_plant.*

class PlantDetailFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plant = mainViewModel.selectedPlant.value
        plant?.let {
            updateContent(plant)

        }

    }

    private fun updateContent(plant: Plant) {
        activity?.title = plant.F_Name_Ch
        Glide.with(this).load(plant.F_Pic01_URL).into(plantIcon)
        chineseNameText.text = plant.F_Name_Ch
        engNameText.text = plant.F_Name_En
        akaText.text = plant.F_AlsoKnown
        briefText.text = plant.F_Brief
        recognizeText.text = plant.F_Feature
        functionText.text = plant.F_Function_Application
        lastUpdateText.text =
            String.format(context!!.getString(R.string.last_update), plant.F_Update)


    }


}
