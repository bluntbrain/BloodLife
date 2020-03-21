package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Request extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
   // private RippleBackground mRippleBackground;
   // private ImageView mImageView;

    BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
       // mImageView=findViewById(R.id.requests_backbutton);
       // mRippleBackground=findViewById(R.id.ripple);
       // mRippleBackground.startRippleAnimation();
        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ArrayList<AddingItemsRequests> requestsArrayList=new ArrayList<>();
        requestsArrayList =addlistdata(requestsArrayList);
       // requestsArrayList.add(new AddingItemsRequests("Lovish Badlani","O+","mandavi emerald","9413119402","Critical","14"));
     //  requestsArrayList.add(new AddingItemsRequests("Naman Sharma","AB+","block 14","9413119402","Emergency","12"));
       // requestsArrayList.add(new AddingItemsRequests("Anukriti Mehta","B+","mandavi emerald","9413119402","Critical","14"));
      //  requestsArrayList.add(new AddingItemsRequests("Lovish Badlani","O+","mandavi emerald","9413119402","Critical","14"));
      //  requestsArrayList.add(new AddingItemsRequests("Lovish Badlani","O+","mandavi emerald","9413119402","Critical","14"));
      //  requestsArrayList.add(new AddingItemsRequests("Lovish Badlani","O+","mandavi emerald","9413119402","Critical","14"));
      //  requestsArrayList.add(new AddingItemsRequests("Lovish Badlani","O+","mandavi emerald","9413119402","Critical","14"));
      //  requestsArrayList.add(new AddingItemsRequests("Lovish Badlani","O+","mandavi emerald","9413119402","Critical","14"));

        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter= new RequestsCustomAdapter(requestsArrayList);
        mRecyclerView.setAdapter(mAdapter);

        //mImageView.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View v) {
            //    Intent i = new Intent(getApplicationContext(), MainActivity.class);
              //  startActivity(i);
            //}
       // });



    }

    public  ArrayList<AddingItemsRequests> addlistdata( ArrayList<AddingItemsRequests> datao)
    {

        final ArrayList<AddingItemsRequests> data=datao;
        ParseQuery<ParseObject> queryall = ParseQuery.getQuery("Requests");
        try {
            List<ParseObject> objects = queryall.find();
            for (int hole = objects.size()-1; hole >= 0; hole--) {
                //Toast.makeText(Request.this, "Server not responding", Toast.LENGTH_LONG).show();

                data.add(new AddingItemsRequests(objects.get(hole).get("victimname") + "", objects.get(hole).get("victimtype") + "", objects.get(hole).get("victimplace") + "", objects.get(hole).get("victimphone") + "", objects.get(hole).get("victimstatus") + "", objects.get(hole).get("victimunits").toString()));

            }
        }catch (ParseException e)
        {

            Toast.makeText(Request.this, "Server not responding", Toast.LENGTH_LONG).show();

        }


        return data;
    }













    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId())
            {
                case R.id.map_icon:
                    Intent c=new Intent(Request.this,MainActivity.class);
                    startActivity(c);
                    break;

                case R.id.leaderboard_icon:
                    Intent a=new Intent(Request.this,BheroLoading.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:
                    Intent b=new Intent(Request.this,Profile.class);
                    startActivity(b);
                    break;

                case R.id.request_blood:

                    break;

            }


            return false;
        }
    };
}
