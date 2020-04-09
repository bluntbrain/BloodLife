package com.example.learningmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class Profile extends AppCompatActivity {

    private ImageView rateus,feedback,TnC,logoutlogo,editprofile,editprofiletext;
    private TextView logouttext;
    private TextView nameprofile,unitsprofile,typeprofile;
    private CircleImageView profilepic;

    private DatabaseReference mReference;
    private FirebaseUser mUser;

    BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mUser= FirebaseAuth.getInstance().getCurrentUser();


        TnC=findViewById(R.id.tnc);
        nameprofile=findViewById(R.id.nameprofile);
        unitsprofile=findViewById(R.id.unitsprofile);
        typeprofile=findViewById(R.id.typeprofile);
       // statusprofile=findViewById(R.id.imagestatus);
        profilepic=findViewById(R.id.profilepic);
        logoutlogo=findViewById(R.id.logoutlogo);
        logouttext=findViewById(R.id.logouttext);
        rateus=findViewById(R.id.rateuspic);
        feedback=findViewById(R.id.feedbackpic);
        editprofile=findViewById(R.id.editprofile);
       // editprofiletext=findViewById(R.id.editprofiletext);


        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadProfile();
        /*
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Profile");
            //ParseQuery<ParseObject> query =new ParseQuery.getQuery("Profile");
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername().toString());
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null && object != null) {
                        nameprofile.setText(object.get("name") + "");
                        typeprofile.setText(object.get("type") + "");
                        String holaunits = object.get("unitsdonated").toString();
                        unitsprofile.setText(holaunits);
                    }

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(Profile.this,e.toString(),Toast.LENGTH_LONG).show();
        }
        */

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
                View view= LayoutInflater.from(Profile.this).inflate(R.layout.activity_rate_us,null);
                builder.setView(view);
                builder.show();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
                View view= LayoutInflater.from(Profile.this).inflate(R.layout.activity_feed_back,null);
                builder.setView(view);
                builder.show();
            }
        });

        TnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.websitepolicies.com/policies/view/iQcUErSr");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        logouttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(Profile.this);
                progressDialog.setMessage("Logging Out");
                progressDialog.show();
              //  ParseUser.logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(Profile.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                progressDialog.dismiss();
            }
        });

        logoutlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(Profile.this);
                progressDialog.setMessage("Logging Out");
                progressDialog.show();
               // ParseUser.logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(Profile.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                progressDialog.dismiss();

            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(Profile.this,UploadPicture.class);
               // startActivity(intent);
            }
        });


    }

    private void loadProfile(){
        String id=mUser.getUid();
        mReference= FirebaseDatabase.getInstance().getReference("Users").child(id);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameprofile.setText(dataSnapshot.child("name").getValue().toString());
                typeprofile.setText(dataSnapshot.child("bloodtype").getValue().toString());
                unitsprofile.setText(dataSnapshot.child("units_donated").getValue().toString());
                String img=dataSnapshot.child("imageURL").getValue().toString();
                if(img.equals("default"))
                {
                    Picasso.get().load(R.drawable.tryimage).into(profilepic);
                }else
                {
                    Picasso.get().load(img).into(profilepic);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

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
                    Intent c=new Intent(Profile.this,MainActivity.class);
                    startActivity(c);
                    break;

                case R.id.leaderboard_icon:
                    Intent a=new Intent(Profile.this,BheroLoading.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:

                    break;

                case R.id.request_blood:
                    Intent b=new Intent(Profile.this,Request.class);
                    startActivity(b);

                    break;

            }


            return false;
        }
    };
}
