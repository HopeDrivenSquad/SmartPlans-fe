package eu.profinit.pml.data.common

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Plan(val id: Int = -99) : Parcelable {
    var name: String = ""
    var percentages: Int = 0
    var amount: Int = 0
    var dateTo: String = ""
    var isOk: Boolean = false

    @Transient
    var currentBalance: Int = 0
    @Transient
    var monthlyPlusOptimisation: Int = 0
    @Transient
    var monthlyMinusPlans: Int = 0
}

@Parcelize
data class PlanModel(val summary: Summary, val plans: ArrayList<Plan>): Parcelable
@Parcelize
data class Summary(val savedAmountPerMonth: Int, val planAmountPerMonth: Int, val totalAmountPerMonth: Int): Parcelable