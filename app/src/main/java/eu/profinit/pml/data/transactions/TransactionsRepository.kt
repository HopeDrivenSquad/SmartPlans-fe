package eu.profinit.pml.data.transactions

import eu.profinit.pml.data.common.TransactionModel
import eu.profinit.pml.network.ApiFactory
import eu.profinit.pml.network.ApiService
import retrofit2.HttpException

open class TransactionsRepository {
    var client: ApiService = ApiFactory.API_SERVICE

    suspend fun loadTransactions(clientId: String?): TransactionModel? {
        try {
            return client.getTransactions(clientId)
        } catch (ex: Exception) {
            when (ex) {
                is HttpException -> return null
            }
        }
        return null
    }
}