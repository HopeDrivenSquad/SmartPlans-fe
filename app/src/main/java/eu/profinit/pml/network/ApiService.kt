package eu.profinit.pml.network

import eu.profinit.pml.data.common.*
import retrofit2.Response
import retrofit2.http.*

abstract interface ApiService {

    @GET("plans")
    suspend fun getPlans(
        @Query("clientId") clientId: String?,
        @Query("currentBalance") balance: Int?
    ): PlanModel?

    @POST("plans")
    suspend fun addNewPlan(
        @Query("clientId") clientId: String?,
        @Body plan: Plan?
    )

    @DELETE("plans/{id}")
    suspend fun deletePlan(
        @Path("id") id: Int
    )

    @PUT("plans/{id}")
    suspend fun enablePlan(
        @Path("id") id: Int,
        @Body body: EnableDisable = EnableDisable(true)
    )

    @PUT("plans/{id}")
    suspend fun disablePlan(
        @Path("id") id: Int,
        @Body body: EnableDisable = EnableDisable(false)
    )

    @GET("transactions")
    suspend fun getTransactions(
        @Query("clientId") clientId: String?
    ): TransactionModel?
}