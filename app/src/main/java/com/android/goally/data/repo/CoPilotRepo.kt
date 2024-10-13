package com.android.goally.data.repo

import android.app.Application
import com.android.goally.data.db.dao.CoPilotDao
import com.android.goally.data.model.api.response.copilot.Routine
import com.android.goally.data.network.rest.api.GeneralApi
import com.android.goally.util.AppUtil
import com.haroldadmin.cnradapter.NetworkResponse

class CoPilotRepo(
    private val generalApi: GeneralApi,
    private val coPilotDao: CoPilotDao,
    private val application: Application
) {

    suspend fun getCoPilotList(authToken: String): Result<List<Routine>> {
        return if (AppUtil.isInternetAvailable(application)) {
            fetchFromNetwork(authToken)
        } else {
            fetchFromDb()
        }
    }

    private suspend fun fetchFromNetwork(authToken: String): Result<List<Routine>> {
        return try {
            when (val response = generalApi.getCoPilotList(authToken)) {
                is NetworkResponse.Success -> {
                    response.body?.routines?.let {
                        if (it.isNotEmpty()) {
                            coPilotDao.insertRoutines(it) // Sync database
                            Result.success(it)
                        } else {
                            Result.failure(Exception("No routines found from server."))
                        }
                    } ?: Result.failure(Exception("Empty response from server."))
                }
                else -> Result.failure(Exception("Failed to load from network"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun fetchFromDb(): Result<List<Routine>> {
        return try {
            val routines = coPilotDao.getAllRoutines()
            if (routines.isNotEmpty()) {
                Result.success(routines)
            } else {
                Result.failure(Exception("No offline data available."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

