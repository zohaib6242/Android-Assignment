package com.android.goally.data.repo

import com.android.goally.data.network.rest.api.GeneralApi

class CoPilotRepo(private val generalApi: GeneralApi) {

    suspend fun getCoPilotList(authToken:String) = generalApi.getCoPilotList(authToken)
}