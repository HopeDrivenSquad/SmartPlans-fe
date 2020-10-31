package eu.profinit.pml.data.common

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Plan(val id: Int? = -99) : Parcelable {
    var name: String = ""
    @Transient
    var percentages: Int = 0
    var amount: Int = 0
    var dateTo: String = ""

    @Transient
    var isOk: Boolean = false
    @Transient
    var enabled: Boolean = true

    @Transient
    var currentBalance: Int = 0
    @Transient
    var monthlySavedPerMonth: Int = 0
    @Transient
    var monthlyPlans: Int = 0
    @Transient
    var emergencyBalance: Int = 0
}

@Parcelize
data class PlanModel(val summary: Summary, val plans: @RawValue Collection<Plan>): Parcelable
@Parcelize
data class Summary(val savedAmountPerMonth: Int, val planAmountPerMonth: Int, val totalAmountPerMonth: Int, val emergencyBalance: Int): Parcelable