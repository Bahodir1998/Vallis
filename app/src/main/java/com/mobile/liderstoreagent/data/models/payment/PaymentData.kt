package com.mobile.liderstoreagent.data.models.payment

class PaymentData(
    val payment_type: String,
    val payment: Double,
    var comment:String,
    val client: Int,
)