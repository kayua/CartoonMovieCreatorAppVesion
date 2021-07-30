package app.mosquito.appmosquito.appmosquito.Posts.BarPost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FollowerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FollowerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}