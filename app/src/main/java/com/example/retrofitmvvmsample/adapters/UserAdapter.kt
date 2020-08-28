package com.example.retrofitmvvmsample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.retrofitmvvmsample.databinding.UsersListItemBinding
import com.example.retrofitmvvmsample.modelClass.Datum
import com.example.retrofitmvvmsample.viewHolder.UsersViewHolder

class UserAdapter(private val mContext: Context) : ListAdapter<Datum, ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Datum>() {
        override fun areItemsTheSame(oldItem: Datum, newItem: Datum): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Datum, newItem: Datum): Boolean =
            oldItem.equals(newItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UsersListItemBinding
            .inflate(inflater, parent, false)
        return UsersViewHolder(binding, mContext)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int) { //populate the data into the list_item (View Holder), as we scroll
        (holder as? UsersViewHolder)?.bindData(getItem(position))
    }
}