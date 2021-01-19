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
import com.bumptech.glide.request.RequestOptions
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Area
import kotlinx.android.synthetic.main.view_info_view_holder.view.*

class AreaAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<AreaAdapter.InfoViewHolder>() {

    private val data = ArrayList<Area>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaAdapter.InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_info_view_holder, parent, false)
        return InfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AreaAdapter.InfoViewHolder, position: Int) {
        val area: Area = data[position]
        holder.bind(area)
        holder.itemView.setOnClickListener { onClickListener.onClick(holder.itemView, area) }
    }

    fun update(newData: List<Area>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    val options = RequestOptions()
        .skipMemoryCache(true)
        .placeholder(R.drawable.ic_launcher_foreground)

    inner class InfoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val titleText = itemView.titleText
        private val descText = itemView.descText
        private val icon = itemView.icon

        fun bind(item: Area) {
            titleText.text = item.E_Name
            descText.text = item.E_Info

            Glide.with(itemView.context)
                .load(item.E_Pic_URL)
                .centerCrop()
                .apply(options.override(200, 200))
                .into(icon)
        }


    }

    class OnClickListener(val clickListener: (view: View, area: Area) -> Unit) {
        fun onClick(view: View, area: Area) = clickListener(view, area)
    }


    override fun getItemCount(): Int {
        return data.size
    }
}
