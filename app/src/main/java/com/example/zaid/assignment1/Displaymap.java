package com.example.zaid.assignment1;

import android.app.Dialog;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import model.Friend;
import model.FriendModel;
import model.Meeting;
import model.MeetingModel;

public class Displaymap extends FragmentActivity implements OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    private GoogleMap mMap;
    private  static final int ERROR_DIALOG_REQUEST = 9001;
    private LocationListener mListener;
    private GoogleApiClient mLocationClient;
    private Marker marker1, marker2;
    private  Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaymap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        List<Friend> friendlocation;
        friendlocation = FriendModel.getInstance().getFriends();
        Meeting meeting;
        String id = getIntent().getStringExtra("DisplayMap");
        Log.d("test",String.format("MeetingID: %s",id));
        meeting = MeetingModel.getInstance().getMeetingById(id);

        //Log.d("Midpoint === == = >",meeting.getLocation());

        String[] parts = meeting.getLocation().split(",");
        String midlat = parts[0]; // 004
        String midlong = parts[1];

        double midpointlat = Double.parseDouble(midlat);
        double midpointlong = Double.parseDouble(midlong);


        LatLng sydney = new LatLng(midpointlat, midpointlong);
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(sydney , 16.0f) );
        marker1 = mMap.addMarker(new MarkerOptions().position(sydney).title(midlat+","+midlong));

        mLocationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationClient.connect();




    }

    public boolean servicesOK(){

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
            Dialog dialog =
            GooglePlayServicesUtil.getErrorDialog(isAvailable,this,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this,"Can't connect",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void getCurrentLocation(){

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);

        if(currentLocation == null){
            Toast.makeText(this,"Can't connect",Toast.LENGTH_SHORT).show();
        }
        else {
            LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
            Double l1=latLng.latitude;
            Double l2=latLng.longitude;
            String coordl1 = l1.toString();
            String coordl2 = l2.toString();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , 16.0f));
           // marker2 = mMap.addMarker(new MarkerOptions().position(latLng).title(coordl1+","+coordl2));
            marker2 = mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));

            drawLine();
        }

    }

    private void drawLine(){
        PolylineOptions lineOptions  = new PolylineOptions()
                .add(marker1.getPosition())
                .add(marker2.getPosition());
        line = mMap.addPolyline(lineOptions);

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();


        mListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(marker2 != null) {
                    marker2.remove();
                    line.remove();
                    Toast.makeText(Displaymap.this,
                            "Location Changed: " + location.getLatitude() + "," +
                                    location.getLongitude(), Toast.LENGTH_SHORT).show();

                    getCurrentLocation();
                }

            }
        };
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(500000000);
        request.setFastestInterval(1000000);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, request, mListener
        );
        //Toast.makeText(this,"Ready to Map",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, mListener);
    }

}
