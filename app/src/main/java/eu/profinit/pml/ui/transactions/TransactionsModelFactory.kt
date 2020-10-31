package eu.profinit.pml.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.profinit.pml.data.newplan.NewPlanRepository
import eu.profinit.pml.data.transactions.TransactionsRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class TransactionsModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            return TransactionsViewModel(transactionsRepoository = TransactionsRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}