package eu.profinit.pml.ui.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.profinit.pml.data.common.Transaction
import eu.profinit.pml.data.common.TransactionCategory
import eu.profinit.pml.data.common.TransactionSummary
import eu.profinit.pml.data.transactions.TransactionsRepository
import eu.profinit.pml.session.UserSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TransactionsViewModel(private val transactionsRepoository: TransactionsRepository) : ViewModel() {

    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val loading = MutableLiveData(false)
    var summary = MutableLiveData<TransactionSummary>()
    var categories = MutableLiveData<Collection<TransactionCategory>>()


    fun loadTransactions() {
        loading.postValue(true)
        uiScope.launch(coroutineContext) {
            val transactionModel = transactionsRepoository.loadTransactions(UserSession.clientId)

            summary.postValue(transactionModel?.summary)

            val _categories = arrayListOf<TransactionCategory>()
            _categories.addAll(transactionModel?.categories ?: emptyList())

            _categories.addAll(_categories)
            categories.postValue(_categories)

            loading.postValue(false)
        }
    }

}