package com.mobile.liderstoreagent.data.models.checkout

data class ExpenseModel(
	val next: Any,
	val previous: Any,
	val count: Int,
	val results: List<ResultsItem>
)

data class ResultsItem(
	val agent: Int,
	val name: String,
	val id: Int
)

