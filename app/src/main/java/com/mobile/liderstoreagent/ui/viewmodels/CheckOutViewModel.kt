package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.checkout.*
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData
import com.mobile.liderstoreagent.domain.usecases.CategoriesUseCase
import com.mobile.liderstoreagent.domain.usecases.CheckOutUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.CategoriesUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.CheckOutUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class CheckOutViewModel : ViewModel() {
    private val checkOutUseCase: CheckOutUseCase = CheckOutUseCaseImpl()
    val errorCategoriesLiveData: LiveData<Unit> = checkOutUseCase.errorExpenseLiveData

    val progressWarehouseLiveData = MutableLiveData<Boolean>()

    val successExpenseLiveData = MediatorLiveData<ExpenseModel>()
    val connectionErrorExpnenseCatLiveData = MutableLiveData<Unit>()

    val successSubCategoryExpenseLiveData = MediatorLiveData<ExpanseSubCategory>()
    val connectionErrorExpenseSubCategoryLiveData = MutableLiveData<Unit>()


    val successExpenseRequestLiveData = MediatorLiveData<ExpanseResponseModel>()
    val connectionErrorExpenseRequestLiveData = MutableLiveData<Unit>()

    val successTransferRequestLiveData = MediatorLiveData<TransferResponseModel>()
    val connectionErrorTransferRequestLiveData= MutableLiveData<Unit>()
    val errorTransferRequestLiveData= MutableLiveData<Unit>()

    val successUpdateTransferLiveData = MediatorLiveData<UpdateTransferModel>()
    val connectionErrorUpdateTransferRequestLiveData= MutableLiveData<Unit>()



    fun getExpenseCategory() {
        if (isConnected()) {
            progressWarehouseLiveData.value = true
            val liveData = checkOutUseCase.getExpenseCategory()
            successExpenseLiveData.addSource(liveData) {
                progressWarehouseLiveData.value = false
                successExpenseLiveData.value = it
                successExpenseLiveData.removeSource(liveData)
            }

        } else {
            connectionErrorExpnenseCatLiveData.value = Unit
        }
    }

    fun getExpenseSubCategory(category_id:Int) {
        if (isConnected()) {
            progressWarehouseLiveData.value = true

            val liveData = checkOutUseCase.getExpenseSubCategory(category_id)

            successSubCategoryExpenseLiveData.addSource(liveData) {
                progressWarehouseLiveData.value = false
                successSubCategoryExpenseLiveData.value = it
                successSubCategoryExpenseLiveData.removeSource(liveData)
            }

        } else {
            connectionErrorExpenseSubCategoryLiveData.value = Unit
        }
    }

    fun setExpenseRequest(expanseRequestModel: ExpanseRequestModel) {
        if (isConnected()) {
            progressWarehouseLiveData.value = true
            val liveData = checkOutUseCase.setExpenseRequest(expanseRequestModel)
            successExpenseRequestLiveData.addSource(liveData) {
                progressWarehouseLiveData.value = false
                successExpenseRequestLiveData.value = it
                successExpenseRequestLiveData.removeSource(liveData)
            }

        } else {
            connectionErrorExpenseSubCategoryLiveData.value = Unit
        }
    }
    fun setTransferRequest(transferRequestModel: TransferRequestModel) {
        if (isConnected()) {
            progressWarehouseLiveData.value = true
            val liveData = checkOutUseCase.setTransferRequest(transferRequestModel)

            successTransferRequestLiveData.addSource(liveData) {
                progressWarehouseLiveData.value = false
                successTransferRequestLiveData.value = it
                successTransferRequestLiveData.removeSource(liveData)
            }

        } else {
            connectionErrorTransferRequestLiveData.value = Unit
        }
    }

    fun updateTransferRequest(payment_id:Int,updateRequestModel:UpdateRequestModel) {
        if (isConnected()) {
            progressWarehouseLiveData.value = true
            val liveData = checkOutUseCase.updateTransferRequest(payment_id,updateRequestModel)
            successUpdateTransferLiveData.addSource(liveData) {
                progressWarehouseLiveData.value = false
                successUpdateTransferLiveData.value = it
                successUpdateTransferLiveData.removeSource(liveData)
                if (it.success==false){
                    errorTransferRequestLiveData.value = Unit
                }
            }
        } else {
            connectionErrorUpdateTransferRequestLiveData.value = Unit
        }
    }

}