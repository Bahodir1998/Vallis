package com.mobile.liderstoreagent.data.models

data class PaymentIncome(
    val amount: String,
    val amount_type: String,
    val cash_register: Int,
    val client: Int,
    val comment: String
)