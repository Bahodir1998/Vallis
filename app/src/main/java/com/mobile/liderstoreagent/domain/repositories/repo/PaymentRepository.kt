package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.payment.PaymentData
import com.mobile.liderstoreagent.data.models.payment.PaymentResponceModel
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun payPayment(payment: PaymentData): Flow<Result<PaymentResponceModel?>>
}