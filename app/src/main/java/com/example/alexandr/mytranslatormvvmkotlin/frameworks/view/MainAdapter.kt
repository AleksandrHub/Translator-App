package com.example.alexandr.mytranslatormvvmkotlin.frameworks.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alexandr.mytranslatormvvmkotlin.R
import com.example.alexandr.model.SearchResult
import com.example.alexandr.historyscreen.convertMeaningsToString
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.*


class MainAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<com.example.alexandr.model.SearchResult> = arrayListOf()

    fun setData(data: List<com.example.alexandr.model.SearchResult>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: com.example.alexandr.model.SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text =
                    com.example.alexandr.historyscreen.convertMeaningsToString(
                        data.meanings!!
                    )
                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: com.example.alexandr.model.SearchResult) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: com.example.alexandr.model.SearchResult)
    }
}