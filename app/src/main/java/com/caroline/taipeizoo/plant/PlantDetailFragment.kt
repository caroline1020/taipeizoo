package com.caroline.taipeizoo.plant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Plant
import kotlinx.android.synthetic.main.fragment_plant.*

class PlantDetailFragment : Fragment() {
    private lateinit var plant: Plant
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_plant, container, false)

        val requireArguments = requireArguments()
        plant = requireArguments.get(KEY_PLANT) as Plant
        (activity as AppCompatActivity).supportActionBar?.title = plant.F_Name_Ch
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateContent()
    }

    private fun updateContent() {
        Glide.with(this).load(plant.F_Pic01_URL).error(R.drawable.image_not_found).into(plantIcon)
        chineseNameText.text = plant.F_Name_Ch
        engNameText.text = plant.F_Name_En
        akaText.text = plant.F_AlsoKnown
        briefText.text = plant.F_Brief
        recognizeText.text = plant.F_Feature
        functionText.text = plant.F_Function_Application
        lastUpdateText.text =
            String.format(requireContext().getString(R.string.last_update), plant.F_Update)

    }

    companion object {
        const val KEY_PLANT = "Plant"
    }


}
