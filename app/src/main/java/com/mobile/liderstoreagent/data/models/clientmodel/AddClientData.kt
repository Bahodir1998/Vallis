package com.mobile.liderstoreagent.data.models.clientmodel

import java.io.File

class AddClientData(
        val id:Int,
        val name: String,
        val address: String,
        val responsible_agent: String,
        val director_phone_number: String,
        val work_phone_number: String = "",
        val latitude: Double,
        val longitude: Double,
        val image: File ,
        val sale_agents: Int,
        val INN:String,
        val director:String,
        val birthdate:String,
        val car:Int,
        val market_type:Int,
        val target:String,
        val territory:Int,
        val price_type:Int
)



class UpdateClientData(
        val id:Int,
        val marketName: String,
        val address: String,
        val responsiblePerson: String,
        val directorPhone: String,
        val workPhone: String = "",
        val latitude: Double,
        val longitude: Double,
        val image: File?,
        val agentId: Int,
        val INN:String,
        val directorName:String,
        val birthDate:String,
        val car:Int,
        val market:Int,
        val target:String,
        val territory:Int,
        val price_type:Int
)