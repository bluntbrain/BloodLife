package com.example.learningmaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OldRequestCustomAdapter extends RecyclerView.Adapter<OldRequestCustomAdapter.OldRequestViewHolder> {

    private OnItemClickListener mListener;

    public interface OnItemClickListener{

        void OnItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;

    }
    private ArrayList<AddingItemsOldRequests> mlist;

    public static class OldRequestViewHolder extends RecyclerView.ViewHolder{
        public TextView request_name;
        public TextView request_type;
        public TextView request_units;
        public TextView request_mobile;
        public TextView request_status;
        public TextView alldataofitme;
        public TextView request_location;



        public OldRequestViewHolder(@NonNull View itemView, final OnItemClickListener listener){
            super(itemView);
            request_name=itemView.findViewById(R.id.nameold);
            request_units=itemView.findViewById(R.id.unitsold);
            request_type= itemView.findViewById(R.id.typeold);
            alldataofitme =itemView.findViewById(R.id.dataset);

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
    public OldRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.oldrequested_item,parent,false);
        OldRequestViewHolder oldRequestsViewHolder=new OldRequestViewHolder(v,mListener);
        return oldRequestsViewHolder;
    }

    public OldRequestCustomAdapter(ArrayList<AddingItemsOldRequests> list) {

        mlist=list;
    }

    @Override
    public void onBindViewHolder(@NonNull OldRequestViewHolder holder, int position) {
        AddingItemsOldRequests currentItem=mlist.get(position);

        holder.request_type.setText(currentItem.getType());
        holder.request_units.setText("Units Required: "+currentItem.getUnits());
        holder.request_name.setText(currentItem.getName());
        holder.alldataofitme.setText(currentItem.getName()+"/=/=/"+currentItem.getUnits()+"/=/=/"
                +currentItem.getType()+"/=/=/"+currentItem.getStatus()+"/=/=/"+currentItem.getGender()+"/=/=/"+currentItem.getLocation()+"/=/=/"
                +currentItem.getMobile()+"/=/=/"+currentItem.getRequest_id()
        );
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
