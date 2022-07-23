package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.checkout.*
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.CheckOutClientApi
import com.mobile.liderstoreagent.data.source.remote.retrofit.WarehouseApi
import com.mobile.liderstoreagent.domain.repositories.repo.CheckOutRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CheckOutRepoImpl():CheckOutRepository {

    private val api = ApiClient.retrofit.create(CheckOutClientApi::class.java)

    override suspend fun getExpenseCategory(): Flow<Result<ExpenseModel>> = flow {
        try {
            val response = api.getCategory()
            if (response.code() == 200){
                emit(Result.success(response.body()) as Result<ExpenseModel>)
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }
    override suspend fun getExpenseSubCategory(category_id:Int): Flow<Result<ExpanseSubCategory>> = flow {
        try {
            val response = api.getsubCategory(category_id)
            if (response.code() == 200){
                emit(Result.success(response.body()) as Result<ExpanseSubCategory>)
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }
    override suspend fun setExpanseRequest(expanseRequestModel: ExpanseRequestModel): Flow<Result<ExpanseResponseModel>> =  flow {
        try {
            val response = api.setExpenseData(expanseRequestModel)
            if (response.code() == 201){
                emit(Result.success(response.body()) as Result<ExpanseResponseModel>)
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }
    override suspend fun setTransferRequest(transferRequestModel: TransferRequestModel): Flow<Result<TransferResponseModel>> =  flow {
        try {
            val response = api.setTransferData(transferRequestModel)
            if (response.code() == 201){
                emit(Result.success(response.body()) as Result<TransferResponseModel>)
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }




    }

    override suspend fun updateTransferRequest(
        payment_id: Int,
        updateRequestModel: UpdateRequestModel
    ): Flow<Result<UpdateTransferModel>>  =  flow {
        try {
            val response = api.updateTransferData(payment_id,updateRequestModel)
            if (response.code() == 200) {
                emit(Result.success(response.body()) as Result<UpdateTransferModel>)
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }




    }
}