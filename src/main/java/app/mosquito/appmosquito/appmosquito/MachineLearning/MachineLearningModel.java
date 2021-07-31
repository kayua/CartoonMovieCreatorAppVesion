package app.mosquito.appmosquito.appmosquito.MachineLearning;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MachineLearningModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MachineLearningModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}