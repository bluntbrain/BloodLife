package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

public class FragmentMakeRequests extends Fragment {
    View view;
    private TextView btn;
    private LatLng mycoordinates;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view=inflater.inflate(R.layout.fragment_make_request,container,false);
            btn=view.findViewById(R.id.requestbloodnowbutton);
        Bundle bundle = getActivity().getIntent().getParcelableExtra("bundle");
        mycoordinates = bundle.getParcelable("userlocation");
          //  mycoordinates=getActivity().getIntent().getExtras().get();

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(getActivity(),RequestBlood.class);
                    Bundle args= new Bundle();
                    args.putParcelable("userlocation",mycoordinates);
                    i.putExtra("bundle", args);
                    startActivity(i);
                }
            });
            return view;
    }



}
