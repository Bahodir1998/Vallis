package com.mobile.liderstoreagent.data.models.checkout

data class TransferRequestModel(
	val payment_type: String? = null,
	val agent: Int? = null,
	val comments: String? = null,
	val payment: String? = null
)

