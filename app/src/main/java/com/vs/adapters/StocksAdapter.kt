package com.vs.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.vs.R
import com.vs.models.Datum
import com.vs.utils.C
import com.vs.views.activities.HomeActivity
import com.vs.views.fragments.HistoryFragment

import kotlinx.android.synthetic.main.layout_note_item.view.*
import java.util.ArrayList


/**
 *  Created by Sachin
 */
class StocksAdapter(private val context:Context) : androidx.recyclerview.widget.RecyclerView.Adapter<StocksAdapter.ViewHolder>() {
    private var data: List<Datum> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindToView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(data: List<Datum>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val tvNoteTitle = itemView.tvSidTitle!!
        private val tvNoteDesc = itemView.tvNoteDesc!!
        private val cvStockItemContainer = itemView.cvStockItemContainer!!
        private val ivStatus = itemView.ivStatus!!

        fun bindToView(data: Datum) {
            tvNoteTitle.text = data.sid
            tvNoteDesc.text = data.price.toString()
            if(data.change>0){
                ivStatus.setImageDrawable(
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_up))
            }else{
                ivStatus.setImageDrawable(
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_down))
            }

            cvStockItemContainer.setOnClickListener {
                goToStockDetailsScreen(data)
            }
        }
    }

    private fun goToStockDetailsScreen(data: Datum) {
        val historyFragment = HistoryFragment()
        val bundle = Bundle()
        bundle.putSerializable(C.STOCK_DATA, data)
        historyFragment.arguments = bundle

        (context as HomeActivity).also {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.rlContainer, historyFragment, C.STOCK_DATA).addToBackStack("HistoryFragment")
                .commitAllowingStateLoss()
        }
    }
}
