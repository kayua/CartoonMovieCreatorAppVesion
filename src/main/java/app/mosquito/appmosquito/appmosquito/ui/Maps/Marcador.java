package app.mosquito.appmosquito.appmosquito.ui.Maps;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Marcador implements ClusterItem {
    private final LatLng mPosition;

    public Marcador(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}