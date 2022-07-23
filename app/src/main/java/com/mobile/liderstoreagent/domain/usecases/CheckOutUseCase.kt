package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.checkout.*

interface CheckOutUseCase {
    val errorExpenseLiveData : LiveData<Unit>
    val errorExpenseSubCategoryLiveData : LiveData<Unit>
    val errorExpenseRequestLiveData : LiveData<Unit>

    val errorTransferRequestLiveData : LiveData<Unit>
    val errorUpdateTransferRequestLiveData : LiveData<Unit>
    fun getExpenseCategory(): LiveData<ExpenseModel>

   fun getExpenseSubCategory(categoryId:Int): LiveData<ExpanseSubCategory>

    fun setExpenseRequest(expanseRequestModel: ExpanseRequestModel):LiveData<ExpanseResponseModel>

   fun setTransferRequest(transferRequestModel: TransferRequestModel):LiveData<TransferResponseModel>

   fun updateTransferRequest(payment_id:Int,updateTransferModel: UpdateRequestModel):LiveData<UpdateTransferModel>


}