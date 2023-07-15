package com.assignment.openinappdashboard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.openinappdashboard.R
import com.assignment.openinappdashboard.model.LinkModelForAdapterItemUI
import com.assignment.openinappdashboard.databinding.LinkDataItemViewBinding


class LinkDataItemAdapter:RecyclerView.Adapter<LinkDataItemAdapter.LinksViewModel> {
    var linksListItem: ArrayList<LinkModelForAdapterItemUI> = ArrayList()

    constructor(listItem: ArrayList<LinkModelForAdapterItemUI>){
        linksListItem.addAll(listItem)
    }
    class LinksViewModel(var binding: LinkDataItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksViewModel {
        return LinksViewModel(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.link_data_item_view,parent,false))
    }

    override fun onBindViewHolder(holder: LinksViewModel, position: Int) {
        val data= linksListItem[position]
        holder.binding.data=data

    }

    override fun getItemCount(): Int {
        return linksListItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRecyclerView(linkItemListForAdapter: ArrayList<LinkModelForAdapterItemUI>) {
        linksListItem.clear()
        linksListItem.addAll(linkItemListForAdapter)
        notifyDataSetChanged()
    }

}