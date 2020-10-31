package eu.profinit.pml.ui.plandashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.profinit.pml.data.dashboard.DashboardRepository
import eu.profinit.pml.data.login.LoginDataSource
import eu.profinit.pml.data.login.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class PlanDashboardModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlanDashboardViewModel::class.java)) {
            return PlanDashboardViewModel(dashboardRepository = DashboardRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}