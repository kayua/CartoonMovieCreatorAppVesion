package app.mosquito.appmosquito.appmosquito.ui.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;
import app.mosquito.appmosquito.appmosquito.ui.Maps.GPS_Sistem.GpsTracker;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    MapView mMapView;
    GpsTracker gps;
    private GoogleMap googleMap;
    Context mContext;
    private Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                gps = new GpsTracker(getActivity().getApplicationContext());
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                LatLng latLng= new LatLng(latitude, longitude);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng).zoom(17).bearing(0).tilt(40).build();

                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Log.i(Double. toString(latitude),Double. toString(longitude));
                Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitude ,longitude))
                        .radius(35)
                        .strokeColor(0xff018783)
                        .fillColor(Color.TRANSPARENT)
                        .strokeWidth(3)

                );
                Circle circle1 = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitude ,longitude))
                        .radius(1)
                        .strokeColor(0xff018783)
                        .fillColor(Color.TRANSPARENT)
                        .strokeWidth(5)

                );
                Marker mSydney = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude ,longitude))
                        .title("Seu local Atual")
                        .snippet("Dispositivo conectado")
                        .zIndex(1.0f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon)).alpha((float) 0.65));

                ParseUser.logInInBackground("Admin", "admin", (user, e) -> {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Detections");

                    try {

                        List<ParseObject> results = query.find();


                        for (int i=0; i< results.size(); i++) {

                            ParseObject  b = results.get(i);
                            ParseObject a = query.get(b.getObjectId());

                            String c = a.getString("latitude");
                            String d = a.getString("longitude");
                            List<LatLng> result = new ArrayList<>();
                            float lat =Float.parseFloat(c);
                            float log =Float.parseFloat(d);

                            result.add(new LatLng(lat, log));

                            int[] colors = {
                                    Color.rgb(30, 160, 30), // green
                                    Color.rgb(160, 30, 30)    // red
                            };

                            float[] startPoints = {
                                    0.1f, 1.0f
                            };


                            Gradient gradient = new Gradient(colors, startPoints);
                            HeatmapTileProvider provider = new HeatmapTileProvider.Builder().radius(25)
                                    .data(result).opacity(0.2).gradient(gradient)
                                    .build();
                            TileOverlay overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));


                        }
                    } catch (ParseException ei) {
                        e.printStackTrace();
                    }


                });




            }
        });

        Context context;


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}