package app.mosquito.appmosquito.appmosquito.Editor.Shapes;

import android.view.View;

import androidx.annotation.Nullable;

public class BrushDrawingStateListener implements BrushViewChangeListener {
    private final PhotoEditorView mPhotoEditorView;
    private final PhotoEditorViewState mViewState;
    private @Nullable
    OnPhotoEditorListener mOnPhotoEditorListener;

    BrushDrawingStateListener(PhotoEditorView photoEditorView,
                              PhotoEditorViewState viewState) {
        mPhotoEditorView = photoEditorView;
        mViewState = viewState;
    }

    public void setOnPhotoEditorListener(@Nullable ja.burhanrashid52.photoeditor.OnPhotoEditorListener onPhotoEditorListener) {
        mOnPhotoEditorListener = onPhotoEditorListener;
    }

    @Override
    public void onViewAdd(ja.burhanrashid52.photoeditor.DrawingView drawingView) {
        if (mViewState.getRedoViewsCount() > 0) {
            mViewState.popRedoView();
        }
        mViewState.addAddedView(drawingView);
        if (mOnPhotoEditorListener != null) {
            mOnPhotoEditorListener.onAddViewListener(
                    ja.burhanrashid52.photoeditor.ViewType.BRUSH_DRAWING,
                    mViewState.getAddedViewsCount()
            );
        }
    }

    @Override
    public void onViewRemoved(ja.burhanrashid52.photoeditor.DrawingView drawingView) {
        if (mViewState.getAddedViewsCount() > 0) {
            View removeView = mViewState.removeAddedView(
                    mViewState.getAddedViewsCount() - 1
            );
            if (!(removeView instanceof ja.burhanrashid52.photoeditor.DrawingView)) {
                mPhotoEditorView.removeView(removeView);
            }
            mViewState.pushRedoView(removeView);
        }

        if (mOnPhotoEditorListener != null) {
            mOnPhotoEditorListener.onRemoveViewListener(
                    ja.burhanrashid52.photoeditor.ViewType.BRUSH_DRAWING,
                    mViewState.getAddedViewsCount()
            );
        }
    }

    @Override
    public void onStartDrawing() {
        if (mOnPhotoEditorListener != null) {
            mOnPhotoEditorListener.onStartViewChangeListener(ja.burhanrashid52.photoeditor.ViewType.BRUSH_DRAWING);
        }
    }

    @Override
    public void onStopDrawing() {
        if (mOnPhotoEditorListener != null) {
            mOnPhotoEditorListener.onStopViewChangeListener(ja.burhanrashid52.photoeditor.ViewType.BRUSH_DRAWING);
        }
    }
}
