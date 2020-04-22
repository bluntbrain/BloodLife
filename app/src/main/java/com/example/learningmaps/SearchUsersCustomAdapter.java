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

public class SearchUsersCustomAdapter extends RecyclerView.Adapter<SearchUsersCustomAdapter.SearchUsersViewHolder> {



    public interface OnItemClickListener{

        void OnItemClick(int position);
    }
    private ArrayList<AddingItemsSearchUsers> mlist;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    public static class SearchUsersViewHolder extends RecyclerView.ViewHolder{

        public TextView user_name;
        public CircleImageView user_dp;
        public TextView user_bloodtype;
        public TextView user_email;

        public SearchUsersViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            user_name=itemView.findViewById(R.id.user_names);
            user_dp=itemView.findViewById(R.id.search_users_dp);
            user_bloodtype=itemView.findViewById(R.id.user_bloodtypes_search);
            user_email=itemView.findViewById(R.id.search_users_email);

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
    public SearchUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_users_item,parent,false);
        SearchUsersViewHolder searchUsersViewHolder=new SearchUsersViewHolder(v,mListener);
        return searchUsersViewHolder;
    }

    public SearchUsersCustomAdapter(ArrayList<AddingItemsSearchUsers> list) {

        mlist=list;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchUsersViewHolder holder, int position) {
        AddingItemsSearchUsers currentItem=mlist.get(position);

        holder.user_email.setText(currentItem.getSearch_user_email());
        holder.user_name.setText(currentItem.getSearch_user_name());
        holder.user_bloodtype.setText(currentItem.getSearch_user_bloodtype());
        if(currentItem.getSearch_user_imgID().equals("Default"))
        {

        }else{
            Picasso.get().load(currentItem.getSearch_user_imgID()).into(holder.user_dp);
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}



