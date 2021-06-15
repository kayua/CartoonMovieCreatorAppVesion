package app.mosquito.appmosquito.appmosquito.Authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import app.mosquito.appmosquito.appmosquito.R;

public class  ActivityStart extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.auth_start, container, false);

        return rootView;
    }
}
