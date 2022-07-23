package com.mobile.liderstoreagent.data.models.category

data class Category(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)