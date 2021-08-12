package app.mosquito.appmosquito.appmosquito.MachineLearning;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecognizeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecognizeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}

