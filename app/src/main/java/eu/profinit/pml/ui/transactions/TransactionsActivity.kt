package eu.profinit.pml.ui.transactions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.profinit.pml.R
import eu.profinit.pml.data.common.TransactionCategory
import eu.profinit.pml.databinding.ActivityNewPlanBinding
import eu.profinit.pml.databinding.ActivityTransactionsBinding
import eu.profinit.pml.ui.login.afterTextChanged
import eu.profinit.pml.ui.plandashboard.PlanDashboardAdapter
import java.text.DecimalFormat
import kotlin.math.abs

class TransactionsActivity : AppCompatActivity() {
    private lateinit var transactionsViewModel: TransactionsViewModel
    private lateinit var binding: ActivityTransactionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUI()
        setupVM()
        setupBindings()
    }

    private fun setupUI() {
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupVM() {
        transactionsViewModel = ViewModelProvider(this, TransactionsModelFactory())
            .get(TransactionsViewModel::class.java)
    }

    private fun setupBindings() {
        binding.categoriesList.adapter = TransactionAdapter(clickListener = ::processCategory)
        binding.categoriesList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        transactionsViewModel.categories.observe(this, {
            (binding.categoriesList.adapter as? TransactionAdapter)?.setData(it)
            binding.categoriesList.adapter?.notifyDataSetChanged()
        })

        transactionsViewModel.summary.observe(this, {
            binding.allSavedValue.text = String.format("%s CZK" , DecimalFormat("#,###").format(it.amountSavedPerMonth))
        })

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()

        transactionsViewModel.loadTransactions()
    }

    private fun processCategory(category: TransactionCategory, type: Int) {

    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}