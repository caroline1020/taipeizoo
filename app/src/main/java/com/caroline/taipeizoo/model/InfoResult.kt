package com.caroline.taipeizoo.model

data class InfoResult(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Info>,
    val sort: String
)