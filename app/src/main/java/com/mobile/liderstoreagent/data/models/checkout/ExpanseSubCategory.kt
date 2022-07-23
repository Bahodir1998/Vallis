package com.mobile.liderstoreagent.data.models.checkout

data class ExpanseSubCategory(
	val data: List<DataItem?>? = null,
	val success: Boolean? = null
)

data class DataItem(
	val name: String? = null,
	val id: Int? = null,
	val category: Int? = null
)

