package eu.profinit.pml.ui.newplan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.data.newplan.NewPlanRepository
import eu.profinit.pml.session.UserSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewPlanViewModel(private val newPlanRepository: NewPlanRepository) : ViewModel() {
    companion object {
        private val DEFAULT_VALUE = 100_000
    }

    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val plan: Plan? = Plan(id = null)
    val loading = MutableLiveData(false)
    val done = MutableLiveData<Boolean>()

    fun createNewPlan() {
        loading.value = true

        uiScope.launch(coroutineContext) {
            val success = newPlanRepository.createNewPlan(UserSession.clientId, plan)
            loading.postValue(false)
            done.postValue(true)
        }
    }
}