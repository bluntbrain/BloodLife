package com.example.learningmaps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    //private LatLng userLocation;
   // ParseGeoPoint currentUserLocation;
    private LatLng mycoordinatesl;
    private ImageView mylocation;
    private CardView buttonforrequest;
    private CameraPosition mCameraPosition; // for altering camera
    private PlacesClient mPlacesClient; // for places api
    private GoogleMap mMap;
    private BottomNavigationView mBottomNavigationView;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(-3.3525, 74.7928); // set to MIT - to be used when permission i'snt granted
    private static final int DEFAULT_ZOOM = 12;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private GifImageView mapLoading;
    private TextView upper,lower;
    private DatabaseReference mReference;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.
   // private static final int M_MAX_ENTRIES = 5;
   // private LatLng[] mLikelyPlaceLatLngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_main); // Rendering of the map
        mapLoading=findViewById(R.id.map_loading);
        upper=findViewById(R.id.no_req_text1);
        lower=findViewById(R.id.no_req_text2);
        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), getString(R.string.map_key));
        mPlacesClient = Places.createClient(this);




        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        //mFusedLocationProviderClient=new FusedLocationProviderClient(MainActivity.this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mylocation=findViewById(R.id.mylocation);
        buttonforrequest=findViewById(R.id.buttontorequestpage);
        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        buttonforrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, RequestsTabs.class);
                Bundle args= new Bundle();
                args.putParcelable("userlocation",mycoordinatesl);
                intent.putExtra("bundle", args);
                startActivity(intent);
            }
        });



    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        //mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        //showCurrentPlace();
         addallrequests();
        //allcamps();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.hideInfoWindow();
                if(marker.getTitle()=="Camp")
                {
                    Toast.makeText(MainActivity.this,"Camping",Toast.LENGTH_LONG).show();
                    BottomSheetDialog bottomSheetDialogCamp = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
                    View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_blood_camp_bottom_sheet, (RelativeLayout) findViewById(R.id.campbottomsheetcontainer));
                    //   bottomSheetView.findViewById(R.id.)
                    bottomSheetDialogCamp.setContentView(bottomSheetView);
                    bottomSheetDialogCamp.show();

                }else {

                    String[] allsheetdata=marker.getTitle().split("-");
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
                    View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_request_bottom_sheet, (RelativeLayout) findViewById(R.id.bottomsheetcontainer));
                    TextView namesheet =bottomSheetView.findViewById(R.id.namesheet);namesheet.setText(allsheetdata[0]);
                    TextView statussheet =bottomSheetView.findViewById(R.id.statussheets);statussheet.setText(allsheetdata[3]);
                    TextView unitssheet =bottomSheetView.findViewById(R.id.unitssheet);unitssheet.setText(allsheetdata[1]);
                    TextView bloodtypesheet =bottomSheetView.findViewById(R.id.bloodtypesheet);bloodtypesheet.setText(allsheetdata[2]);
                    TextView gendersheet =bottomSheetView.findViewById(R.id.gendersheet);gendersheet.setText(allsheetdata[4]);
                    TextView hospitalsheet =bottomSheetView.findViewById(R.id.locationsheet);hospitalsheet.setText(allsheetdata[5]);
                    final TextView phonesheet =bottomSheetView.findViewById(R.id.phonesheet);phonesheet.setText(allsheetdata[6]);

                    Button btnsheet = bottomSheetView.findViewById(R.id.buttonsheet);
                    btnsheet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+phonesheet.getText().toString()));
                            startActivity(intent);
                        }
                    });
                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();
                }
                return false;
            }
        });



    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                 mycoordinatesl=new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());


                            }
                        } else {
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Prompts the user to select the current place from a list of likely places, and shows the
     * current place on the map - provided the user has granted location permission.
     */
    private void allcamps()
    {
        ParseQuery<ParseObject> campAll = ParseQuery.getQuery("Camps");
        campAll.whereExists("camplocation");
        campAll.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null) {
                    if (objects.size() > 0) {
                        for (int i = 0; i < objects.size(); i++) {
                            LatLng Location = new LatLng(objects.get(i).getParseGeoPoint("camplocation").getLatitude(), objects.get(i).getParseGeoPoint("camplocation").getLongitude());
                            //MarkerOptions campmarker=new MarkerOptions().position(Location).title("Camp");
                            //campmarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker_icon)));

                            Marker marker = mMap.addMarker(new MarkerOptions().position(Location).title("Camp"));
                            marker.showInfoWindow();
                          //  marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mapmarker2));
                        }

                    }
                }else {
                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }

   private void addallrequests()
    {

        mReference= FirebaseDatabase.getInstance().getReference("BloodRequests");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int flag=0;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    flag=1;
                    Double Lat = Double.parseDouble(snapshot.child("victim_lat").getValue().toString());
                    Double Long = Double.parseDouble(snapshot.child("victim_long").getValue().toString());
                    String bloodType=snapshot.child("victim_bloodtype").getValue().toString();
                    String Title = snapshot.child("victim_name").getValue().toString()+"-"+
                            snapshot.child("units").getValue().toString()+"-"+
                            snapshot.child("victim_bloodtype").getValue().toString()+"-"+
                            snapshot.child("victim_status").getValue().toString()+"-"+
                            snapshot.child("victim_gender").getValue().toString()+"-"+
                            snapshot.child("victim_hospital").getValue().toString()+"-"+
                            snapshot.child("phone").getValue().toString()+"-"
                            ;

                    Bitmap smallMarker=getMarkerImage(bloodType);

                    LatLng Location = new LatLng(Lat,Long);
                    Marker requestmarker =mMap.addMarker(new MarkerOptions().position(Location).title(Title).icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            );

                    requestmarker.hideInfoWindow();

                }
                mapLoading.setAlpha(0f);
                mapLoading.setVisibility(View.GONE);
                if(flag==0){
                    upper.setAlpha(1f);
                    lower.setAlpha(1f);
                    upper.setVisibility(View.VISIBLE);
                    lower.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Server Respond :"+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }

    private Bitmap getMarkerImage(String bloodType){

        BitmapDrawable bitmapDrawable;
        if(bloodType.equals("AB-")){
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.custommarkerabminus);

        }else if(bloodType.equals("AB+")) {
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.custommarkerabpos);
        }else if(bloodType.equals("O+")) {
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.custommarkeropos);
        }else if(bloodType.equals("O-")) {
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.custommarkeroneg);
        }else if(bloodType.equals("A+")) {
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.custommarkerapos);
        }else if(bloodType.equals("A-")) {
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.custommarkeraneg);
        }else if(bloodType.equals("B+")) {
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.custommarkerbpos);
        }else {
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.custommarkerbneg);
        }

        Bitmap smallMarker=Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(),90,140,false);
        return smallMarker;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId())
            {
                case R.id.map_icon:

                    break;

                case R.id.leaderboard_icon:
                    Intent a=new Intent(MainActivity.this,BheroLoading.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:
                    Intent b=new Intent(MainActivity.this,ProfileFinal.class);
                    startActivity(b);
                    break;

                case R.id.request_blood: Intent c=new Intent(MainActivity.this,Request.class);
                    startActivity(c);

                    break;

            }


            return false;
        }
    };

    public void onBackPressed(){
        finishAffinity();
        finish();

    }
}
