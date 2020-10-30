package com.android.ekishan.delivery;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.ekishan.R;
import com.android.ekishan.view.LatLngInterpolator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MApActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private List<LatLng> polyLineList;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentLocationMarker;
    private Location currentLocation;
    private boolean firstTimeFlag = true;
    private SharedPreferences sharedPreferences;
    String origin = "18.969049,72.821182";
    String destination = "19.199560,72.964256";
    String name,mobile;
    ImageView address_back;
    String order_id = "";
    String id = "";
    String order_date = "";
    String super_viser_id = "";
    Button continue_btn;

//    private final View.OnClickListener clickListener = view -> {
//        if (view.getId() == R.id.currentLocationImageButton && googleMap != null && currentLocation != null)
//            animateCamera(currentLocation);
//    };

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            currentLocation = locationResult.getLastLocation();
            showMarker(currentLocation);

            if (firstTimeFlag && googleMap != null) {
                animateCamera(currentLocation);
                firstTimeFlag = false;
                currentLocation.setLatitude(currentLocation.getLatitude());
                currentLocation.setLongitude(currentLocation.getLongitude());
                showMarker(currentLocation);
                origin=currentLocation.getLatitude()+","+currentLocation.getLongitude();
                LatLng origin_lt_lng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(origin_lt_lng));
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(dest_lt_lng));
                new GetDirection().execute();
            }


        }
    };
    LatLng dest_lt_lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
        getSupportActionBar().hide();
        continue_btn = findViewById(R.id.continue_btn);
        address_back = findViewById(R.id.address_back);
        id=getIntent().getStringExtra("id");
        order_id=getIntent().getStringExtra("order_id");
        name=getIntent().getStringExtra("name");
        mobile=getIntent().getStringExtra("mobile");
        String drop_lat=getIntent().getStringExtra("drop_lat");
        String drop_long=getIntent().getStringExtra("drop_long");
        destination=drop_lat+","+drop_long;
         dest_lt_lng=new LatLng(Double.parseDouble(drop_lat),Double.parseDouble(drop_long));
        address_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DeliveryOrderDetails.class);
                intent.putExtra("name", name);
                intent.putExtra("mobile", mobile);
                intent.putExtra("subscription_id", getIntent().getStringExtra("subscription_id"));
                intent.putExtra("package_delivery_id", getIntent().getStringExtra("package_delivery_id"));
                intent.putExtra("payment_status", getIntent().getStringExtra("payment_status"));
                intent.putExtra("order_type", getIntent().getStringExtra("order_type"));
                intent.putExtra("agro_item_id", getIntent().getStringExtra("agro_item_id"));
                intent.putExtra("order_id", order_id);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.getMapAsync(this);

//        findViewById(R.id.currentLocationImageButton).setOnClickListener(clickListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


    }





    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MApActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(this, "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }

    private void animateCamera(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(14).build();
    }

    private void showMarker(@NonNull Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        CameraPosition.Builder positionBuilder = new CameraPosition.Builder();
//        positionBuilder.target(latLng);
//        positionBuilder.zoom(15f);
//        positionBuilder.bearing(45);
//        positionBuilder.tilt(60);
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(positionBuilder.build()));
//        changePositionSmoothly(currentLocationMarker,latLng);
        if (currentLocationMarker == null) {
            currentLocationMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.gps)).position(latLng));
        } else {
//            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());
//        animateCamera(currentLocation);

//        ValueAnimator markerAnimator = ValueAnimator.ofObject(new LatLngEvaluator(), (Object[]) new com.teluscare.android.tracking.LatLng()[]{currentLocationMarker.getPosition(),new com.teluscare.android.tracking.LatLng(latLng.latitude,latLng.longitude)});
//                ValueAnimator markerAnimator = ValueAnimator.ofObject(new LatLngEvaluator(), (Object[]) new com.teluscare.android.tracking.LatLng[]{ new com.teluscare.android.tracking.LatLng(latLng.latitude, latLng.longitude)});
//                //  markerAnimator.setRepeatCount(ValueAnimator.INFINITE);
//                //   markerAnimator.setRepeatMode(ValueAnimator.REVERSE);
//                markerAnimator.setDuration(500);
//                markerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//                final Marker finalMarker = currentLocationMarker;
////        final Icon finalIcon = icon;
//                markerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        if (finalMarker != null) {
////                    finalMarker.setIcon(finalIcon);
//                            finalMarker.setPosition((LatLng) animation.getAnimatedValue());
//                        }
//                    }
//                });
//                markerAnimator.start();
            animateMarker(currentLocation, currentLocationMarker);
        }
    }

    public void animateMarker(Location destination, Marker marker) {
        if (marker != null) {
            LatLng startPosition = marker.getPosition();
            LatLng endPosition = new LatLng(destination.getLatitude(), destination.getLongitude());

            float startRotation = marker.getRotation();

            LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(500); // duration 1 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                        marker.setPosition(newPosition);
                        marker.setRotation(computeRotation(v, startRotation, destination.getBearing()));
                        CameraPosition.Builder positionBuilder = new CameraPosition.Builder();
                        positionBuilder.target(newPosition);
                        positionBuilder.zoom(16f);
//                        positionBuilder.bearing(45);
//                        positionBuilder.tilt(60);
//                        animateCamera(currentLocation);
//                        go.animateCamera(CameraUpdateFactory.newCameraPosition(positionBuilder.build()));
                    } catch (Exception ex) {
                        // I don't care atm..
                    }
                }
            });

            valueAnimator.start();
        }
    }

    private float computeRotation(float fraction, float start, float end) {
        float normalizeEnd = end - start; // rotate start to 0
        float normalizedEndAbs = (normalizeEnd + 360) % 360;

        float direction = (normalizedEndAbs > 180) ? -1 : 1; // -1 = anticlockwise, 1 = clockwise
        float rotation;
        if (direction > 0) {
            rotation = normalizedEndAbs;
        } else {
            rotation = normalizedEndAbs - 360;
        }

        float result = fraction * rotation + start;
        return (result + 360) % 360;
    }
