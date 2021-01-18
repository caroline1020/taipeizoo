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
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Info
import kotlinx.android.synthetic.main.view_info_view_holder.view.*

class MainAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<MainAdapter.InfoViewHolder>() {

    private val data = ArrayList<Info>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_info_view_holder, parent, false)
        return InfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainAdapter.InfoViewHolder, position: Int) {
        val info: Info = data[position]
        holder.bind(info)
        holder.itemView.setOnClickListener { onClickListener.onClick(info) }
    }

    fun update(newData: List<Info>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }


    inner class InfoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val titleText = itemView.titleText
        val descText = itemView.descText

        fun bind(item: Info) {
            titleText.text = item.E_Name
            descText.text=item.E_Info
        }


    }

    class OnClickListener(val clickListener: (info: Info) -> Unit) {
        fun onClick(info: Info) = clickListener(info)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
