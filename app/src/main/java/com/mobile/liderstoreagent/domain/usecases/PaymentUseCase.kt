package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.payment.PaymentData
import com.mobile.liderstoreagent.data.models.payment.PaymentResponceModel

interface PaymentUseCase {
    val errorPaymentLiveData: LiveData<Unit>
    fun payPayment(paymentData: PaymentData): LiveData<PaymentResponceModel>
}