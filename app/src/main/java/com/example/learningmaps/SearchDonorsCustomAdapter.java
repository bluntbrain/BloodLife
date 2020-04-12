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

public class SearchDonorsCustomAdapter extends RecyclerView.Adapter<SearchDonorsCustomAdapter.SearchDonorsViewHolder> {


    public interface OnItemClickListener{

        void OnItemClick(int position);
    }
    private ArrayList<AddingItemsSearchDonors> mlist;

    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    public static class SearchDonorsViewHolder extends RecyclerView.ViewHolder{

        public TextView donor_name;
        public CircleImageView donor_dp;
        public TextView donor_id;


        public SearchDonorsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            donor_dp=itemView.findViewById(R.id.searchdonorsdp);
            donor_id=itemView.findViewById(R.id.searchdonor_id);
            donor_name=itemView.findViewById(R.id.searchdonorsname);

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
    public SearchDonorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.searchdonors_item,parent,false);
        SearchDonorsViewHolder searchDonorsViewHolder=new SearchDonorsViewHolder(v,mListener);
        return searchDonorsViewHolder;
    }

    public SearchDonorsCustomAdapter(ArrayList<AddingItemsSearchDonors> list) {

        mlist=list;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchDonorsViewHolder holder, int position) {
        AddingItemsSearchDonors currentItem=mlist.get(position);

        holder.donor_name.setText(currentItem.getSearchname());
        if(currentItem.getSearchimgURL().equals("Default"))
        {

        }else{
            Picasso.get().load(currentItem.getSearchimgURL()).into(holder.donor_dp);
        }
        holder.donor_id.setText(currentItem.getSearchid());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}



