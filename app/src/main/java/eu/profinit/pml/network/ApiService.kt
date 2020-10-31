package eu.profinit.pml.network

import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.data.common.PlanModel
import eu.profinit.pml.data.common.Transaction
import retrofit2.http.*

abstract interface ApiService {

    @GET("plans")
    suspend fun getPlans(
        @Query("clientId") clientId: String?,
        @Query("currentBalance") balance: Int?
    ): PlanModel

    @POST("plans")
    suspend fun addNewPlan(
        @Body plan: Plan
    )

    @DELETE("plans/{id}")
    suspend fun deletePlan(
        @Path("id") id: Int
    )

    @POST("plans")
    suspend fun enablePlan(
        @Path("id") id: Int
    )

    @POST("plans/{id}")
    suspend fun disablePlan(
        @Path("id") id: Int
    )

    @GET("transactions")
    suspend fun getTransactions(
        @Query("clientId") deviceId: String?
    ): Collection<Transaction>
}