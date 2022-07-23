package com.mobile.liderstoreagent.data.models.checkout

data class ExpanseRequestModel(
	val payment_type: String? = null,
	val agent: Int? = null,
	val money: String? = null,
	val comment: String? = null,
	val category: String? = null,
	val subcategory: String? = null
)

