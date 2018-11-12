package com.showgather.sungbin.showgather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.showgather.sungbin.showgather.model.ResModel;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public ArrayList<ResModel> resInfoArrayList;
    public Context context;
    MyAdapter(ArrayList<ResModel> resInfoArrayList){
        this.resInfoArrayList = resInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        context=parent.getContext();
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReservationActivity.class);
                intent.putExtra("res_lon",Double.parseDouble(resInfoArrayList.get(position).lon));
                intent.putExtra("res_lat",Double.parseDouble(resInfoArrayList.get(position).lat));
                intent.putExtra("res_name",resInfoArrayList.get(position).name);
                context.startActivity(intent);

            }
        });
        myViewHolder.name.setText(resInfoArrayList.get(position).name);
        myViewHolder.address.setText(resInfoArrayList.get(position).address);
        myViewHolder.diff.setText(resInfoArrayList.get(position).diff);
        myViewHolder.phone.setText(resInfoArrayList.get(position).phone);
        Glide.with(holder.itemView.getContext())
                .load(resInfoArrayList.get(position).image_url)
                .into(myViewHolder.picture);

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView picture;
        TextView name;
        TextView address;
        TextView diff;
        TextView phone;

        MyViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            picture = view.findViewById(R.id.res_picture);
            name = view.findViewById(R.id.res_name);
            address = view.findViewById(R.id.res_address);
            diff = view.findViewById(R.id.res_diff);
            phone = view.findViewById(R.id.res_phone);

        }

        @Override
        public void onClick(View v) {
            System.out.println(getOldPosition());
            Intent intent = new Intent(v.getContext() ,ReservationActivity.class);
            v.getContext().startActivity(intent);
        }
    }

    public int getItemCount() {
        return resInfoArrayList.size();
    }

}

