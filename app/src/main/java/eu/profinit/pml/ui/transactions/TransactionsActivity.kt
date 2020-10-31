package eu.profinit.pml.ui.transactions

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import eu.profinit.pml.databinding.ActivityNewPlanBinding
import eu.profinit.pml.databinding.ActivityTransactionsBinding
import eu.profinit.pml.ui.login.afterTextChanged

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

    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}