package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.payment.PaymentData
import com.mobile.liderstoreagent.data.models.payment.PaymentResponceModel
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.PaymentApi
import com.mobile.liderstoreagent.domain.repositories.repo.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class PaymentRepoImpl : PaymentRepository {

    private val api = ApiClient.retrofit.create(PaymentApi::class.java)
    override suspend fun payPayment(payment1: PaymentData): Flow<Result<PaymentResponceModel?>> = flow {
        val paymentType = payment1.payment_type.toRequestBody("text/plain".toMediaTypeOrNull())

        try {
            val response = api.payPayment(
                "application/json",
                paymentType,
                payment1.payment,
                payment1.comment,
                payment1.client
            )
            if (response.code() == 201) {
                emit(Result.success(response.body()))
            } else if (response.code() == 400) {
                emit(Result.success(response.body()))
            }
        } catch (e: Exception) {
        }
    }
}