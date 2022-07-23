package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.agentbox.Agent
import retrofit2.http.GET
import retrofit2.http.Query

interface Kassa {

    @GET("core/agent-cash-register/")
    suspend fun getkassa(@Query("agent_id") agent_id:String): Agent

}