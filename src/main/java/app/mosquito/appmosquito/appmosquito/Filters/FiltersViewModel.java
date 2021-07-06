package app.mosquito.appmosquito.appmosquito.Filters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FiltersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FiltersViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}