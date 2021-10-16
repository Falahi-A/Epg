package com.dazn.codeassignment.epg.ui.main.schedules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dazn.codeassignment.epg.databinding.ItemEpgBinding
import com.dazn.codeassignment.epg.domain.model.Schedule
import com.dazn.codeassignment.epg.ui.base.BaseViewHolder
import com.dazn.codeassignment.epg.utils.loadImage

class SchedulesListAdapter :
    ListAdapter<Schedule, SchedulesListAdapter.SchedulesViewHolder>(DifCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedulesViewHolder {
        return SchedulesViewHolder(
            ItemEpgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SchedulesViewHolder, position: Int) {

        holder.onBind(getItem(position))
    }


    inner class SchedulesViewHolder(private val itemBinding: ItemEpgBinding) :
        BaseViewHolder<Schedule>(itemBinding.root) {
        override fun onBind(obj: Schedule) {

            itemBinding.textEpgTitleItem.text = obj.title
            itemBinding.textEpgSubTitleItem.text = obj.subtitle
            itemBinding.textEpgDateItem.text = obj.date
            loadImage(obj.imageUrl, itemBinding.imgEpgItem)

        }

    }

    object DifCallback : DiffUtil.ItemCallback<Schedule>() {
        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem == newItem
        }

    }


}

