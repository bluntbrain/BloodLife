package com.example.learningmaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddedDonorCustomAdapter extends RecyclerView.Adapter<AddedDonorCustomAdapter.AddedDonorViewHolder>{
    public interface OnItemClickListener{

        void OnItemClick(int position);
    }
    private ArrayList<AddingItemsAddedDonors> mlist;

    private OnItemClickListener mListener;
    public void setOnItmeClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }





    public static class AddedDonorViewHolder extends RecyclerView.ViewHolder{

        public TextView added_name;
        public CircleImageView added_dp;
        public TextView added_id;


        public AddedDonorViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            added_dp=itemView.findViewById(R.id.addeddonordp);
            added_id=itemView.findViewById(R.id.addeddonorid);
            added_name=itemView.findViewById(R.id.addeddonorname);

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
    public AddedDonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.addeddonors_item,parent,false);
        AddedDonorViewHolder searchDonorsViewHolder=new AddedDonorViewHolder(v,mListener);
        return searchDonorsViewHolder;
    }

    public AddedDonorCustomAdapter(ArrayList<AddingItemsAddedDonors> list) {

        mlist=list;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddedDonorViewHolder holder, int position) {
        AddingItemsAddedDonors currentItem=mlist.get(position);

        holder.added_name.setText(currentItem.getAddedname());
        if(currentItem.getAddedimgURL().equals("Default"))
        {
            Picasso.get().load(R.drawable.tryimage).into(holder.added_dp);

        }else{
            Picasso.get().load(currentItem.getAddedimgURL()).into(holder.added_dp);
        }

        holder.added_id.setText(currentItem.getAddedid());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
