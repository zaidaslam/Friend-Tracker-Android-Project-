package model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by Zaid on 9/28/2017.
 */

public class MyLocationModel  implements LocationListener{


    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private Criteria criteria;
    private String provider;
    Context mContext;

    private   String LOG_TAG = this.getClass().getName();

    public MyLocationModel(Context mContext){
        this.mContext = mContext;


        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }

        locationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                0, this);
        setMostRecentLocation(locationManager.getLastKnownLocation(provider));
    }


    private void setMostRecentLocation(Location lastKnownLocation) {

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    @Override
    public void onLocationChanged(Location location) {
        double lon = (double) (location.getLongitude());
        double lat = (double) (location.getLatitude());

        //Toast.makeText(mContext,"testing"+lon,Toast.LENGTH_SHORT).show();
        latitude = lat;
        longitude = lon;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
