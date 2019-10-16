package com.example.retrofitmvvmsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitmvvmsample.databinding.UsersListItemBinding;
import com.example.retrofitmvvmsample.modelClass.Datum;
import com.example.retrofitmvvmsample.utils.Utlity;
import com.example.retrofitmvvmsample.viewHolder.UsersViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<Datum> mListItems = new ArrayList<>();
    private final Context mContext;

    //constructor
    public UserAdapter(Context context) { // constructor
        this.mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UsersListItemBinding binding = UsersListItemBinding
                .inflate(inflater, parent, false);
        return new UsersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UsersViewHolder userViewHolder;

        if (holder instanceof UsersViewHolder) {
            userViewHolder = (UsersViewHolder) holder;
            setUserViewHolder(userViewHolder, position);
        }
    }

    private void setUserViewHolder(UsersViewHolder userViewHolder, int position) {
        Datum user = mListItems.get(position);
        if (user != null) {
            //TODO : move it to data, bind it inside viewholder
            userViewHolder.getBinding().emailTv.setText(user.getEmail());
            userViewHolder.getBinding().nameTv.setText(user.getFirstName() + "" + user.getLastName());
            Utlity.loadImageUsingGlide(mContext,
                    userViewHolder.getBinding().userImageIv,
                    user.getAvatar());
        }
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    //-------------------------------------Manipulating RecyclerView--------------------------------//
    public void clearData() {
        if (!mListItems.isEmpty()) {
            mListItems.clear();
            notifyDataSetChanged();
        }
    }

    public void addItemRange(List<Datum> items) {
        if (items != null) {
            int position = mListItems.size();
            mListItems.addAll(position, items);
            notifyItemRangeInserted(position, items.size());
        }
    }
}
