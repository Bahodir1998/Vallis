package com.mobile.liderstoreagent.domain.paging_source

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mobile.liderstoreagent.data.models.checkout.PaymentsItem
import com.mobile.liderstoreagent.data.models.historymodel.other.Result
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.CheckOutClientApi
import com.mobile.liderstoreagent.data.source.remote.retrofit.HistoryClientApi
import com.mobile.liderstoreagent.utils.Constants
import com.mobile.liderstoreagent.utils.SharedPref

class TransferHistoryPagingSource(  private val context: Context,
                                    private val apiService: CheckOutClientApi,
                                    private val startDate:String,
                                    private val endDate:String
                                    ): PagingSource<Int, PaymentsItem>() {
        override suspend fun load(
            params: LoadParams<Int>,
        ): LoadResult<Int, PaymentsItem> {

            val position = params.key ?: Constants.PAGE_STARTING_INDEX

            return try {
                val response = apiService.getTransferHistory(
                    "${Constants.BASE_URL}order/sell-order-payment/",
                    10,position, SharedPref.user?.toInt()!!,
                    startDate,endDate
                )
                val pagedResponse = response.body()
                Log.e("loadFeraz",pagedResponse?.payments.toString())
                LoadResult.Page(
                    data = pagedResponse?.payments.orEmpty(),
                    prevKey = null,
                    nextKey = if (pagedResponse?.next == null) null else position + 10
                )

            } catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, PaymentsItem>): Int? {

            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }

}