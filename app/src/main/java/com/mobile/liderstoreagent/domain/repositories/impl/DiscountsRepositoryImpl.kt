package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.discountsmodel.Discounts
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.DiscountsApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.DiscountsRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DiscountsRepositoryImpl : DiscountsRepository {

    private val api = ApiClient.retrofit.create(DiscountsApiInterface::class.java)
    override suspend fun getDiscounts(): Flow<Result<List<Discounts>?>> = flow {
        try {
            val response = api.getDiscounts()
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
           // emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xato!")
        }
    }


}