package com.caroline.taipeizoo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.caroline.taipeizoo.model.Area
import com.caroline.taipeizoo.model.Plant

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

fun List<DatabaseArea>.asAreaDomainModel(): List<Area> {
    return map {
        Area(
            it.category,
            it.geo,
            it.info,
            it.memo,
            it.name,
            it.pic_url,
            it.url,
            it.no,
            it._id
        )
    }
}

@Entity
data class DatabasePlant constructor(
    @PrimaryKey
    val aka: String,
    val brief: String,
    val code: String,
    val family: String,
    val feature: String,
    val function: String,
    val genus: String,
    val geo: String,
    val keywords: String,
    val location: String,
    val name_en: String,
    val pic_url: String,
    val summary: String,
    val update: String,
    val _id: Int,
    val name_ch: String

)

fun List<DatabasePlant>.asDomainModel(): List<Plant> {
    return map {
        Plant(
            it.aka,
            it.brief,
            it.code,
            it.family,
            it.feature,
            it.function,
            it.genus,
            it.geo,
            it.keywords,
            it.location,
            it.name_en,
            it.pic_url,
            it.summary,
            it.update,
            it._id,
            it.name_ch
        )
    }
}