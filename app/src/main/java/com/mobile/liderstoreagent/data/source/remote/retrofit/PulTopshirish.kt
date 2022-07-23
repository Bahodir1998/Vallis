package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.checkout.TransferRequestModel
import com.mobile.liderstoreagent.data.models.checkout.TransferResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PulTopshirish {

    @Headers("Content-Type: application/json")
    @POST("order/sell-order-payment/")
    suspend fun setTransferData(@Body transferRequestModel: TransferRequestModel): Response<TransferResponseModel>
}