//    public class LatLngEvaluator implements TypeEvaluator<com.teluscare.android.tracking.LatLng> {
//
//        private com.teluscare.android.tracking.LatLng mLatLng = new com.teluscare.android.tracking.LatLng();
//
//        @Override
//        public com.teluscare.android.tracking.LatLng evaluate(float fraction, com.teluscare.android.tracking.LatLng startValue, com.teluscare.android.tracking.LatLng endValue) {
//            mLatLng.setLatitude(startValue.getLatitude() + (endValue.getLatitude() - startValue.getLatitude()) * fraction);
//            mLatLng.setLongitude(startValue.getLongitude() + (endValue.getLongitude() - startValue.getLongitude()) * fraction);
//            return mLatLng;
//        }
//    }
//    private ValueAnimator animateMoveMarker(final Marker marker, LatLng to) {
//        final ValueAnimator markerAnimator = ObjectAnimator.ofObject(marker, "position", new LatLngEvaluator(), marker.getPosition(), to);
//        markerAnimator.setDuration((long) (10 * marker.getPosition().distanceTo(to)));
//        markerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//
//        // Start
//        markerAnimator.start();
//
//        return markerAnimator;
//    }
//    public LatLng() {
//        this.latitude = 0.0;
//        this.longitude = 0.0;
//    }

//            if (markerhash.containsKey(Integer.parseInt(user_id))) {
//        marker = markerhash.get(Integer.parseInt(user_id));
//        marker.setIcon(icon);
//        double lat_dif = marker.getPosition().getLatitude() - latitude;
//        double long_dif = marker.getPosition().getLongitude() - longitude;
//        if ((lat_dif > -0.0000300 && lat_dif < 0.0000300) && (long_dif > -0.0000300 && long_dif < 0.0000300)) {
//            return;
////        } else {
//            ValueAnimator markerAnimator = ValueAnimator.ofObject(new LatLngEvaluator(), (Object[]) new LatLng[]{currentLocationMarker.getPosition(),latLng});
//            //  markerAnimator.setRepeatCount(ValueAnimator.INFINITE);
//            //   markerAnimator.setRepeatMode(ValueAnimator.REVERSE);
//            markerAnimator.setDuration(500);
//            markerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//            final Marker finalMarker = marker;
//            final Icon finalIcon = icon;
//            markerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    if (finalMarker != null) {
//                        finalMarker.setIcon(finalIcon);
//                        finalMarker.setPosition((LatLng) animation.getAnimatedValue());
//                    }
//                }
//            });
//            markerAnimator.start();
//        }
//    } else {
//        if (navigationView.getMapboxMap() != null) {
//
//            marker = navigationView.getMapboxMap().addMarker(new MarkerOptions()
//                    .position(new LatLng(latitude, longitude))
//                    .snippet(user_id)
//                    .icon(icon)
//            );
//
////            markerhash.put(Integer.valueOf(user_id), marker);
//        }

    //    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        googleMap = null;
    }


    List<LatLng> pontos;

    public class GetDirection extends AsyncTask<String, String, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MApActivity.this);
            dialog.setMessage("Drawing the route, please wait!");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... args) {
//            String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=" + origin+ "&destination=" + destination+ "&sensor=false";
            String stringUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&sensor=false" + "&key="+getString(R.string.google_map_key);
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(stringUrl);
                HttpURLConnection httpconn = (HttpURLConnection) url
                        .openConnection();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader input = new BufferedReader(
                            new InputStreamReader(httpconn.getInputStream()),
                            8192);
                    String strLine = null;

                    while ((strLine = input.readLine()) != null) {
                        response.append(strLine);
                    }
                    input.close();
                }

                String jsonOutput = response.toString();

                JSONObject jsonObject = new JSONObject(jsonOutput);

                // routesArray contains ALL routes
                JSONArray routesArray = jsonObject.getJSONArray("routes");
                // Grab the first route
                JSONObject route = routesArray.getJSONObject(0);

                JSONObject poly = route.getJSONObject("overview_polyline");
                String polyline = poly.getString("points");
                pontos = decodePoly(polyline);

            } catch (Exception e) {

            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            try {
                if (pontos != null && pontos.size() > 0) {

                    for (int i = 0; i < pontos.size() - 1; i++) {
                        LatLng src = pontos.get(i);
                        LatLng dest = pontos.get(i + 1);
                        try {
                            //here is where it will draw the polyline in your map
                            Polyline line = googleMap.addPolyline(new PolylineOptions()
                                    .add(new LatLng(src.latitude, src.longitude),
                                            new LatLng(dest.latitude, dest.longitude))
                                    .width(10).color(Color.RED).geodesic(true));
                        } catch (NullPointerException e) {
                            Log.e("Error", "NullPointerException onPostExecute: " + e.toString());
                        } catch (Exception e2) {
                            Log.e("Error", "Exception onPostExecute: " + e2.toString());
                        }
                    }

                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }catch (Exception e){

            }
        }

        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }
}