package com.caroline.taipeizoo.zoneDetail

import com.caroline.taipeizoo.model.Zone
import com.caroline.taipeizoo.model.Plant

/**
 * Created by nini on 2021/1/19.
 */
sealed class DataItem {
    data class PlantItem(val plant: Plant) : DataItem() {
        override val id = plant._id.toLong()
    }
    data class HeaderItem(val area: Zone) : DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}