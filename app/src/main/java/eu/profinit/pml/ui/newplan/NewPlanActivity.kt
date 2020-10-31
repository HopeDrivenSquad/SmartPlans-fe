package eu.profinit.pml.ui.newplan

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.profinit.pml.data.newplan.NewPlanRepository
import eu.profinit.pml.databinding.ActivityDashboardPlanBinding
import eu.profinit.pml.databinding.ActivityNewPlanBinding
import eu.profinit.pml.ui.login.afterTextChanged
import eu.profinit.pml.ui.plandashboard.PlanDashboardAdapter
import eu.profinit.pml.ui.plandashboard.PlanDashboardModelFactory
import eu.profinit.pml.ui.plandashboard.PlanDashboardViewModel

class NewPlanActivity : AppCompatActivity() {
    private lateinit var newPlanViewModel: NewPlanViewModel
    private lateinit var binding: ActivityNewPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUI()
        setupVM()
        setupBindings()
    }

    private fun setupUI() {
        binding = ActivityNewPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupVM() {
        newPlanViewModel = ViewModelProvider(this, NewPlanModelFactory())
            .get(NewPlanViewModel::class.java)
    }

    private fun setupBindings() {
        binding.planAmountValue.afterTextChanged {
            newPlanViewModel.plan?.amount = Integer.parseInt(it)
        }

        binding.planNameValue.afterTextChanged {
            newPlanViewModel.plan?.name = it
        }

        binding.planExpirationValue.afterTextChanged {
            newPlanViewModel.plan?.dateTo = it
        }

        binding.createButton.setOnClickListener {
            newPlanViewModel.createNewPlan()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        newPlanViewModel.done.observe(this, {
            if (it) {
                onBackPressed()
            } else {
                Toast.makeText(this, "Error occured", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}