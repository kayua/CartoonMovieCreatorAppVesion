package app.mosquito.appmosquito.appmosquito.Editor.Shapes;

import android.graphics.Bitmap;


public interface OnSaveBitmap {
    void onBitmapReady(Bitmap saveBitmap);

    void onFailure(Exception e);
}
