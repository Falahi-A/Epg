package com.dazn.codeassignment.epg.ui.main.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dazn.codeassignment.epg.databinding.ItemEpgBinding
import com.dazn.codeassignment.epg.domain.model.Event
import com.dazn.codeassignment.epg.ui.base.BaseViewHolder
import com.dazn.codeassignment.epg.utils.loadImage


class EventsListAdapter(val onItemClicked: (String) -> Unit) :
    ListAdapter<Event, EventsListAdapter.EventsViewHolder>(DifCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            ItemEpgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        holder.onBind(getItem(position))
    }


    inner class EventsViewHolder(private val itemBinding: ItemEpgBinding) :
        BaseViewHolder<Event>(itemBinding.root) {
        override fun onBind(obj: Event) {

            itemBinding.textEpgTitleItem.text = obj.title
            itemBinding.textEpgSubTitleItem.text = obj.subtitle
            itemBinding.textEpgDateItem.text = obj.date
            loadImage(obj.imageUrl, itemBinding.imgEpgItem)

            itemBinding.root.setOnClickListener {
                onItemClicked(obj.videoUrl)
            }

        }

    }

    object DifCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

    }


}