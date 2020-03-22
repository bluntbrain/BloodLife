package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMakeRequests extends Fragment implements View.OnClickListener {
    View view;
        public FragmentMakeRequests()
        {

        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view=inflater.inflate(R.layout.fragment_make_request,container,false);
            return view;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.requestbloodnowbutton)
        {
            Intent i =new Intent(getActivity(),RequestBlood.class);
            startActivity(i);

        }else
        {
            return;
        }
    }


}
