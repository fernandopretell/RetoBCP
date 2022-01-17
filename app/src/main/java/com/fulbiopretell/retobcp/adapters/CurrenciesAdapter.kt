package com.fulbiopretell.retobcp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fulbiopretell.core.models.CurrencyLocalModel
import com.fulbiopretell.retobcp.R
import com.fulbiopretell.retobcp.core.extensions.loadUrl
import java.util.*

class CurrenciesAdapter(val listener: CurrenciesAdapterListener, val ctx: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<CurrenciesAdapter.CurrenciesAdapterViewHolder>() {

    private val items: ArrayList<CurrencyLocalModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesAdapterViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrenciesAdapterViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CurrenciesAdapterViewHolder, position: Int) {
        val item = items[position]

        holder.ivFlag?.loadUrl(item.flag, ctx.getDrawable(R.drawable.flag_default))
        holder.tvCountry?.text = item.name
        "${item.currency.name} (${item.currency.symbol})".also { holder.tvCountryCurrency?.text = it }

        holder.container?.setOnClickListener {
            listener.onclickItem(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<CurrencyLocalModel>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    inner class CurrenciesAdapterViewHolder(itemView: View?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        var ivFlag = itemView?.findViewById<ImageView>(R.id.ivFlag)
        var tvCountry = itemView?.findViewById<TextView>(R.id.tvCountry)
        var tvCountryCurrency = itemView?.findViewById<TextView>(R.id.tvCountryCurrency)
        var container = itemView?.findViewById<ConstraintLayout>(R.id.clContainer)
    }

    interface CurrenciesAdapterListener {
        fun onclickItem(item: CurrencyLocalModel)
    }
}