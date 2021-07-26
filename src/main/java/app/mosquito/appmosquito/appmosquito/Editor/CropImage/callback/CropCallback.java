package app.mosquito.appmosquito.appmosquito.Editor.CropImage.callback;

import android.graphics.Bitmap;

public interface CropCallback extends Callback {
  void onSuccess(Bitmap cropped);
}
