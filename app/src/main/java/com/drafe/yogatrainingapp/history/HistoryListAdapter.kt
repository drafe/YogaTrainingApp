package com.drafe.yogatrainingapp.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drafe.yogatrainingapp.TrainHistoryWithAsana
import com.drafe.yogatrainingapp.databinding.HistoryListItemBinding
import java.util.UUID


class HistoryListAdapter(
    private val historyList: List<TrainHistoryWithAsana>,
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
    fun bind(history: TrainHistoryWithAsana, onHistoryClicked: (historyId: UUID) -> Unit) {
        binding.asanName.text = history.asanaName
        binding.trainDate.text = history.date.toString()
        binding.trainScore.text = history.score.toString()

        binding.root.setOnClickListener{
            onHistoryClicked(history.id)
        }
    }
}



