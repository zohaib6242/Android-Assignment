package com.android.goally.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.android.goally.data.repo.CoPilotRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoPiloDetailViewModel @Inject constructor(private val coPilotRepo: CoPilotRepo) : ViewModel()  {
}