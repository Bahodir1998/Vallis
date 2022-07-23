package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.payment.PaymentResponceModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PaymentApi {
    @Multipart
    @POST("core/agent-income-clients/")
    suspend fun payPayment(
        @Header("Accept") app_json: String,
        @Part("payment_type") address: RequestBody,
        @Part("payment") payment: Double,
        @Part("comment") comment: String,
        @Part("client") client: Int
    ): Response<PaymentResponceModel>
}