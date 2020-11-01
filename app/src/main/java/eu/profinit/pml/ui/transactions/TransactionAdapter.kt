package eu.profinit.pml.ui.transactions

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import eu.profinit.pml.R
import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.data.common.TransactionCategory
import kotlinx.android.synthetic.main.card_category.view.*
import kotlinx.android.synthetic.main.card_dashboard_overview.view.*
import kotlinx.android.synthetic.main.card_dashboard_plan.view.*
import java.text.DecimalFormat
import java.util.*


class TransactionAdapter(
    private val clickListener: (TransactionCategory, Int) -> Unit
) : RecyclerView.Adapter<CommonHolder>() {

    private var list: ArrayList<TransactionCategory> = arrayListOf()

    fun addData(newData: Collection<TransactionCategory>) {
        list.addAll(newData)
    }

    fun setData(data: Collection<TransactionCategory>) {
        list = ArrayList(data)
    }

    fun clearData() {
        list.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TransactionItemViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CommonHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    override fun getItemCount(): Int = list.size
}

abstract class CommonHolder(inflater: LayoutInflater, parent: ViewGroup, layoutRes: Int) :
    RecyclerView.ViewHolder(inflater.inflate(layoutRes, parent, false)) {
    abstract fun bind(transaction: TransactionCategory, clickListener: (TransactionCategory, Int) -> Unit)
}

class TransactionItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : CommonHolder(inflater, parent, R.layout.card_category) {

    @SuppressLint("SetTextI18n")
    override fun bind(transaction: TransactionCategory, clickListener: (TransactionCategory, Int) -> Unit) {

        itemView.categoryTitle.text = transaction.name
        itemView.categorySavingsValue.text = String.format("%s CZK" , DecimalFormat("#,###").format(transaction.amountSavedPerMonth))

        itemView.expander.setOnClickListener {
            itemView.transactions.adapter = SubTransactionAdapter()
            itemView.transactions.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            (itemView.transactions.adapter as SubTransactionAdapter).setData(transaction.transactions)
            (itemView.transactions.adapter as SubTransactionAdapter).notifyDataSetChanged()
            itemView.transactions.visibility = if (itemView.transactions.visibility == View.VISIBLE) View.GONE else View.VISIBLE
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

fun AppCompatTextView.setColor(@ColorRes colorRes: Int) {
    this.setTextColor(ContextCompat.getColor(context, colorRes))
}