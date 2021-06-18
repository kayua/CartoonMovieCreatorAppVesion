package app.mosquito.appmosquito.appmosquito.ui.Home;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import app.mosquito.appmosquito.appmosquito.R;
import app.mosquito.appmosquito.appmosquito.ui.Maps.GPS_Sistem.GpsTracker;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    MapView mMapView;
    GpsTracker gpsDaemonTracker;
    private GoogleMap googleMap;
    private Context context;
    private TextView userName;

    public static final String PREFS_NAME = "PersonalDatabase";

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Camera mCamera = getCameraInstance();

        CameraPreview mPreview = new CameraPreview(getContext(), mCamera);

        FrameLayout preview = (FrameLayout) root.findViewById(R.id.camera_preview);

        preview.addView(mPreview);

        return root;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    public static Camera getCameraInstance(){
        Camera c = null;

        try {
            c = Camera.open(); // attempt to get a Camera instance
            c.setDisplayOrientation(90);
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


}