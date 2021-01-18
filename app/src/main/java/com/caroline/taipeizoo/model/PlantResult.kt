package com.caroline.taipeizoo.model

data class PlantResult(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Plant>,
    val sort: String
)