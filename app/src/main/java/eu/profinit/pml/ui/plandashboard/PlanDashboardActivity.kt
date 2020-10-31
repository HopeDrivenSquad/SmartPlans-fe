package eu.profinit.pml.ui.plandashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.databinding.ActivityDashboardPlanBinding
import eu.profinit.pml.ui.detail.PlanDetailActivity
import eu.profinit.pml.ui.newplan.NewPlanActivity
import kotlinx.android.synthetic.main.card_dashboard_overview.*

class PlanDashboardActivity : AppCompatActivity() {

    private lateinit var planDashboardViewModel: PlanDashboardViewModel
    private lateinit var binding: ActivityDashboardPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUI()
        setupVM()
        setupBindings()
    }

    private fun setupUI() {
        binding = ActivityDashboardPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupVM() {
        planDashboardViewModel = ViewModelProvider(this, PlanDashboardModelFactory())
            .get(PlanDashboardViewModel::class.java)
    }

    private fun setupBindings() {
        binding.swipeRefreshMyPlans.setOnRefreshListener { planDashboardViewModel.loadMyPlans() }
        binding.myPlansRecycler.adapter = PlanDashboardAdapter { processPlan(it) }
        binding.myPlansRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        planDashboardViewModel.myResult.observe(this, {
            (binding.myPlansRecycler.adapter as? PlanDashboardAdapter)?.setData(it)
            binding.myPlansRecycler.adapter?.notifyDataSetChanged()
        })

        planDashboardViewModel.loading.observe(this, {
            binding.swipeRefreshMyPlans.isRefreshing = it
        })

        binding.addNewPlan.setOnClickListener {
            navigateToNewPlan()
        }

        planDashboardViewModel.currentBalance.observe(this, {
            planDashboardViewModel.loadMyPlans()
        })

        planDashboardViewModel.loading.observe(this, {
            binding.swipeRefreshMyPlans.isRefreshing = it
        })
    }

    private fun processPlan(plan: Plan) {
        if (plan.id == -100) {
            planDashboardViewModel.currentBalance.postValue(plan.currentBalance)
        } else {
            plan.id?.let {
                if (!plan.enabled) {
                    planDashboardViewModel.enablePlan(it)
                } else {
                    planDashboardViewModel.disablePlan(it)
                }
            } ?: run {
                Toast.makeText(this, "Plan has no ID", Toast.LENGTH_LONG)
            }
        }
    }

    private fun navigateToDetail(plan: Plan) {
        val intent = Intent(this, PlanDetailActivity::class.java)
        intent.putExtra("PLAN", plan)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun navigateToNewPlan() {
        val intent = Intent(this, NewPlanActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onResume() {
        super.onResume()

        planDashboardViewModel.loadMyPlans()
    }
}