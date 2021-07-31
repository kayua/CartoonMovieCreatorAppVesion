package app.mosquito.appmosquito.appmosquito.Editor.Shapes;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import app.mosquito.appmosquito.appmosquito.R;

class Sticker extends Graphic {

    private final MultiTouchListener mMultiTouchListener;
    private final ViewGroup mPhotoEditorView;
    private final PhotoEditorViewState mViewState;
    private ImageView imageView;

    public Sticker(ViewGroup photoEditorView, MultiTouchListener multiTouchListener,
                   PhotoEditorViewState viewState, GraphicManager graphicManager

    ) {

        super(photoEditorView.getContext(), graphicManager);
        mPhotoEditorView = photoEditorView;
        mViewState = viewState;
        mMultiTouchListener = multiTouchListener;
        setupGesture();

    }

    void buildView(Bitmap desiredImage) { imageView.setImageBitmap(desiredImage); }

    private void setupGesture() {

        MultiTouchListener.OnGestureControl onGestureControl = buildGestureController(mPhotoEditorView, mViewState);
        mMultiTouchListener.setOnGestureControl(onGestureControl);
        View rootView = getRootView();
        rootView.setOnTouchListener(mMultiTouchListener);

    }


    @Override
    ViewType getViewType() { return ViewType.IMAGE; }

    @Override
    int getLayoutId() { return R.layout.layout_image_editor_view; }

    @Override
    void setupView(View rootView) { imageView = rootView.findViewById(R.id.imgPhotoEditorImage); }

}
