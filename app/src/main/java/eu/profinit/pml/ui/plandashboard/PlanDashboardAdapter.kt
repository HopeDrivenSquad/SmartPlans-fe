package eu.profinit.pml.ui.plandashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import eu.profinit.pml.R
import eu.profinit.pml.data.common.Plan
import kotlinx.android.synthetic.main.card_dashboard_overview.view.*
import kotlinx.android.synthetic.main.card_dashboard_plan.view.*
import java.util.*


class PlanDashboardAdapter(
    private val clickListener: (Plan) -> Unit
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
    abstract fun bind(plan: Plan, clickListener: (Plan) -> Unit)
}

class PlanOverviewItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : CommonHolder(inflater, parent, R.layout.card_dashboard_overview) {

    @SuppressLint("SetTextI18n")
    override fun bind(plan: Plan, clickListener: (Plan) -> Unit) {
        itemView.currentBalanceValue.text = String.format("%d CZK", plan.currentBalance)
        itemView.monthlyPlusSaveFromTransactionsValue.text = String.format("%d CZK", plan.monthlySavedPerMonth)
        itemView.monthlyMinusPlansValue.text = String.format("%d CZK", plan.monthlyPlans)
        itemView.balanceSlider.value = plan.currentBalance.toFloat()

        itemView.balanceSlider.clearOnChangeListeners()
        itemView.balanceSlider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->  itemView.currentBalanceValue.text = String.format("%s CZK", slider.value.toString()) })

        itemView.balanceSlider.clearOnSliderTouchListeners()
        itemView.balanceSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                plan.currentBalance = slider.value.toInt()
                clickListener(plan)
            }
        })

    }
}


class PlanSeparatorItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : CommonHolder(inflater, parent, R.layout.card_dashboard_separator) {

    @SuppressLint("SetTextI18n")
    override fun bind(plan: Plan, clickListener: (Plan) -> Unit) {
    }
}

class PlanDashboardItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : CommonHolder(inflater, parent, R.layout.card_dashboard_plan) {

    @SuppressLint("SetTextI18n")
    override fun bind(plan: Plan, clickListener: (Plan) -> Unit) {

        itemView.planTitle.text = plan.name
        itemView.planValue.text = String.format("Target: %s CZK", plan.amount.toString())
        itemView.planActual.text = String.format("Actual: %s CZK", ((plan.amount * plan.percentages) / 100).toString())
        itemView.planExpiration.text = String.format("Expiration: %s", plan.dateTo.toString())

        val color = when (plan.percentages) {
            in 0..50 -> R.color.profinit_red
            in 50..75 -> android.R.color.holo_orange_light
            in 75..100 -> android.R.color.holo_green_light
            else -> android.R.color.holo_green_light
        }
        itemView.indicator.setTint(if (plan.isOk) android.R.color.holo_green_light else R.color.profinit_red)
        itemView.planProgress.progress = plan.percentages
        itemView.planProgress.setTint(color)


        itemView.enableDisablePlan.setOnClickListener {
            clickListener(plan)
        }

        if (plan.enabled) {
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