package com.drafe.yogatrainingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.drafe.yogatrainingapp.databinding.HistoryListFragmentBinding
import com.drafe.yogatrainingapp.databinding.HistoryListItemBinding
import java.util.UUID


class HistoryListAdapter(
    private val historyList: List<TrainHistory>,
    private val onHistoryClicked: (historyId: UUID) -> Unit
    ): RecyclerView.Adapter<HistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryListItemBinding.inflate(inflater, parent, false)
        return HistoryHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history, onHistoryClicked)
    }

    override fun getItemCount() = historyList.size
}

class HistoryHolder(
    private val binding : HistoryListItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(history: TrainHistory, onHistoryClicked: (historyId: UUID) -> Unit) {
        binding.asanName.text = history.asanName
        binding.trainDate.text = history.date.toString()
        binding.trainScore.text = history.score.toString()

        binding.root.setOnClickListener{
            onHistoryClicked(history.id)
        }
    }
}



