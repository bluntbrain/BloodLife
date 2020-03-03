package com.example.learningmaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestsCustomAdapter extends RecyclerView.Adapter<RequestsCustomAdapter.RequestsViewHolder> {

    private ArrayList<AddingItemsRequests> mlist;

    public static class RequestsViewHolder extends RecyclerView.ViewHolder{

        public TextView request_name;
        public TextView request_type;
        public TextView request_units;
        public TextView request_mobile;
        public TextView request_status;
        public TextView request_location;


        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            request_location =itemView.findViewById(R.id.requests_location);
            request_mobile=itemView.findViewById(R.id.requests_mobile);
            request_name=itemView.findViewById(R.id.requests_name);
            request_units=itemView.findViewById(R.id.requests_units);
            request_status=itemView.findViewById(R.id.requests_status);
            request_type=itemView.findViewById(R.id.requests_type);

        }
    }

    @NonNull
    @Override
    public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_item,parent,false);
        RequestsViewHolder requestsViewHolder=new RequestsViewHolder(v);
        return requestsViewHolder;
    }

    public RequestsCustomAdapter(ArrayList<AddingItemsRequests> list) {

        mlist=list;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsViewHolder holder, int position) {
        AddingItemsRequests currentItem=mlist.get(position);

        holder.request_type.setText(currentItem.getType());
        holder.request_status.setText("Status : "+ currentItem.getStatus());
        holder.request_units.setText(currentItem.getUnits());
        holder.request_name.setText(currentItem.getName());
        holder.request_mobile.setText(currentItem.getMobile());
        holder.request_location.setText(currentItem.getLocation());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


}
