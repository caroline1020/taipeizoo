package com.caroline.taipeizoo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.caroline.taipeizoo.model.NetworkArea

/**
 * Created by nini on 2021/1/20.
 */
@Entity
data class DatabaseArea constructor(
    @PrimaryKey
    val _id: Int,
    val name: String,
    val geo: String,
    val category: String,
    val info: String,
    val pic_url: String,
    val url: String,
    val no: String,
    val memo: String
)

fun List<DatabaseArea>.asDomainModel(): List<NetworkArea> {
    return map {
        NetworkArea(it.category, it.geo, it.info, it.memo, it.name, it.pic_url, it.url, it.no, it._id)
    }
}