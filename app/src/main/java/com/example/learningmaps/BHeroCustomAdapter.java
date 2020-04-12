package com.example.learningmaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class BHeroCustomAdapter extends RecyclerView.Adapter<BHeroCustomAdapter.BHeroViewHolder>{



    private ArrayList<AddingItemsBHero> mlist;

    private String[] congratslist ={"saved a life today :)","you are a saviour","new hero in town ;",
    "keep saving lives"};





    public static class BHeroViewHolder extends RecyclerView.ViewHolder{

        public TextView bhero_name;
        public CircleImageView bhero_dp;


        public BHeroViewHolder(@NonNull View itemView) {
            super(itemView);

            bhero_name=itemView.findViewById(R.id.bheroname);
            bhero_dp=itemView.findViewById(R.id.bherodp);

        }
    }

    @NonNull
    @Override
    public BHeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.bhero_item,parent,false);
        BHeroViewHolder searchDonorsViewHolder=new BHeroViewHolder(v);
        return searchDonorsViewHolder;
    }

    public BHeroCustomAdapter(ArrayList<AddingItemsBHero> list) {

        mlist=list;
    }

    @Override
    public void onBindViewHolder(@NonNull final BHeroViewHolder holder, int position) {
        AddingItemsBHero currentItem=mlist.get(position);

        Random rand = new Random();
        Integer int1= rand.nextInt(3);
        String namo = currentItem.getBheroname();
        if(namo.length()>14){
            holder.bhero_name.setText(namo.substring(0,13)+"  "+congratslist[int1]);
        }else{
            holder.bhero_name.setText(namo+"  "+congratslist[int1]);
        }
        holder.bhero_name.setText(namo+"  "+congratslist[int1]);
        if(currentItem.getBheroimageURL().equals("Default"))
        {

        }else{
            Picasso.get().load(currentItem.getBheroimageURL()).into(holder.bhero_dp);
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

