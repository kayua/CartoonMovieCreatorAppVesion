package app.mosquito.appmosquito.appmosquito.Posts.BarMakePost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MakePostViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MakePostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}