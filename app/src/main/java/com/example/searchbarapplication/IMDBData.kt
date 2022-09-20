package com.example.searchbarapplication

data class IMDBData(
    val query: String,
    val results: List<IMDBDataItem>
)