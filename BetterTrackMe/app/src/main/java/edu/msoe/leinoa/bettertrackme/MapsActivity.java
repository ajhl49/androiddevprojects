package edu.msoe.leinoa.bettertrackme;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
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

    final static int PERMISSIONS_REQUEST_READ_LOCATION = 1;

    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private Criteria criteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String svcName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(svcName);

        // Set location criteria
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                String str = "App needs to determine your location";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed, we can request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_READ_LOCATION);

                String str = "Location permissions granted";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            }
        } else {
            Location location = determineLocation();
            if (location != null) {
                updateWithNewLocation(location);
            }
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateWithNewLocation(location);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String str = "Lat: " + latitude + "\nLong: " + longitude;
                Toast.makeText(MapsActivity.this, str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Toast.makeText(MapsActivity.this, "status changed to " + i + "!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(MapsActivity.this, "Provider " + s + " enabled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(MapsActivity.this, "Provider " + s + " disabled!", Toast.LENGTH_SHORT).show();
            }
        };


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
        LatLng home = new LatLng(43.043811305948, -87.9084595625341);
        updateMapLocation(home, "Marker in Milwaukee");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Location location = determineLocation();
                    if (location != null) {
                        updateWithNewLocation(location);
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

    @Override
    protected void onResume() {
        super.onResume();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Toast.makeText(this, "onResume called", Toast.LENGTH_SHORT).show();
        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);
    }

    public void normalMapBtn(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void hybridMapBtn(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void satelliteMapBtn(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    private void updateWithNewLocation(Location location) {
        TextView myLocationText;
        myLocationText = (TextView) findViewById(R.id.app_location_text);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if (mMap != null) {
            updateMapLocation(new LatLng(latitude, longitude), "Updated map marker");
        }
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
                Toast.makeText(this, "Geocoder IO Exception", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Geocoder not found!", Toast.LENGTH_SHORT).show();
        }
    }

    private Location determineLocation() {
        Location returnLocation = null;
        String svcName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(svcName);

        // Obtain reference to location provider
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            try {
                returnLocation = locationManager.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                e.printStackTrace();
                Toast.makeText(this, "Exception on location load", Toast.LENGTH_SHORT);
            }
        } else {
            Toast toast = Toast.makeText(this, "Location based services not available",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        return returnLocation;
    }

    private void updateMapLocation(LatLng latLng, String markerTitle) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(markerTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
