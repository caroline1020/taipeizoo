package com.caroline.taipeizoo.network

import com.caroline.taipeizoo.database.DatabaseArea
import com.caroline.taipeizoo.model.AreaResponse
import com.squareup.moshi.JsonClass

/**
 * Created by nini on 2021/1/20.
 */


@JsonClass(generateAdapter = true)
data class NetworkAreaContainer(val areaResponse: AreaResponse)

fun NetworkAreaContainer.asDatabaseModel(): List<DatabaseArea> {
    val areaList = areaResponse.result.results
    return areaList.map {
        DatabaseArea(
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