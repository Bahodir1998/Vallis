package com.mobile.liderstoreagent.ui.viewmodels

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.mobile.liderstoreagent.data.models.historymodel.other.Result
import com.mobile.liderstoreagent.domain.paging_source.DataWrapper
import com.mobile.liderstoreagent.domain.repositories.HistoryCheckoutRepos

class CheckOutHistoryViewModel(private val context: Context,
                               private val repository: HistoryCheckoutRepos):ViewModel(){


    private val _historyState = MutableLiveData<DataWrapper<List<Result>>>()
    val historyState: LiveData<DataWrapper<List<Result>>> get() = _historyState

    fun getTransferHistory(startDate: String,endDate:String) = repository.getTransferHistory(context, startDate,endDate).cachedIn(viewModelScope)

    fun getExpenseHistory(startDate: String,endDate:String) =repository.getExpenseHistory(context,startDate,endDate).cachedIn(viewModelScope)


}


class CheckOutHistoryFactoryModel(
    private val context: Context,
    private val repository: HistoryCheckoutRepos
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckOutHistoryViewModel::class.java)) {
            return CheckOutHistoryViewModel(context, repository) as T
        } /*else if (modelClass.isAssignableFrom(OwnProductViewModel::class.java)) {
            return OwnProductViewModel(context) as T
        }*/
        throw IllegalArgumentException("$modelClass is not FurnitureViewModel")
    }
}