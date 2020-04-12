package com.example.learningmaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestsCustomAdapter extends RecyclerView.Adapter<RequestsCustomAdapter.RequestsViewHolder> {

    private OnItemClickListener mListener;
    public interface OnItemClickListener{

        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }



    private ArrayList<AddingItemsRequests> mlist;

    public static class RequestsViewHolder extends RecyclerView.ViewHolder{

        public TextView request_name;
        public TextView request_type;
        public TextView request_units;
        public TextView request_mobile;
        public TextView request_status;
        public TextView alldataofitme;
        public TextView request_location;


        public RequestsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
           //request_location =itemView.findViewById(R.id.requests_location);
           // request_mobile=itemView.findViewById(R.id.requests_mobile);
            request_name=itemView.findViewById(R.id.requests_name);
            request_units=itemView.findViewById(R.id.requests_units);
            request_status=itemView.findViewById(R.id.requests_status);
            request_type=itemView.findViewById(R.id.requests_type);
            alldataofitme=itemView.findViewById(R.id.dataofitem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_item,parent,false);
        RequestsViewHolder requestsViewHolder=new RequestsViewHolder(v,mListener);
        return requestsViewHolder;
    }

    public RequestsCustomAdapter(ArrayList<AddingItemsRequests> list) {

        mlist=list;
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestsViewHolder holder, int position) {
        AddingItemsRequests currentItem=mlist.get(position);

        holder.request_type.setText(currentItem.getType());
        holder.request_status.setText("Status : "+ currentItem.getStatus());
        holder.request_units.setText("Units Required: "+currentItem.getUnits());
        holder.request_name.setText(currentItem.getName());
        //holder.request_mobile.setText(currentItem.getMobile());
       // holder.request_location.setText(currentItem.getLocation());
        holder.alldataofitme.setText(currentItem.getName()+"-"+currentItem.getUnits()+"-"
        +currentItem.getType()+"-"+currentItem.getStatus()+"-"+"Male"+"-"+currentItem.getLocation()+"-"
                +currentItem.getMobile()
        );

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


}
