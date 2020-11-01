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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import eu.profinit.pml.R
import eu.profinit.pml.data.common.Plan
import eu.profinit.pml.data.common.Transaction
import eu.profinit.pml.data.common.TransactionCategory
import kotlinx.android.synthetic.main.card_category.view.*
import kotlinx.android.synthetic.main.card_dashboard_overview.view.*
import kotlinx.android.synthetic.main.card_dashboard_plan.view.*
import kotlinx.android.synthetic.main.card_transaction.view.*
import java.text.DecimalFormat
import java.util.*


class SubTransactionAdapter() : RecyclerView.Adapter<SubCommonHolder>() {

    private var list: ArrayList<Transaction> = arrayListOf()

    fun addData(newData: Collection<Transaction>) {
        list.addAll(newData)
    }

    fun setData(data: Collection<Transaction>) {
        list = ArrayList(data)
    }

    fun clearData() {
        list.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCommonHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SubTransactionItemViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: SubCommonHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

abstract class SubCommonHolder(inflater: LayoutInflater, parent: ViewGroup, layoutRes: Int) :
    RecyclerView.ViewHolder(inflater.inflate(layoutRes, parent, false)) {
    abstract fun bind(transaction: Transaction)
}

class SubTransactionItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : SubCommonHolder(inflater, parent, R.layout.card_transaction) {

    @SuppressLint("SetTextI18n")
    override fun bind(transaction: Transaction) {

        itemView.transactionTitle.text = transaction.merchantCategory
        itemView.transactionAmount.text = String.format("%s CZK" ,DecimalFormat("#,###").format(transaction.amount))
        itemView.transactionDate.text = transaction.transactionDate
    }
}