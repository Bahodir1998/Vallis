package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.checkout.*
import com.mobile.liderstoreagent.domain.repositories.impl.CheckOutRepoImpl
import com.mobile.liderstoreagent.domain.repositories.impl.WarehouseRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.CheckOutRepository
import com.mobile.liderstoreagent.domain.repositories.repo.WarehouseRepository
import com.mobile.liderstoreagent.domain.usecases.CheckOutUseCase
import kotlinx.coroutines.flow.collect

class CheckOutUseCaseImpl:CheckOutUseCase {

    private val repository: CheckOutRepository = CheckOutRepoImpl()

    override val errorExpenseLiveData= MutableLiveData<Unit>()
    override val errorExpenseSubCategoryLiveData=MutableLiveData<Unit>()

    override val errorExpenseRequestLiveData= MutableLiveData<Unit>()
    override val errorTransferRequestLiveData=MutableLiveData<Unit>()
    override val errorUpdateTransferRequestLiveData=MutableLiveData<Unit>()


    override fun getExpenseCategory(): LiveData<ExpenseModel> = liveData {
        repository.getExpenseCategory().collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorExpenseLiveData.postValue(Unit)
            }
        }
    }
    override fun getExpenseSubCategory(categoryId: Int): LiveData<ExpanseSubCategory> = liveData {
        repository.getExpenseSubCategory(categoryId).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorExpenseSubCategoryLiveData.postValue(Unit)
            }
        }
    }
    override fun setExpenseRequest(expanseRequestModel: ExpanseRequestModel): LiveData<ExpanseResponseModel> = liveData {
        repository.setExpanseRequest(expanseRequestModel).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorExpenseRequestLiveData.postValue(Unit)
            }
        }
    }

    override fun setTransferRequest(transferRequestModel: TransferRequestModel): LiveData<TransferResponseModel> = liveData {
        repository.setTransferRequest(transferRequestModel).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorExpenseRequestLiveData.postValue(Unit)
            }
        }
    }

    override fun updateTransferRequest(payment_id:Int,updateTransferModel: UpdateRequestModel): LiveData<UpdateTransferModel> = liveData {
        repository.updateTransferRequest(payment_id,updateTransferModel).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorUpdateTransferRequestLiveData.postValue(Unit)
            }
        }
}
}