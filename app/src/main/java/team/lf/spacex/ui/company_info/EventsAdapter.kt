package team.lf.spacex.ui.company_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import team.lf.spacex.databinding.ItemHistoryEventBinding
import team.lf.spacex.ui.company_info.data.HistoryEvent

class EventsAdapter :
    ListAdapter<HistoryEvent, EventsAdapter.EventsViewHolder>(EventsDiffCallBack()) {
    class EventsViewHolder private constructor(private val binding: ItemHistoryEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryEvent) {
            binding.historyEvent = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EventsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHistoryEventBinding.inflate(layoutInflater, parent, false)
                return EventsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val item: HistoryEvent = getItem(position)
        holder.bind(item)
    }
}

class EventsDiffCallBack : DiffUtil.ItemCallback<HistoryEvent>() {
    override fun areItemsTheSame(oldItem: HistoryEvent, newItem: HistoryEvent): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoryEvent, newItem: HistoryEvent): Boolean {
        return oldItem == newItem
    }
}
