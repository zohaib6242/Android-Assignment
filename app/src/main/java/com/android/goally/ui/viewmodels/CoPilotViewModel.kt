package com.android.goally.ui.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.goally.data.model.api.response.copilot.Routine
import com.android.goally.data.repo.CoPilotRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoPilotViewModel @Inject constructor(
    private val coPilotRepo: CoPilotRepo,
    application: Application
) : AndroidViewModel(application) {

    private val _coPilotApiResponseLiveData = MutableLiveData<List<Routine>>()
    val coPilotApiResponseLiveData: LiveData<List<Routine>>
        get() = _coPilotApiResponseLiveData

    fun getCoPilots(
        authToken: String,
        onLoading: (Boolean) -> Unit,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        onLoading(true)
        viewModelScope.launch {
            val result = coPilotRepo.getCoPilotList(authToken)
            result.onSuccess { routines ->
                _coPilotApiResponseLiveData.postValue(routines)
                onSuccess()
            }.onFailure { error ->
                onError(error.message ?: "An unknown error occurred.")
            }
            onLoading(false)
        }
    }
}
