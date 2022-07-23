package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.checkout.*
import retrofit2.Response
import retrofit2.http.*

interface CheckOutClientApi {
    /* {
         "payment_type": "dollar",
         "money": "string",
         "comment": "string",
         "category": 0,
         "subcategory": 0,
         "agent": 0
     }*/
    //xarajat getCategory
    @GET("expense_discount/agent-expense-category/")
    suspend fun getCategory(): Response<ExpenseModel>

    @GET("expense_discount/agent-expense-subcategory-byid/")
    suspend fun getsubCategory(@Query("category_id") category_id: Int): Response<ExpanseSubCategory>

    //xarajat POST
    @Headers("Content-Type: application/json")
    @POST("expense_discount/agent-expense/")
    suspend fun setExpenseData(@Body expensRequestModel: ExpanseRequestModel): Response<ExpanseResponseModel>

    @Headers("Content-Type: application/json")
    @POST("order/sell-order-payment/")
    suspend fun setTransferData(@Body transferRequestModel: TransferRequestModel): Response<TransferResponseModel>

    @GET
    suspend fun getTransferHistory(
        @Url url: String,
        @Query("page") pageNumber: Int = 10,
        @Query("offset") offset: Int? = 0,
        @Query("agent") agentId: Int,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Response<TransferHistoryModel>

    @GET
    suspend fun getExpenseHistory(
        @Url url: String,
        @Query("page") pageNumber: Int = 10,
        @Query("offset") offset: Int? = 0,
        @Query("agent_id") agentId: Int,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Response<ExpenseHistoryModel>

    // @Headers("Content-Type: application/json")

  //  @Headers("Content-Type: application/json")
    @PUT("order/sell-order-payment-change/{payment_id}/")
    suspend fun updateTransferData(
        @Path("payment_id") paymentid: Int,
        @Body updateRequest: UpdateRequestModel
    ): Response<UpdateTransferModel>


}