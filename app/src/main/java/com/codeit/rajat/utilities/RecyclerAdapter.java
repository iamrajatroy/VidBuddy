package com.codeit.rajat.utilities;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codeit.rajat.vidbuddy.ExploreActivity;
import com.codeit.rajat.vidbuddy.FeedsActivity;
import com.codeit.rajat.vidbuddy.Logout;
import com.codeit.rajat.vidbuddy.R;
import com.codeit.rajat.vidbuddy.TrendingActivity;
import com.codeit.rajat.vidbuddy.UploadActivity;

import java.util.ArrayList;

/**
 * Created by rajat on 9/11/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    ArrayList<String> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<String> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.textView.setText(arrayList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    v.getContext().startActivity(new Intent(
                            v.getContext(), FeedsActivity.class
                    ));
                }
                if (position == 1){
                    v.getContext().startActivity(new Intent(
                            v.getContext(), ExploreActivity.class
                    ));
                }
                if (position == 2){
                    v.getContext().startActivity(new Intent(
                            v.getContext(), TrendingActivity.class
                    ));
                }
                if (position == 3){
                    v.getContext().startActivity(new Intent(
                            v.getContext(), UploadActivity.class
                    ));
                }
                if(position == 7){
                    v.getContext().startActivity(new Intent(
                            v.getContext(), Logout.class
                    ));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public RecyclerViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.tx_item);

        }

    }
}
