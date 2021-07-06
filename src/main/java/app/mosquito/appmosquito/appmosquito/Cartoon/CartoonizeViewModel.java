package app.mosquito.appmosquito.appmosquito.Cartoon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartoonizeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CartoonizeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}

