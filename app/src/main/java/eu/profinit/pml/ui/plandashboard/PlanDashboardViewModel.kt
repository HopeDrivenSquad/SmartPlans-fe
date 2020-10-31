package eu.profinit.pml.ui.plandashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.data.dashboard.DashboardRepository
import eu.profinit.pml.session.UserSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PlanDashboardViewModel(private val dashboardRepository: DashboardRepository) : ViewModel() {
    companion object {
        private val DEFAULT_VALUE = 100_000
    }

    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val loading = MutableLiveData(false)
    val myResult = MutableLiveData<Collection<Plan>>()
    var currentBalance = MutableLiveData(DEFAULT_VALUE)

    fun loadMyPlans() {
        loading.value = true
        uiScope.launch(coroutineContext) {
            val response = dashboardRepository.getPlans(
                UserSession.clientId,
                currentBalance.value ?: DEFAULT_VALUE
            )
            var returnList = arrayListOf<Plan>()

            val overview = Plan(id = -100)
            overview.currentBalance = currentBalance.value ?: DEFAULT_VALUE
            overview.monthlySavedPerMonth = response?.summary?.savedAmountPerMonth ?: 0
            overview.monthlyPlans = response?.summary?.planAmountPerMonth ?: 0

            returnList.add(overview)
            returnList.add(Plan()) // SEPARATOR
            returnList.addAll(response?.plans ?: emptyList())

            val mockPlan = Plan(1)
            mockPlan.dateTo = "20-12-2020"
            mockPlan.percentages = 33
            mockPlan.amount = 10_000
            mockPlan.name = "Vanoce za rohem"
            mockPlan.enabled = true
            mockPlan.isOk = true

            returnList.add(mockPlan)

            loading.postValue(false)
            myResult.postValue(returnList)
        }
    }

    fun enablePlan() {
    }

    fun disablePlan() {
    }
}