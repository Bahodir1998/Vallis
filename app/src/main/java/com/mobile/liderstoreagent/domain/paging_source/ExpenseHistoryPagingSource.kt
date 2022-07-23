package com.mobile.liderstoreagent.domain.paging_source

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mobile.liderstoreagent.data.models.checkout.ExpensesItem
import com.mobile.liderstoreagent.data.source.remote.retrofit.CheckOutClientApi
import com.mobile.liderstoreagent.utils.Constants
import com.mobile.liderstoreagent.utils.SharedPref


class ExpenseHistoryPagingSource(
    private val context: Context,
    private val apiService: CheckOutClientApi,
    private val startDate: String,
    private val endDate: String
) : PagingSource<Int, ExpensesItem>() {
    override suspend fun load(
        params: LoadParams<Int>,
    ): LoadResult<Int, ExpensesItem> {

        val position = params.key ?: Constants.PAGE_STARTING_INDEX

        return try {
            val response = apiService.getExpenseHistory(
                "${Constants.BASE_URL}expense_discount/agent-expense/",
                10, position, SharedPref.user?.toInt()!!,
                startDate, endDate)

            val pagedResponse = response.body()
            Log.e("loadPaging:", pagedResponse?.expenses.toString())
            LoadResult.Page(
                data = pagedResponse?.expenses.orEmpty(),
                prevKey = null,
                nextKey = if (pagedResponse?.next == null) null else position + 10
            )
        } catch (exception: Exception) { 
            LoadResult.Error(exception)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, ExpensesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}