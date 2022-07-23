package com.mobile.liderstoreagent.data.models.checkout

data class TransferResponseModel(
	val paymentType: String? = null,
	val approved: Boolean? = null,
	val agent: Int? = null,
	val comments: String? = null,
	val payment: String? = null,
	val id: Int? = null,
	val updatedDate: String? = null,
	val status: String? = null
)

