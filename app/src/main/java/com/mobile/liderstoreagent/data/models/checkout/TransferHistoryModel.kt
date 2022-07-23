package com.mobile.liderstoreagent.data.models.checkout

import java.io.Serializable

data class TransferHistoryModel(
	val next: String? = null,
	val previous: String? = null,
	val payments: List<PaymentsItem> ,
	val count: Int? = null
)
data class PaymentsItem(
	val payment_type: String? = null,
	val approved: Boolean? = null,
	val agent: Agent? = null,
	val comments: String? = null,
	val payment: String? = null,
	val id: Int? = null,
	val created_date: String? = null,
	val updated_date: String? = null,
	val status: String? = null
)
data class Agent(
	val role: String? = null,
	val userPermissions: List<Any?>? = null,
	val phoneMother: Any? = null,
	val phoneFather: Any? = null,
	val isAdmin: Boolean? = null,
	val password: String? = null,
	val id: Int? = null,
	val dateJoined: String? = null,
	val department: Any? = null,
	val firstName: String? = null,
	val birthdateFather: Any? = null,
	val isSuperuser: Boolean? = null,
	val isActive: Boolean? = null,
	val selfBirthDate: Any? = null,
	val lastLogin: String? = null,
	val fullNameFather: Any? = null,
	val lastName: String? = null,
	val groups: List<Any?>? = null,
	val selfAddress: Any? = null,
	val agentType: String? = null,
	val fullNameChildren: Any? = null,
	val birthdateChildren: Any? = null,
	val familyAddress: Any? = null,
	val fullNameMother: Any? = null,
	val phoneNumber: String? = null,
	val birthdateMother: Any? = null
)

