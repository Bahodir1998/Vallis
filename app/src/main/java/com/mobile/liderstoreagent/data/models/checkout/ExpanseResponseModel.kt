package com.mobile.liderstoreagent.data.models.checkout

data class ExpanseResponseModel(
	val id: Int? = null,
	val paymentType: String? = null,
	val money: String? = null,
	val comment: String? = null,
	val created_date: String? = null,
	val updated_date: String? = null,
	val category: Category1? = null,
	val subcategory: Subcategory1? = null,
	val agent: Int? = null
)
data class Category1(
	val id: Int? = null,
	val name: String? = null
)
data class Subcategory1(
	val id: Int? = null,
	val name: String? = null,
	val category: Int? = null
)