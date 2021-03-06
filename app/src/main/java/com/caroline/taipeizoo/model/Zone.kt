package com.caroline.taipeizoo.model

import java.io.Serializable

data class Zone(
    val E_Category: String,
    val E_Geo: String,
    val E_Info: String,
    val E_Memo: String,
    val E_Name: String,
    val E_Pic_URL: String,
    val E_URL: String,
    val E_no: String,
    val _id: Int
) : Serializable