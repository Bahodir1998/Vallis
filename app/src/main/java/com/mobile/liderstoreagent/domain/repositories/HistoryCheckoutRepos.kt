package com.mobile.liderstoreagent.domain.repositories

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mobile.liderstoreagent.data.models.checkout.ExpenseHistoryModel
import com.mobile.liderstoreagent.data.models.checkout.TransferHistoryModel
import com.mobile.liderstoreagent.data.source.remote.retrofit.CheckOutClientApi
import com.mobile.liderstoreagent.data.source.remote.retrofit.HistoryClientApi
import com.mobile.liderstoreagent.domain.paging_source.ExpenseHistoryPagingSource
import com.mobile.liderstoreagent.domain.paging_source.HistoryPagingSource
import com.mobile.liderstoreagent.domain.paging_source.TransferHistoryPagingSource

class HistoryCheckoutRepos(private val apiService: CheckOutClientApi) {

    fun getTransferHistory(context: Context,startDate:String,endDate:String) = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TransferHistoryPagingSource(context, apiService, startDate,endDate) }
    ).liveData


    fun getExpenseHistory(context: Context,startDate:String,endDate:String)= Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false),
        pagingSourceFactory = {ExpenseHistoryPagingSource(context,apiService,startDate,endDate)}
    ).liveData



}