package com.example.learningmaps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
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
    private TextView logout_btn,edit_btn,shareBtn;
    private TextView feedback,rateus,aboutus,developers,TnC;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_final);
        mUser= FirebaseAuth.getInstance().getCurrentUser();

        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setSelectedItemId(R.id.profile_icon);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        profilefinal_name=findViewById(R.id.profilefinal_name);
        shareBtn=findViewById(R.id.share_app_profile_btn);
        profilefinal_bloodtype=findViewById(R.id.profilefinal_bloodtype);
        profilefinal_gender=findViewById(R.id.profilefinal_gender);
        profilefinal_phone=findViewById(R.id.profilefinal_phone);
        profilefinal_email=findViewById(R.id.profilefinal_email);
        profilepic=findViewById(R.id.profilefinalpic);
        loading=findViewById(R.id.profilefinal_loading);
        logout_btn=findViewById(R.id.profilefinal_logout_btn);
        edit_btn=findViewById(R.id.profilefinal_edit_btn);

        feedback=findViewById(R.id.feedback_text);
        rateus=findViewById(R.id.rate_us_text);
        aboutus=findViewById(R.id.aboutus);
        developers=findViewById(R.id.developers);
        TnC=findViewById(R.id.tncc);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(ProfileFinal.this);
                builder.setTitle("Would you like to logout?");
                builder.setMessage("You won't be able to access Blood Requests");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutUser();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog = builder.create();
                dialog.show();


            }
        });

        TnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.websitepolicies.com/policies/view/pLXKb6oC"));
                startActivity(i);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BloodLife");
                String shareMessage= "Manipal's own blood donation platform is here, download it now and save lives in no time\n*Blood is a life, pass it on!*\n";
                shareMessage = shareMessage + "playstore link....";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
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

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ProfileFinal.this, "hello",Toast.LENGTH_SHORT).show();
               // FeedBack feedBack=new FeedBack();
               // feedBack.showDialog(ProfileFinal.this);

                Dialog dialog = new Dialog(ProfileFinal.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_feed_back);
                ImageView mButton = dialog.findViewById(R.id.emailusbutton);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "bloodlifemanipal@gmail.com"));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report");
                        intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileFinal.this,about_us_page.class);
                startActivity(intent);
            }
        });

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ProfileFinal.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_rate_us);
                dialog.show();
            }
        });

        developers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(ProfileFinal.this,developers_page.class);
               startActivity(intent);
            }
        });
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

    private void logoutUser(){
        ProgressDialog progressDialog=new ProgressDialog(ProfileFinal.this);
        progressDialog.setMessage("Logging Out");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent(ProfileFinal.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        progressDialog.dismiss();
        startActivity(intent);


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

                    Intent a=new Intent(ProfileFinal.this,LeaderBoard.class);
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
