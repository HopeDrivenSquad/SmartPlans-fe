package eu.profinit.pml.ui.newplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.profinit.pml.data.dashboard.DashboardRepository
import eu.profinit.pml.data.login.LoginDataSource
import eu.profinit.pml.data.login.LoginRepository
import eu.profinit.pml.data.newplan.NewPlanRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class NewPlanModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewPlanViewModel::class.java)) {
            return NewPlanViewModel(newPlanRepository = NewPlanRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}