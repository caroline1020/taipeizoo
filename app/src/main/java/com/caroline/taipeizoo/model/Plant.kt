package com.caroline.taipeizoo.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Plant(
    val F_AlsoKnown: String,
    val F_Brief: String,
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
    val F_Pic01_URL: String,
    val F_Summary: String,
    val F_Update: String,
    val _id: Int,
    @Json(name = "\uFEFFF_Name_Ch")
    val F_Name_Ch: String


) : Serializable