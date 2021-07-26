package app.mosquito.appmosquito.appmosquito.Editor.CropImage;

import android.graphics.RectF;
import android.net.Uri;

import com.isseiaoki.simplecropview.callback.LoadCallback;

import io.reactivex.Completable;

public class LoadRequest {

  private float initialFrameScale;
  private RectF initialFrameRect;
  private boolean useThumbnail;
  private CropImageView cropImageView;
  private Uri sourceUri;

  public LoadRequest(CropImageView cropImageView, Uri sourceUri) {
    this.cropImageView = cropImageView;
    this.sourceUri = sourceUri;
  }

  public com.isseiaoki.simplecropview.LoadRequest initialFrameScale(float initialFrameScale) {
    this.initialFrameScale = initialFrameScale;
    return this;
  }

  public com.isseiaoki.simplecropview.LoadRequest initialFrameRect(RectF initialFrameRect) {
    this.initialFrameRect = initialFrameRect;
    return this;
  }

  public com.isseiaoki.simplecropview.LoadRequest useThumbnail(boolean useThumbnail) {
    this.useThumbnail = useThumbnail;
    return this;
  }

  public void execute(LoadCallback callback) {
    if (initialFrameRect == null) {
      cropImageView.setInitialFrameScale(initialFrameScale);
    }
    cropImageView.loadAsync(sourceUri, useThumbnail, initialFrameRect, callback);
  }

  public Completable executeAsCompletable() {
    if (initialFrameRect == null) {
      cropImageView.setInitialFrameScale(initialFrameScale);
    }
    return cropImageView.loadAsCompletable(sourceUri, useThumbnail, initialFrameRect);
  }
}
