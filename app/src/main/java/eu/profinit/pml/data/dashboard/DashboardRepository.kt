package eu.profinit.pml.data.dashboard

import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.data.common.PlanModel
import eu.profinit.pml.network.ApiFactory
import eu.profinit.pml.network.ApiService
import retrofit2.HttpException

open class DashboardRepository {
    var client: ApiService = ApiFactory.API_SERVICE

    suspend fun getPlans(clientId: String?, currentBalance: Int): PlanModel? {
        try {
            return client.getPlans(clientId = clientId, balance = currentBalance)
        } catch (ex: Exception) {
            when (ex) {
                is HttpException -> return null
            }
        }
        return null
    }
}