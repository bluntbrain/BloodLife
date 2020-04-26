package com.example.learningmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class ProfileFinal extends AppCompatActivity {

    private TextView profilefinal_name,profilefinal_email,profilefinal_bloodtype,profilefinal_phone,profilefinal_gender;
    private GifImageView loading;
    private CircleImageView profilepic;
    private DatabaseReference mReference;
    private FirebaseUser mUser;
    BottomNavigationView mBottomNavigationView;
    private TextView logout_btn,edit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_final);
        mUser= FirebaseAuth.getInstance().getCurrentUser();

        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setSelectedItemId(R.id.profile_icon);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        profilefinal_name=findViewById(R.id.profilefinal_name);
        profilefinal_bloodtype=findViewById(R.id.profilefinal_bloodtype);
        profilefinal_gender=findViewById(R.id.profilefinal_gender);
        profilefinal_phone=findViewById(R.id.profilefinal_phone);
        profilefinal_email=findViewById(R.id.profilefinal_email);
        profilepic=findViewById(R.id.profilefinalpic);
        loading=findViewById(R.id.profilefinal_loading);
        logout_btn=findViewById(R.id.profilefinal_logout_btn);
        edit_btn=findViewById(R.id.profilefinal_edit_btn);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(ProfileFinal.this);
                progressDialog.setMessage("Logging Out");
                progressDialog.show();
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(ProfileFinal.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                progressDialog.dismiss();
                startActivity(intent);

            }
        });
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileFinal.this,EditProfile.class);
                startActivity(intent);
            }
        });
        loadProfile();
    }


    private void loadProfile() {
        String id=mUser.getUid();
        mReference= FirebaseDatabase.getInstance().getReference("Users").child(id);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profilefinal_name.setText(dataSnapshot.child("name").getValue().toString());
                profilefinal_bloodtype.setText(dataSnapshot.child("bloodtype").getValue().toString());
                profilefinal_gender.setText(dataSnapshot.child("gender").getValue().toString());
                profilefinal_phone.setText(dataSnapshot.child("phone").getValue().toString());
                profilefinal_email.setText(dataSnapshot.child("email").getValue().toString());

                String img=dataSnapshot.child("imageURL").getValue().toString();
                if(img.equals("default"))
                {
                    Picasso.get().load(R.drawable.tryimage).into(profilepic);
                }else
                {
                    Picasso.get().load(img).into(profilepic);


                }

                loading.setVisibility(View.GONE);
                profilepic.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileFinal.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId())
            {
                case R.id.map_icon:

                    Intent c=new Intent(ProfileFinal.this,MainActivity.class);
                    startActivity(c);
                    break;

                case R.id.leaderboard_icon:

                    Intent a=new Intent(ProfileFinal.this,BheroLoading.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:

                    break;

                case R.id.request_blood_icon:

                    Intent b=new Intent(ProfileFinal.this,Request.class);
                    startActivity(b);

                    break;

            }


            return false;
        }
    };

}
