package com.mobile.liderstoreagent.data.models.checkout

data class ExpenseHistoryModel(
	val next: String? = null,
	val previous: String? = null,
	val count: Int? = null,
	val expenses: List<ExpensesItem>,
)
data class ExpensesItem(
	val payment_type: String? = null,
	val agent: Int? = null,
	val money: String? = null,
	val comment: String? = null,
	val id: Int? = null,
	val created_date: String? = null,
	val updated_date: String? = null,
	val category: Category? = null,
	val subcategory: Subcategory? = null
)
data class Category(
	val id: Int? = null,
	val name: String? = null
)
data class Subcategory(
	val id: Int? = null,
	val name: String? = null,
	val category: Int? = null
)

