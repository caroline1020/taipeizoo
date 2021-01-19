package com.caroline.taipeizoo.model

data class AreaResult(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Area>,
    val sort: String
)