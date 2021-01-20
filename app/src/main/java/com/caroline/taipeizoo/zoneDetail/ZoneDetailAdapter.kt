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

package com.caroline.taipeizoo.zoneDetail

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.caroline.taipeizoo.R
import com.caroline.taipeizoo.model.Plant
import com.caroline.taipeizoo.model.Zone
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_view_holder.view.*
import kotlinx.android.synthetic.main.item_view_holder.view.holidayText


class AreaDetailAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(AreaDetailDiffCallback()) {

    private val data = ArrayList<Plant>()
    private lateinit var area: Zone

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == ITEM_VIEW_TYPE_HEADER) {
            HeaderViewHolder.from(parent)
        } else {
            PlantViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val headerItem = getItem(position) as DataItem.HeaderItem
                holder.bind(headerItem.area, itemCount)
            }
            is PlantViewHolder -> {
                val plantItem = getItem(position) as DataItem.PlantItem
                holder.bind(plantItem.plant)
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(
                        holder.itemView,
                        plantItem.plant
                    )
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) is DataItem.HeaderItem) {
            return ITEM_VIEW_TYPE_HEADER
        }
        return ITEM_VIEW_TYPE_ITEM
    }

    override fun getItem(position: Int): DataItem {
        if (position == 0) {
            return DataItem.HeaderItem(area)
        }
        return DataItem.PlantItem(data[position - 1])
    }


    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val areaIcon = itemView.areaIcon
        private val areaDescText = itemView.areaDescText
        private val holidayText = itemView.holidayText
        private val zoneText = itemView.zoneText
        private val openUrlBtn = itemView.openUrlBtn
        private val plantListTitle = itemView.plantListTitle

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_header, parent, false)
                return HeaderViewHolder(view)
            }
        }

        fun bind(area: Zone, itemCount: Int) {
            Glide.with(itemView.context).load(area.E_Pic_URL).error(R.drawable.image_not_found)
                .into(areaIcon)
            areaDescText.text = area.E_Info
            holidayText.text = area.getHoliday(itemView.context)
            zoneText.text = area.E_Category
            openUrlBtn.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(area.E_URL))
                itemView.context.startActivity(i)
            }
            if (itemCount > 1) {
                plantListTitle.visibility = View.VISIBLE
            } else {
                plantListTitle.visibility = View.GONE
            }
        }
    }


    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            fun from(parent: ViewGroup): PlantViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_view_holder, parent, false)
                return PlantViewHolder(view)
            }
        }

        private val titleText = itemView.titleText
        private val descText = itemView.descText
        private val icon = itemView.icon

        fun bind(item: Plant) {
            titleText.text = item.F_Name_Ch
            descText.text = item.F_AlsoKnown
            icon.clipToOutline = true
            Glide.with(itemView.context)
                .load(item.F_Pic01_URL)
                .centerCrop()
                .error(R.drawable.image_not_found)
                .placeholder(R.drawable.image_loading)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(icon)
        }


    }

    class OnClickListener(val clickListener: (view: View, plant: Plant) -> Unit) {
        fun onClick(view: View, area: Plant) = clickListener(view, area)
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    fun update(area: Zone) {
        this.area = area
        notifyItemInserted(0)
    }

    fun update(newData: List<Plant>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }

}

class AreaDetailDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}
