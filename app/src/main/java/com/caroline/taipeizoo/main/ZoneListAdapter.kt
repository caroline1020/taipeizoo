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

package com.caroline.taipeizoo.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Zone
import com.caroline.taipeizoo.zoneDetail.getHoliday
import kotlinx.android.synthetic.main.item_view_holder.view.*


class ZoneListAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<ZoneListAdapter.InfoViewHolder>() {

    private val data = ArrayList<Zone>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZoneListAdapter.InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_holder, parent, false)
        return InfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ZoneListAdapter.InfoViewHolder, position: Int) {
        val zone: Zone = data[position]
        holder.bind(zone)
        holder.itemView.setOnClickListener { onClickListener.onClick(holder.itemView, zone) }
    }

    fun update(newData: List<Zone>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }


    inner class InfoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val titleText = itemView.titleText
        private val holidayText = itemView.holidayText
        private val descText = itemView.descText
        private val icon = itemView.icon

        fun bind(item: Zone) {
            titleText.text = item.E_Name
            descText.text = item.E_Info
            holidayText.text = item.getHoliday(itemView.context)
            Glide.with(itemView.context)
                .load(item.E_Pic_URL)
                .centerCrop()
                .error(R.drawable.image_not_found)
                .placeholder(R.drawable.image_loading)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(icon)
        }


    }

    class OnClickListener(val clickListener: (view: View, area: Zone) -> Unit) {
        fun onClick(view: View, area: Zone) = clickListener(view, area)
    }


    override fun getItemCount(): Int {
        return data.size
    }
}
