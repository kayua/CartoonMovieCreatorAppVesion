package app.mosquito.appmosquito.appmosquito.Cartoon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartoonModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CartoonModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}