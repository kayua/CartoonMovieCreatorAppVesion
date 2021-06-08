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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    GpsTracker gpsDaemonTracker;
    private GoogleMap googleMap;
    private Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
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
            public void onMapReady(GoogleMap fragmentMaps) {

                googleMap = fragmentMaps;
                gpsDaemonTracker = new GpsTracker(getActivity().getApplicationContext());
                double relativeLatitude = gpsDaemonTracker.getLatitude();
                double relativeLongitude = gpsDaemonTracker.getLongitude();

                LatLng latLng= new LatLng(relativeLatitude, relativeLongitude);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng).zoom(16).bearing(0).tilt(40).build();

                fragmentMaps.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Log.i(Double. toString(relativeLatitude),Double. toString(relativeLongitude));

                fragmentMaps.addCircle(new CircleOptions().center(new LatLng(relativeLatitude ,relativeLongitude))
                        .radius(35).strokeColor(0xff018783)
                        .fillColor(Color.TRANSPARENT).strokeWidth(3));

                fragmentMaps.addCircle(new CircleOptions().center(new LatLng(relativeLatitude ,relativeLongitude))
                        .radius(1).strokeColor(0xff018783)
                        .fillColor(Color.TRANSPARENT).strokeWidth(5));

                fragmentMaps.addMarker(new MarkerOptions().position(new LatLng(relativeLatitude ,relativeLongitude))
                        .title("Seu local Atual").snippet("Dispositivo conectado")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon))
                        .alpha((float) 0.65));

                ParseUser.logInInBackground("Admin", "admin", (user, e) -> {

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Detections");

                    try {

                        List<ParseObject> databaseRequest = query.find();


                        for (int i=0; i< databaseRequest.size(); i++) {

                            ParseObject  idInfoRequest = databaseRequest.get(i);
                            ParseObject setFileRequest = query.get(idInfoRequest.getObjectId());
                            String latitudeRequested = setFileRequest.getString("latitude");
                            String longitudeRequested = setFileRequest.getString("longitude");
                            List<LatLng> arrayCoordinates = new ArrayList<>();
                            float latitudeValue =Float.parseFloat(latitudeRequested);
                            float longitudevalue =Float.parseFloat(longitudeRequested);

                            arrayCoordinates.add(new LatLng(latitudeValue, longitudevalue));

                            int[] gradientColors = {
                                    Color.rgb(30, 160, 30),
                                    Color.rgb(160, 30, 30)};

                            float[] gradientLimits = {0.1f, 1.0f};


                            Gradient gradientDimension = new Gradient(gradientColors, gradientLimits);
                            HeatmapTileProvider structureProvider = new HeatmapTileProvider.Builder()
                                    .radius(25)
                                    .data(arrayCoordinates).opacity(0.2).gradient(gradientDimension)
                                    .build();
                            fragmentMaps.addTileOverlay(new TileOverlayOptions().tileProvider(structureProvider));


                        }
                    } catch (ParseException ei) {
                        e.printStackTrace();
                    }


                });


            }
        });

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