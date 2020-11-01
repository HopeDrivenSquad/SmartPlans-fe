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

            val trns = arrayListOf<Transaction>()
            trns.add(Transaction(1, "2020-12-12", -100, "Hello"))
            trns.add(Transaction(2, "2020-12-12", -100, "Hello"))
            trns.add(Transaction(3, "2020-12-12", -100, "Hello"))
            trns.add(Transaction(4, "2020-12-12", -100, "Hello"))

            _categories.add(TransactionCategory("TEST", 1000, trns))
            _categories.addAll(transactionModel?.categories ?: emptyList())
            categories.postValue(_categories)

            loading.postValue(false)
        }
    }

}