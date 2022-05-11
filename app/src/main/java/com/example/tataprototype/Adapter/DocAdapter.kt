package com.example.tataprototype.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tataprototype.Model.DocumentViewModel
import com.example.tataprototype.Network.Document
import com.example.tataprototype.R
import java.util.*

class DocAdapter (private val items: MutableList<Document>, private val viewModel: DocumentViewModel, private val context: Context, private val colorIndex: Int)
    : RecyclerView.Adapter<DocAdapter.DocHolder>(), Filterable
{
    private val itemsFilter: MutableList<Document> by lazy {
        items.toMutableList()
    }

    inner class DocHolder(val view: View, private val viewModel: DocumentViewModel, private val colorIndex: Int, private val context: Context)
        : RecyclerView.ViewHolder(view)
    {
        fun paint(item: Document)
        {
            val clickArea = view.findViewById<ConstraintLayout>(R.id.click_area)

            //Change button text to doc.name
            view.findViewById<TextView>(R.id.doc_name).text = item.docName
            view.findViewById<ConstraintLayout>(R.id.click_area).setOnClickListener {
                viewModel.url.value = itemsFilter[adapterPosition].URL
                viewModel.docName.value = itemsFilter[adapterPosition].docName
            }

            when(colorIndex)
            {
                0 -> clickArea.background = ContextCompat.getDrawable(context, R.drawable.round_corners_blue)
                1 -> clickArea.background = ContextCompat.getDrawable(context, R.drawable.round_corners_orange)
                2 -> clickArea.background = ContextCompat.getDrawable(context, R.drawable.round_corners_pink)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocHolder
    {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_element, parent, false)

        return DocHolder(adapterLayout, viewModel, colorIndex, context)
    }

    override fun onBindViewHolder(holder: DocHolder, position: Int)
    {
        holder.paint(itemsFilter[position])
    }

    override fun getItemCount(): Int = itemsFilter.size

    override fun getFilter(): Filter
    {
        return object: Filter() {

            override fun performFiltering(p0: CharSequence?): FilterResults
            {
                val charSearch = p0.toString()

                if(charSearch.isEmpty())
                {
                    itemsFilter.clear()
                    itemsFilter.addAll(items)
                }
                else
                {
                    itemsFilter.clear()

                    for(item in items)
                    {
                        if(item.docName.lowercase(Locale.getDefault()).contains(charSearch.lowercase(Locale.getDefault())))
                        {
                            itemsFilter.add(item)
                        }
                    }
                }

                val filterResults = FilterResults()

                filterResults.values = itemsFilter

                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, results: FilterResults?)
            {
                notifyDataSetChanged()
            }

        }
    }
}