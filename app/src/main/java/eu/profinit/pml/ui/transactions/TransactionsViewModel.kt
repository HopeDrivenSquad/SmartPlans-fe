package eu.profinit.pml.ui.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.data.newplan.NewPlanRepository
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

}