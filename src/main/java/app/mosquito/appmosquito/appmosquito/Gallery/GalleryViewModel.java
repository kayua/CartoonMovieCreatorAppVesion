package app.mosquito.appmosquito.appmosquito.Gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {

    }


    public LiveData<String> getText() {
        return mText;
    }
}