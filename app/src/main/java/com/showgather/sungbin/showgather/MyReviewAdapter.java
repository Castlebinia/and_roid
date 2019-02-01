package com.showgather.sungbin.showgather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.showgather.sungbin.showgather.model.ReviewModel;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MyReviewAdapter extends BaseAdapter {
    public ArrayList<ReviewModel> reviewModelList = new ArrayList<>();
    public MyReviewAdapter(){

    }
    @Override
    public int getCount() {
        return reviewModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recycler_view_review, parent, false);
        }
        TextView titleTextView = convertView.findViewById(R.id.review_title);
        TextView contentTextView = convertView.findViewById(R.id.review_content);
        ReviewModel reviewModel = reviewModelList.get(position);
        titleTextView.setText(reviewModel.getTitle());
        contentTextView.setText(reviewModel.getContent());
        return convertView;
    }
    public void addItem(String title, String context,String time) {
        ReviewModel item = new ReviewModel();
        item.setTitle(title);
        item.setContext(context);
        item.setTime(time);
        reviewModelList.add(item);
    }
}
