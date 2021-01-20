package com.caroline.taipeizoo.model

data class ZoneResult(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Zone>,
    val sort: String
)