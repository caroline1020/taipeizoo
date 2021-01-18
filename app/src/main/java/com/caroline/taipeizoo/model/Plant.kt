package com.caroline.taipeizoo.model

import com.squareup.moshi.Json

data class Plant(
    val F_AlsoKnown: String,
    val F_Brief: String,
    val F_CID: String,
    val F_Code: String,
    val F_Family: String,
    val F_Feature: String,
    @Json(name = "F_Function＆Application")
    val F_Function_Application: String,
    val F_Genus: String,
    val F_Geo: String,
    val F_Keywords: String,
    val F_Location: String,
    val F_Name_En: String,
    val F_Name_Latin: String,
    val F_Pic01_ALT: String,
    val F_Pic01_URL: String,
    val F_Pic02_ALT: String,
    val F_Pic02_URL: String,
    val F_Pic03_ALT: String,
    val F_Pic03_URL: String,
    val F_Pic04_ALT: String,
    val F_Pic04_URL: String,
    val F_Summary: String,
    val F_Update: String,
    val F_Vedio_URL: String,
    val F_Voice01_ALT: String,
    val F_Voice01_URL: String,
    val F_Voice02_ALT: String,
    val F_Voice02_URL: String,
    val F_Voice03_ALT: String,
    val F_Voice03_URL: String,
    val F_pdf01_ALT: String,
    val F_pdf01_URL: String,
    val F_pdf02_ALT: String,
    val F_pdf02_URL: String,
    val _id: Int,
    @Json(name = "\uFEFFF_Name_Ch")
    val F_Name_Ch: String


) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as Plant

        if (other.F_Name_Ch == F_Name_Ch) {
            return true
        }

        return false
    }

    override fun hashCode(): Int {
        return F_Name_Ch.hashCode()
    }
}