package com.caroline.taipeizoo.network

import com.caroline.taipeizoo.database.DatabaseZone
import com.caroline.taipeizoo.database.DatabasePlant
import com.caroline.taipeizoo.model.ZoneResponse
import com.caroline.taipeizoo.model.PlantResponse
import com.squareup.moshi.JsonClass

/**
 * Created by nini on 2021/1/20.
 */


@JsonClass(generateAdapter = true)
data class NetworkAreaContainer(val areaResponse: ZoneResponse)

fun NetworkAreaContainer.asDatabaseModel(): List<DatabaseZone> {
    val areaList = areaResponse.result.results
    return areaList.map {
        DatabaseZone(
            it._id,
            it.E_Name,
            it.E_Geo,
            it.E_Category,
            it.E_Info,
            it.E_Pic_URL,
            it.E_URL,
            it.E_no,
            it.E_Memo
        )
    }
}

@JsonClass(generateAdapter = true)
data class NetworkPlantContainer(val plantResponse: PlantResponse)

fun NetworkPlantContainer.asDatabaseModel(): List<DatabasePlant> {
    val areaList = plantResponse.result.results
    return areaList.map {
        DatabasePlant(
            it.F_Name_Ch,
            it.F_AlsoKnown,
            it.F_Brief,
            it.F_Code,
            it.F_Family,
            it.F_Feature,
            it.F_Function_Application,
            it.F_Genus,
            it.F_Geo,
            it.F_Keywords,
            it.F_Location,
            it.F_Name_En,
            it.F_Pic01_URL,
            it.F_Summary,
            it.F_Update,
            it._id

        )
    }
}