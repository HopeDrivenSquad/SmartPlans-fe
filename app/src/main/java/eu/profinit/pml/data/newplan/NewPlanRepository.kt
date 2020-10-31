package eu.profinit.pml.data.newplan

import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.data.common.PlanModel
import eu.profinit.pml.network.ApiFactory
import eu.profinit.pml.network.ApiService
import retrofit2.HttpException

open class NewPlanRepository {
    var client: ApiService = ApiFactory.API_SERVICE

    suspend fun createNewPlan(clientId: String?, newPlan: Plan?): Boolean {
        try {
            client.addNewPlan(clientId, newPlan)
            return true
        } catch (ex: Exception) {
            when (ex) {
                is HttpException -> return false
            }
        }
        return false
    }
}