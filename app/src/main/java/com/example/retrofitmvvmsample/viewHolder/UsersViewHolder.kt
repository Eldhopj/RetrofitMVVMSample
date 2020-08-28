package com.example.retrofitmvvmsample.viewHolder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.retrofitmvvmsample.databinding.UsersListItemBinding
import com.example.retrofitmvvmsample.modelClass.Datum
import com.example.retrofitmvvmsample.utils.Utility

class UsersViewHolder(val binding: UsersListItemBinding, private val context: Context) : ViewHolder(binding.root) {

    //Binding of data happens in here
    fun bindData(item: Datum) {
        val position = adapterPosition
        if (position == RecyclerView.NO_POSITION) {
            return
        }
        with(item) {
            binding.userData = this
            binding.executePendingBindings() //is important in order to execute the data binding immediately. Otherwise it can populate incorrect view.
            Utility.loadImageUsingGlide(context,
                binding.userImageIv,
                avatar)
        }
    }
}