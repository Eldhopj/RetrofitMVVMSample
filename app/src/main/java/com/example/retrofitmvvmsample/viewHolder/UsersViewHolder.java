package com.example.retrofitmvvmsample.viewHolder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitmvvmsample.databinding.UsersListItemBinding;
import com.example.retrofitmvvmsample.modelClass.Datum;

public class UsersViewHolder extends RecyclerView.ViewHolder {
    private UsersListItemBinding binding;

    public UsersViewHolder(UsersListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public UsersListItemBinding getBinding() {
        return binding;
    }

    public void bind(Datum user) {
        binding.setUserData(user);
        binding.executePendingBindings();//is important in order to execute the data binding immediately. Otherwise it can populate incorrect view.
    }
}
