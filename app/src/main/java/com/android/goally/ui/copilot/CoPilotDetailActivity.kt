package com.android.goally.ui.copilot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.goally.BaseActivity
import com.android.goally.data.model.api.response.copilot.Activity
import com.android.goally.data.model.api.response.copilot.Routine
import com.android.goally.databinding.ActivityCoPilotDetailBinding
import com.android.goally.ui.copilot.adapter.CoPilotActivitiesAdapter
import com.android.goally.ui.viewmodels.CoPilotViewModel
import com.android.goally.util.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoPilotDetailActivity : BaseActivity() {

    private var _binding: ActivityCoPilotDetailBinding? = null
    private val binding get() = _binding!!

    private val coPilotViewModel: CoPilotViewModel by viewModels()

    private lateinit var activitiesAdapter: CoPilotActivitiesAdapter

    private var activities: List<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCoPilotDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupIntentData()
        setupViews()
        setupObservers()

    }

    private fun setupIntentData() {
        activities = intent.getParcelableArrayListExtra(INTENT_DATA_KEY_COPILOT_DETAIL)

    }

    private fun setupObservers() {

    }

    private fun setupViews() {
        binding.appbar.ivBack.setSafeOnClickListener {
            finish()
        }
        if (activities != null) {
            activitiesAdapter = CoPilotActivitiesAdapter(activities!!)
            binding.rvCopilotActivities.layoutManager = LinearLayoutManager(this)
            binding.rvCopilotActivities.adapter = activitiesAdapter
        }
    }

    companion object{

        val INTENT_DATA_KEY_COPILOT_DETAIL = "INTENT_DATA_KEY_COPILOT_DETAIL"

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, CoPilotDetailActivity::class.java)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}