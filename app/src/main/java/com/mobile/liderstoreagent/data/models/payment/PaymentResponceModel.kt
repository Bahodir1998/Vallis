package com.mobile.liderstoreagent.data.models.payment

data class PaymentResponceModel(
    val cash_register: Int,
    val client: Int,
    val comment: String,
    val created_date: String,
    val currency_dollar: String,
    val id: Int,
    val payment: String,
    val payment_type: String,
    val updated_date: String
)