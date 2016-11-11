package edu.msoe.leinoa.trackme;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    final static int MY_PERMISSIONS_REQUEST_READ_LOCATION = 1;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                String str = "Explanation needed: Please I need to determine your location";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed, we can request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_LOCATION);

                String str = "No explanation needed: thanks.";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            }
        }
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] gransResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                if (gransResults.length > 0 && gransResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager;
                    String svcName = Context.LOCATION_SERVICE;
                    locationManager = (LocationManager) getSystemService(svcName);

                    // Set location criteria
                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    criteria.setPowerRequirement(Criteria.POWER_LOW);
                    criteria.setAltitudeRequired(false);
                    criteria.setBearingRequired(false);
                    criteria.setSpeedRequired(false);
                    criteria.setCostAllowed(true);

                    // Obtain reference to location provider
                    String provider = locationManager.getBestProvider(criteria, true);
                    if (provider != null) {
                        try {
                            Location l = locationManager.getLastKnownLocation(provider);
                            updateWithNewLocation(l);

                        } catch (SecurityException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Exception on location load", Toast.LENGTH_SHORT);
                        }

                        // add automated location updates later
                        //locationManager.requestLocationUpdate(provider, 2000, 10, locationListener);
                    } else {
                        Toast toast = Toast.makeText(this, "Location based services not available",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                break;
            }
            default: {
                Toast.makeText(this, "Unknown " + requestCode + " permissions granted", Toast.LENGTH_SHORT).show();
                break;
            }
        }

    }

    private void updateWithNewLocation(Location location) {
        TextView myLocationText;
        myLocationText = (TextView) findViewById(R.id.myLocationText);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Geocoder geocoder = new Geocoder(this);
        if (geocoder.isPresent()) {
            try {
                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                Address address = addressList.get(0);
                String addressString = address.getAddressLine(0) + "\n" + address.getLocality() + "\n" +
                        address.getPostalCode() + "\n" + address.getCountryName();

                myLocationText.setText("Your current position is:\n" +
                    "Lat: " + latitude + " Long: " + longitude + "\n\n" +
                    addressString);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Geocoder IO Exception", Toast.LENGTH_LONG);
            }
        } else {
            Toast.makeText(this, "Geocoder not found!", Toast.LENGTH_SHORT);
        }
    }
}
