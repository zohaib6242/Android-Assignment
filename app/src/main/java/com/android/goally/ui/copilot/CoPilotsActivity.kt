package com.android.goally.ui.copilot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.goally.BaseActivity
import com.android.goally.data.db.entities.token.Authentication
import com.android.goally.data.model.api.response.copilot.Routine
import com.android.goally.databinding.ActivityCoPilotsBinding
import com.android.goally.ui.copilot.CoPilotDetailActivity.Companion.INTENT_DATA_KEY_COPILOT_DETAIL
import com.android.goally.ui.copilot.adapter.CopilotAdapter
import com.android.goally.ui.copilot.filter.FilterDialogFragment
import com.android.goally.ui.viewmodels.CoPilotViewModel
import com.android.goally.util.setSafeOnClickListener
import com.android.goally.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoPilotsActivity : BaseActivity() {

    private var _binding: ActivityCoPilotsBinding? = null
    private val binding get() = _binding!!

    private val coPilotViewModel: CoPilotViewModel by viewModels()

    private var routines: MutableList<Routine> = mutableListOf()

    private var adapter: CopilotAdapter = CopilotAdapter(routines, ::onCopilotClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCoPilotsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        generalViewModel.getAuthenticationLive().observe(this) {
            it?.let {
                Handler(Looper.getMainLooper()).postDelayed({
                    fetchCopilots(it)
                } , 1000)
            }
        }
        coPilotViewModel.coPilotApiResponseLiveData.observe(this){
            it?.let {
                routines.addAll(it.routines)
                adapter.submitList(it.routines)
            }
        }
    }

    private fun fetchCopilots(authentication: Authentication){
        coPilotViewModel.getCoPilots(authentication.token!! , onLoading = {
            //progressBar.isVisible = it
        }, onError = {
            toast(it)
        }, onSuccess = {
            //goToHomeScreen()
        })
    }

    private fun setupViews() {
        binding.textViewFolder.setSafeOnClickListener {
            val filterDialog = FilterDialogFragment(routines) { selectedFilter ->
                adapter.filter.filter(selectedFilter)
            }
            filterDialog.show(supportFragmentManager, "FilterDialog")
        }
        binding.appbar.ivBack.setSafeOnClickListener {
            finish()
        }
        adapter = CopilotAdapter(listOf(), ::onCopilotClicked)
        binding.rvCopilots.layoutManager = LinearLayoutManager(this)
        binding.rvCopilots.adapter = adapter
    }

    companion object{
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, CoPilotsActivity::class.java)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onCopilotClicked(routine: Routine) {
        if(!routine.activities.isNullOrEmpty()){
            val activities = routine.activities
            val intent = CoPilotDetailActivity.getCallingIntent(this)
            intent.putParcelableArrayListExtra(INTENT_DATA_KEY_COPILOT_DETAIL, ArrayList(activities)) // Pass the list
            startActivity(intent)
        }

    }
}


