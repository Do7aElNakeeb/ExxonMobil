package com.exxonmobil.mobapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exxonmobil.mobapp.R;
import com.exxonmobil.mobapp.app.AppConfig;
import com.exxonmobil.mobapp.app.AppController;
import com.exxonmobil.mobapp.app.MarkerDataSource;
import com.exxonmobil.mobapp.app.MarkerObj;
import com.exxonmobil.mobapp.helper.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Nakeeb PC on 7/24/2016.
 */

public class Stations extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener,
        PlaceSelectionListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = Stations.class.getSimpleName();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    Location nearestStation;
    Location mNearestStation;
    LocationRequest mLocationRequest;

    private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 1;

    Context context = this;
    MarkerDataSource stationInfo;
    private GoogleMap stationsMap;
    private LocationManager locationManager;
    private ProgressDialog pDialog;

    private ClusterManager<MarkerObj> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stations);

        Toolbar toolbar= (Toolbar) findViewById(R.id.mytoolBar);
        setSupportActionBar(toolbar);

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.mipmap.mobil_tm).withBadge("Home").withName("Menu");

        SecondaryDrawerItem item2= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.stations).withName("Stations");
        SecondaryDrawerItem item3= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.products).withName("Products");
        SecondaryDrawerItem item4= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.promotions).withName("Promotions");
        SecondaryDrawerItem item5= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.profile).withName("Profile");
        SecondaryDrawerItem item6= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.history).withName("History");
        SecondaryDrawerItem item7= (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(R.mipmap.settings).withName("Settings");


        Drawer result = new DrawerBuilder().withActivity(this).withToolbar(toolbar).addDrawerItems(item1,item2,item3,item4,item5, item6, item7).build();

        result.setSelection(item2);
        item1.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Landing.class);
                startActivity(intent);
                finish();
                return true;
            }
        };
        item2.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Stations.class);
                startActivity(intent);
                finish();
                return true;
            }
        };
        item3.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
                finish();
                return true;
            }
        };
        item4.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Promotions.class);
                startActivity(intent);
                finish();
                return true;
            }
        };
        item5.mOnDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
                return true;
            }
        };

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        stationInfo = new MarkerDataSource(context);
        try {
            stationInfo.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHasOptionsMenu(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        stationsMap = googleMap;
        stationsMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        initListeners();
        enableMyLocation();
        mClusterManager = new ClusterManager<MarkerObj>(this, stationsMap);
        stationsMap.setOnCameraIdleListener(mClusterManager);

        DisplayStations();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
            buildGoogleApiClient();
            stationsMap.setMyLocationEnabled(true);
        } else if (stationsMap != null) {
            // Access to the location has been granted to the app.
            buildGoogleApiClient();
            stationsMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void initListeners() {
        stationsMap.setOnMarkerClickListener(this);
        stationsMap.setOnInfoWindowClickListener(this);
        stationsMap.setTrafficEnabled(true);
        stationsMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void removeListeners() {
        if( stationsMap != null ) {
            stationsMap.setOnMarkerClickListener(null);
            stationsMap.setOnMapLongClickListener(null);
            stationsMap.setOnInfoWindowClickListener(null);
            stationsMap.setOnMapClickListener(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeListeners();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Create a default location if the Google API Client fails. Placing location at Googleplex
/*        mCurrentLocation = new Location( "" );
        mCurrentLocation.setLatitude( 37.422535 );
        mCurrentLocation.setLongitude(-122.084804);
//        initCamera(mCurrentLocation);
        onLocationChanged(mCurrentLocation);
        */
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(context, "Station Information", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, StationInfo.class);
        i.putExtra("marker", marker.getTitle());
        startActivity(i);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();

        return true;
    }


    private void syncStations(){

        // Tag used to cancel the request
        String tag_string_req = "req_register";


        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, AppConfig.URL_STATIONS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Stations Response: " + response.toString());
                pDialog.setMessage("Loading Mobil Stations");
//                showDialog();
                stationInfo.deleteMarkers();
//                hideDialog();

                try {
                    // Extract JSON array from the response
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    // If no of array elements is not zero
                    if(arr.length() != 0){
                        // Loop through each array element, get JSON object
                        for (int i = 0; i < arr.length(); i++) {
                            // Get JSON object
                            JSONObject obj = (JSONObject) arr.get(i);

                            // DB QueryValues Object to insert into SQLite
                            String name = obj.get("name").toString();
                            String address = obj.get("address").toString();
                            String region = obj.get("region").toString();
                            String city = obj.get("city").toString();
                            String latitude = obj.get("latitude").toString();
                            String longitude = obj.get("longitude").toString();
                            String mog80 = obj.get("MOG80").toString();
                            String mog92 = obj.get("MOG92").toString();
                            String mog95 = obj.get("MOG95").toString();
                            String diesel = obj.get("diesel").toString();
                            String MobilMart = obj.get("MobilMart").toString();
                            String OnTheRun = obj.get("OnTheRun").toString();
                            String TheWayToGo = obj.get("TheWayToGo").toString();
                            String CarWash = obj.get("CarWash").toString();
                            String lubricants = obj.get("lubricants").toString();
                            String phone = obj.get("phone").toString();

                            stationInfo.addStation(new MarkerObj(name, address, region, city, latitude, longitude,  mog80,  mog92,  mog95,  diesel,
                                    MobilMart,  OnTheRun,  TheWayToGo,  CarWash,  lubricants,  phone));

                            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                            MarkerOptions options = new MarkerOptions().position( latLng );
                            options.title(name);
                            options.icon( BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mobil_tm_marker ) ) );
                            stationsMap.addMarker( options );

                        }
                        //hideDialog();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Connection Error: " + error.getMessage());
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void DisplayStations() {

        pDialog.setMessage("Loading Mobil Stations");
//        showDialog();

        mCurrentLocation = new Location("");
        mCurrentLocation = stationsMap.getMyLocation();
        mNearestStation = new Location("");
        nearestStation = new Location("");

        List<MarkerObj> m = stationInfo.getStationsDetails(null);
        if(m.size() != 0) {
            mClusterManager.addItems(m);
            mNearestStation.setLatitude(30.1071329);
            mNearestStation.setLongitude(-95.4322685);
            for (int i = 0; i < m.size(); i++) {
                LatLng latLng = new LatLng(Double.valueOf(m.get(i).getLatitude()), Double.valueOf(m.get(i).getLongitude()));
                stationsMap.addMarker(new MarkerOptions()
                                .title(m.get(i).getName())
                                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mobil_tm_marker)))
                                .position(latLng)
                );
           //     nearestStation.setLatitude(Double.valueOf(m.get(i).getLatitude()));
             //   nearestStation.setLongitude(Double.valueOf(m.get(i).getLongitude()));

               // if (mCurrentLocation.distanceTo(nearestStation) < mCurrentLocation.distanceTo(mNearestStation)){
                 //   mNearestStation = nearestStation;
               // }
            }

            //hideDialog();
        } else {
            Toast.makeText(context,"No Stations Found",Toast.LENGTH_LONG).show();
        }

    }


    private void toggleTraffic() {
        stationsMap.setTrafficEnabled(!stationsMap.isTrafficEnabled());
    }

    private void cycleMapType() {
        if (curMapTypeIndex < MAP_TYPES.length - 1 ) {
            curMapTypeIndex++;
        } else {
            curMapTypeIndex = 0;
        }

        stationsMap.setMapType(MAP_TYPES[curMapTypeIndex]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear: {
                stationsMap.clear();
                return true;
            }

            case R.id.action_traffic: {
                toggleTraffic();
                return true;
            }
            case R.id.action_cycle_map_type: {
                cycleMapType();
                return true;
            }
            case R.id.refresh: {
                syncStations();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onPause() {
        stationInfo.close();
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onResume() {
        try {
            stationInfo.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //move map camera
        stationsMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        stationsMap.animateCamera(CameraUpdateFactory.zoomTo(16f));

        if (mNearestStation != null){
            LatLng latLng1 = new LatLng(mNearestStation.getLatitude(), mNearestStation.getLongitude());
            //move map camera
            stationsMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
            stationsMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onPlaceSelected(Place place) {

        //Place current location
        LatLng latLng = place.getLatLng();

        //move map camera
        stationsMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        stationsMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
        stationsMap.addMarker(new MarkerOptions()
                        .title(place.getName().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker())
                        .position(latLng)
        );
    }

    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}