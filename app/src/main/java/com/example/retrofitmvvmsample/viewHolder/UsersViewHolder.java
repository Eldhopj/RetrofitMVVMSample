package com.example.retrofitmvvmsample.viewHolder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitmvvmsample.databinding.UsersListItemBinding;

public class UsersViewHolder extends RecyclerView.ViewHolder {
    private UsersListItemBinding binding;

    public UsersViewHolder(UsersListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public UsersListItemBinding getBinding() {
        return binding;
    }
}
