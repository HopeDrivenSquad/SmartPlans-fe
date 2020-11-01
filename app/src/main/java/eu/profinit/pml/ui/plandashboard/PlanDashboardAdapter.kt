package eu.profinit.pml.ui.plandashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import eu.profinit.pml.R
import eu.profinit.pml.data.common.Plan
import kotlinx.android.synthetic.main.card_dashboard_overview.view.*
import kotlinx.android.synthetic.main.card_dashboard_plan.view.*
import java.text.DecimalFormat
import java.util.*
import kotlin.math.min


class PlanDashboardAdapter(
    private val clickListener: (Plan, Int) -> Unit
) : RecyclerView.Adapter<CommonHolder>() {

    private var list: ArrayList<Plan> = arrayListOf()

    fun addData(newData: Collection<Plan>) {
        list.addAll(newData)
    }

    fun setData(data: Collection<Plan>) {
        list = ArrayList(data)
    }

    fun clearData() {
        list.clear()
    }

    override fun getItemViewType(position: Int): Int {
        return list.get(position).id ?: -99
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            -100 -> return PlanOverviewItemViewHolder(inflater, parent)
            -99 -> return PlanSeparatorItemViewHolder(inflater, parent)
            else -> return PlanDashboardItemViewHolder(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: CommonHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    override fun getItemCount(): Int = list.size
}

abstract class CommonHolder(inflater: LayoutInflater, parent: ViewGroup, layoutRes: Int) :
    RecyclerView.ViewHolder(inflater.inflate(layoutRes, parent, false)) {
    abstract fun bind(plan: Plan, clickListener: (Plan, Int) -> Unit)
}

class PlanOverviewItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : CommonHolder(inflater, parent, R.layout.card_dashboard_overview) {

    @SuppressLint("SetTextI18n")
    override fun bind(plan: Plan, clickListener: (Plan, Int) -> Unit) {


        itemView.currentBalanceValue.text = String.format("%s CZK" , DecimalFormat("#,###").format(plan.currentBalance))
        itemView.monthlyPlusSaveFromTransactionsValue.text = String.format("%s CZK" , DecimalFormat("#,###").format(plan.monthlySavedPerMonth))
        itemView.monthlyMinusPlansValue.text = String.format("%s CZK" , DecimalFormat("#,###").format(plan.monthlyPlans))
        itemView.balanceSlider.value = plan.currentBalance.toFloat()
        itemView.emergencyFundsValue.text = String.format("%s CZK" , DecimalFormat("#,###").format(plan.emergencyBalance))
        itemView.freeFundsValue.text = String.format("%s CZK" , DecimalFormat("#,###").format(plan.monthlySavedPerMonth - plan.monthlyPlans))

        if (plan.monthlySavedPerMonth - plan.monthlyPlans < 0) {
            itemView.freeFunds.setColor(R.color.profinit_red)
            itemView.freeFundsValue.setColor(R.color.profinit_red)
            itemView.idea.visibility = View.VISIBLE
            itemView.idea.setTint(android.R.color.holo_orange_light)
        } else {
            itemView.freeFunds.setColor(android.R.color.holo_green_light)
            itemView.freeFundsValue.setColor(android.R.color.holo_green_light)
            itemView.idea.visibility = View.VISIBLE
            itemView.idea.setTint(android.R.color.holo_green_light)
        }

        itemView.balanceSlider.clearOnChangeListeners()
        itemView.balanceSlider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->  itemView.currentBalanceValue.text = String.format("%s CZK", slider.value.toString()) })

        itemView.balanceSlider.clearOnSliderTouchListeners()
        itemView.balanceSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                plan.currentBalance = slider.value.toInt()
                clickListener(plan, 0)
            }
        })


        itemView.idea.setOnClickListener {
            clickListener(plan, 666)
        }

    }
}


class PlanSeparatorItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : CommonHolder(inflater, parent, R.layout.card_dashboard_separator) {

    @SuppressLint("SetTextI18n")
    override fun bind(plan: Plan, clickListener: (Plan, Int) -> Unit) {
    }
}

class PlanDashboardItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : CommonHolder(inflater, parent, R.layout.card_dashboard_plan) {

    @SuppressLint("SetTextI18n")
    override fun bind(plan: Plan, clickListener: (Plan, Int) -> Unit) {

        itemView.planTitle.text = plan.name
        itemView.planValue.text = String.format("Target: %s CZK", DecimalFormat("#,###").format(plan.amount))
        itemView.planActual.text = String.format("Actual: %s CZK", DecimalFormat("#,###").format(((plan.amount * (min(plan.percentages ?:0, 100))) / 100)))
        itemView.planExpiration.text = String.format("Expiration: %s", plan.dateTo)

        val color = when (plan.percentages) {
            in 0..50 -> R.color.profinit_red
            in 50..75 -> android.R.color.holo_orange_light
            in 75..100 -> android.R.color.holo_green_light
            else -> android.R.color.holo_green_light
        }
        itemView.indicator.setTint(if (plan.isOk == true) android.R.color.holo_green_light else R.color.profinit_red)
        itemView.planProgress.progress = plan.percentages ?: 0
        itemView.planProgress.setTint(color)

        itemView.enableDisablePlan.setOnClickListener {
            clickListener(plan, 0)
        }

        if (plan.enabled != false) {
            itemView.enableDisablePlan.setDrawable(R.drawable.ic_turned_on)
        } else {
            itemView.enableDisablePlan.setDrawable(R.drawable.ic_turned_off)
            itemView.indicator.setTint(R.color.disabled)
            itemView.planProgress.setTint(R.color.disabled)
        }
    }
}

fun AppCompatImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes)))
}

fun ProgressBar.setTint(@ColorRes colorRes: Int) {
    this.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes))
}

fun AppCompatImageButton.setDrawable(@DrawableRes colorDrawable: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, colorDrawable))
}

fun AppCompatImageButton.setTint(@ColorRes colorRes: Int) {
    this.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes))
}

fun AppCompatTextView.setColor(@ColorRes colorRes: Int) {
    this.setTextColor(ContextCompat.getColor(context, colorRes))
}