package app.mosquito.appmosquito.appmosquito.Cartoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import app.mosquito.appmosquito.appmosquito.R;

public class CartoonizeFragment extends Fragment {

    private CartoonizeViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(CartoonizeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cartoon, container, false);

        return root;
    }



}
