package com.example.retrofitmvvmsample.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class Utlity {
    public static void loadImageUsingGlide(Context context, ImageView view, String url) {
        if (url != null && !url.isEmpty() && view != null && context != null) {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(view);
        }
    }

    public static void setVerticalRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, Context context, boolean nestedScroll) {
        if (recyclerView != null && context != null) {
            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setNestedScrollingEnabled(nestedScroll);
            recyclerView.setAdapter(adapter);
        }
    }
}
