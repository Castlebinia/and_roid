package com.showgather.sungbin.showgather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.showgather.sungbin.showgather.model.ResModel;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView name;
        TextView address;
        TextView diff;
        TextView phone;

        MyViewHolder(View view){
            super(view);
            picture = view.findViewById(R.id.res_picture);
            name = view.findViewById(R.id.res_name);
            address = view.findViewById(R.id.res_address);
            diff = view.findViewById(R.id.res_diff);
            phone = view.findViewById(R.id.res_phone);
        }
    }
    private ArrayList<ResModel> resInfoArrayList;
    MyAdapter(ArrayList<ResModel> resInfoArrayList){
        this.resInfoArrayList = resInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.name.setText(resInfoArrayList.get(position).name);
        myViewHolder.address.setText(resInfoArrayList.get(position).address);
        myViewHolder.diff.setText(resInfoArrayList.get(position).diff);
        myViewHolder.phone.setText(resInfoArrayList.get(position).phone);
        Glide.with(holder.itemView.getContext())
                .load(resInfoArrayList.get(position).image_url)
                .into(myViewHolder.picture);

    }
    @Override
    public int getItemCount() {
        return resInfoArrayList.size();
    }
}

