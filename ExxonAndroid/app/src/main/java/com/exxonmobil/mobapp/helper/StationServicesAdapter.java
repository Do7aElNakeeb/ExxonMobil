package com.exxonmobil.mobapp.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.exxonmobil.mobapp.R;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 11/8/16.
 */

public class StationServicesAdapter extends RecyclerView.Adapter<StationServicesAdapter.ViewHolder> {

    private Context context;
    private ArrayList <Integer> arrayList;

    public StationServicesAdapter(Context context, ArrayList<Integer> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup viewGroup = (ViewGroup) mInflater.inflate(R.layout.service_item, parent, false);



        return new ViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.serviceImageView.setImageResource(arrayList.get(holder.getAdapterPosition()));
//        holder.serviceImageView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 75));
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView serviceImageView;

        ViewHolder(View itemView) {
            super(itemView);

            serviceImageView = (ImageView) itemView.findViewById(R.id.service_item);
        }
    }
}
