package com.mobile.liderstoreagent.data.models.checkout

data class UpdateRequestModel(
	val payment_type: String? = null,
	val agent: Int? = null,
	val comments: String? = null,
	val payment: String? = null
)

