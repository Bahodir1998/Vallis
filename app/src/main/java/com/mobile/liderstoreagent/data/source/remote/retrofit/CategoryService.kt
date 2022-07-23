package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.category.Category
import com.mobile.liderstoreagent.data.models.category.Subcategory
import com.mobile.liderstoreagent.data.models.categorymodel.CategoryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryService {

    @GET("expense_discount/agent-expense-category/")
    suspend fun getCategory():Response<Category>

    @GET("expense_discount/agent-expense-subcategory-byid/")
    suspend fun getSubCategory(
        @Query("category_id") category_id:Int
    ):Response<Subcategory>
}