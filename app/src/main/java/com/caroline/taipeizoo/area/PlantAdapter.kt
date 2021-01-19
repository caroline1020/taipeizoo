/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.caroline.taipeizoo.area

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Plant
import kotlinx.android.synthetic.main.view_view_holder.view.*

class PlantAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<PlantAdapter.InfoViewHolder>() {

    private val data = ArrayList<Plant>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantAdapter.InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_view_holder, parent, false)
        return InfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlantAdapter.InfoViewHolder, position: Int) {
        val area: Plant = data[position]
        holder.bind(area)
        holder.itemView.setOnClickListener { onClickListener.onClick(holder.itemView, area) }
    }

    fun update(newData: List<Plant>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    val options = RequestOptions()
        .skipMemoryCache(true).error(R.drawable.image_not_found)
        .placeholder(R.drawable.image_loading)

    inner class InfoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val titleText = itemView.titleText
        private val descText = itemView.descText
        private val icon = itemView.icon

        fun bind(item: Plant) {
            titleText.text = item.F_Name_Ch
            descText.text = item.F_AlsoKnown

            Glide.with(itemView.context)
                .load(item.F_Pic01_URL)
                .centerCrop()
                .apply(options.override(200, 200))
                .into(icon)
        }


    }

    class OnClickListener(val clickListener: (view: View, plant: Plant) -> Unit) {
        fun onClick(view: View, area: Plant) = clickListener(view, area)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
