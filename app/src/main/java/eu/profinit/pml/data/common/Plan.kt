package eu.profinit.pml.data.common

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Plan(val id: Int? = -99) : Parcelable {
    var name: String = ""
    var percentages: Int? = 0
    var amount: Int = 0
    var dateTo: String = ""

    var isOk: Boolean? = false
    var enabled: Boolean? = true

    @Transient
    var currentBalance: Int = 0
    @Transient
    var monthlySavedPerMonth: Float = 0f
    @Transient
    var monthlyPlans: Float = 0f
    @Transient
    var emergencyBalance: Float = 0f
}

@Parcelize
data class PlanModel(val summary: Summary, val plans: @RawValue Collection<Plan>): Parcelable
@Parcelize
data class Summary(val amountSavedPerMonth: Float?, val amountPlanPerMonth: Float?, val amountTotalPerMonth: Float?, val emergencyBalance: Float?): Parcelable