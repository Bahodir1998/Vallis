package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.checkout.*
import kotlinx.coroutines.flow.Flow

interface CheckOutRepository {
    suspend fun getExpenseCategory(): Flow<Result<ExpenseModel>>
    suspend fun getExpenseSubCategory(category_id: Int): Flow<Result<ExpanseSubCategory>>
    suspend fun setExpanseRequest(expanseRequestModel: ExpanseRequestModel): Flow<Result<ExpanseResponseModel>>
    suspend fun setTransferRequest(transferRequestModel: TransferRequestModel): Flow<Result<TransferResponseModel>>

    suspend fun updateTransferRequest(
        payment_id: Int,
        updateRequestModel: UpdateRequestModel
    ): Flow<Result<UpdateTransferModel>>

}