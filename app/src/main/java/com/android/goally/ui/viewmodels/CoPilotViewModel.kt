package com.android.goally.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goally.data.db.entities.token.Authentication
import com.android.goally.data.model.api.response.copilot.CoPilotApiResponse
import com.android.goally.data.repo.CoPilotRepo
import com.android.goally.util.LogUtil
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoPilotViewModel @Inject constructor(private val coPilotRepo: CoPilotRepo) : ViewModel() {

    private val _coPilotApiResponseLiveData = MutableLiveData<CoPilotApiResponse>()
    val coPilotApiResponseLiveData: LiveData<CoPilotApiResponse>
        get() = _coPilotApiResponseLiveData

    fun getCoPilots(authToken:String,
                    onLoading: (Boolean) -> Unit,
                    onError: (String) -> Unit,
                    onSuccess: () -> Unit) {
        onLoading(true)
        viewModelScope.launch {
            when (val res = coPilotRepo.getCoPilotList(authToken)) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    res.body?.let {
                        if(it.routines != null && it.routines.isNotEmpty()){
                            //set copilot live data here
                            onSuccess()
                            _coPilotApiResponseLiveData.postValue(it)
                        }
                    }?:run {
                        onError("Something went wrong")
                    }
                    onLoading(false)
                }

                is NetworkResponse.ServerError -> {
                    LogUtil.e(res.code.toString())
                    LogUtil.e(res.body?.message)
                    onError(res.body?.message ?: "Server error")
                    onLoading(false)
                }

                is NetworkResponse.NetworkError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Network error")
                    onLoading(false)
                }

                is NetworkResponse.UnknownError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Unknown error")
                    onLoading(false)
                }
            }
        }
    }
}