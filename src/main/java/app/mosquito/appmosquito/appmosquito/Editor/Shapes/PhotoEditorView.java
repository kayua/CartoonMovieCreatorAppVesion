package app.mosquito.appmosquito.appmosquito.Editor.Shapes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import app.mosquito.appmosquito.appmosquito.R;

public class PhotoEditorView extends RelativeLayout {

    private static final String TAG = "PhotoEditorView";

    private FilterImageView mImgSource;
    private DrawingView mDrawingView;
    private ImageFilterView mImageFilterView;
    private static final int imgSrcId = 1, shapeSrcId = 2, glFilterId = 3;

    public PhotoEditorView(Context context) {

        super(context);
        init(null);

    }

    public PhotoEditorView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(attrs);

    }

    public PhotoEditorView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PhotoEditorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

    }

    @SuppressLint("Recycle")
    private void init(@Nullable AttributeSet attrs) {

        mImgSource = new FilterImageView(getContext());
        mImgSource.setId(imgSrcId);
        mImgSource.setAdjustViewBounds(true);
        LayoutParams imgSrcParam = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgSrcParam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        if (attrs != null) {

            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PhotoEditorView);
            Drawable imgSrcDrawable = a.getDrawable(R.styleable.PhotoEditorView_photo_src);

            if (imgSrcDrawable != null) { mImgSource.setImageDrawable(imgSrcDrawable); }

        }

        mDrawingView = new DrawingView(getContext());
        mDrawingView.setVisibility(GONE);
        mDrawingView.setId(shapeSrcId);

        LayoutParams brushParam = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        brushParam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        brushParam.addRule(RelativeLayout.ALIGN_TOP, imgSrcId);
        brushParam.addRule(RelativeLayout.ALIGN_BOTTOM, imgSrcId);

        mImageFilterView = new ImageFilterView(getContext());
        mImageFilterView.setId(glFilterId);
        mImageFilterView.setVisibility(GONE);

        LayoutParams imgFilterParam = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        imgFilterParam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        imgFilterParam.addRule(RelativeLayout.ALIGN_TOP, imgSrcId);
        imgFilterParam.addRule(RelativeLayout.ALIGN_BOTTOM, imgSrcId);

        mImgSource.setOnImageChangedListener(new FilterImageView.OnImageChangedListener() {
            @Override
            public void onBitmapLoaded(@Nullable Bitmap sourceBitmap) {
                mImageFilterView.setFilterEffect(PhotoFilter.NONE);
                mImageFilterView.setSourceBitmap(sourceBitmap);
                Log.d(TAG, "onBitmapLoaded() called with: sourceBitmap = [" + sourceBitmap + "]");
            }
        });


        addView(mImgSource, imgSrcParam);
        addView(mImageFilterView, imgFilterParam);
        addView(mDrawingView, brushParam);

    }


    public ImageView getSource() { return mImgSource; }

    DrawingView getDrawingView() { return mDrawingView; }

    void saveFilter(@NonNull final OnSaveBitmap onSaveBitmap) {

        if (mImageFilterView.getVisibility() == VISIBLE) {

            mImageFilterView.saveBitmap(new OnSaveBitmap() {

                @Override
                public void onBitmapReady(final Bitmap saveBitmap) {

                    Log.e(TAG, "saveFilter: " + saveBitmap);
                    mImgSource.setImageBitmap(saveBitmap);
                    mImageFilterView.setVisibility(GONE);
                    onSaveBitmap.onBitmapReady(saveBitmap);

                }

                @Override

                public void onFailure(Exception e) {
                    onSaveBitmap.onFailure(e);
                }

            });

        } else {

            onSaveBitmap.onBitmapReady(mImgSource.getBitmap());

        }

    }

    void setFilterEffect(PhotoFilter filterType) {

        mImageFilterView.setVisibility(VISIBLE);
        mImageFilterView.setSourceBitmap(mImgSource.getBitmap());
        mImageFilterView.setFilterEffect(filterType);

    }

    void setFilterEffect(CustomEffect customEffect) {

        mImageFilterView.setVisibility(VISIBLE);
        mImageFilterView.setSourceBitmap(mImgSource.getBitmap());
        mImageFilterView.setFilterEffect(customEffect);

    }

}
