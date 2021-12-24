package com.example.fusiontestapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fusiontestapp.databinding.EntriesAdapterItemBinding
import com.example.fusiontestapp.model.EntriesModel

//interface to handle list item click listener
interface RecyclerItemClickListener {
    fun itemClicked(position: Int)
}

class MainRecyclerAdapter(val listener: RecyclerItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var entriesDataList: MutableList<EntriesModel> = mutableListOf()

    fun setDataList(dataList: List<EntriesModel>) {
        this.entriesDataList.clear()
        this.entriesDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    class EntriesItemViewHolder(val layoutBinding: EntriesAdapterItemBinding) :
        RecyclerView.ViewHolder(layoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding: EntriesAdapterItemBinding =
            EntriesAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntriesItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val apiTextView = (holder as EntriesItemViewHolder).layoutBinding.apiName
        val descTextView = holder.layoutBinding.desc

        val entryItem = entriesDataList[position]
        val apiName = entryItem.api
        if (apiName != null && apiName != "") {
            apiTextView.visibility = View.VISIBLE
            apiTextView.text = apiName
        } else {
            apiTextView.visibility = View.GONE
        }

        val desc = entryItem.description
        if (desc != null && desc != "") {
            descTextView.visibility = View.VISIBLE
            descTextView.text = desc
        } else {
            descTextView.visibility = View.GONE
        }

        holder.layoutBinding.container.setOnClickListener {
            listener.itemClicked(
                position = position
            )
        }

    }

    override fun getItemCount(): Int {
        return this.entriesDataList.size
    }
}