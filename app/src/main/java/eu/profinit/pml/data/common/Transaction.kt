package eu.profinit.pml.data.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Transaction(val id: Int, val transactionDate: String, val amount: Int, val merchantCategory: String): Parcelable

@Parcelize
data class TransactionModel(val summary: TransactionSummary, val categories: @RawValue Collection<TransactionCategory>): Parcelable

@Parcelize
data class TransactionSummary(val amountSavedPerMonth: Int): Parcelable

@Parcelize
data class TransactionCategory(val name: String, val amountSavedPerMonth: Float, val transactions: @RawValue Collection<Transaction>): Parcelable
