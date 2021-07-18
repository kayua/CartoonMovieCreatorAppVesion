package app.mosquito.appmosquito.appmosquito.Editor.Shapes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.UiThread;


public interface PhotoEditor {

    void addImage(Bitmap desiredImage);


    @SuppressLint("ClickableViewAccessibility")
    void addText(String text, int colorCodeTextView);

    @SuppressLint("ClickableViewAccessibility")
    void addText(@Nullable Typeface textTypeface, String text, int colorCodeTextView);

    @SuppressLint("ClickableViewAccessibility")
    void addText(String text, @Nullable TextStyleBuilder styleBuilder);

    void editText(@NonNull View view, String inputText, int colorCode);

    void editText(@NonNull View view, @Nullable Typeface textTypeface, String inputText, int colorCode);

    void editText(@NonNull View view, String inputText, @Nullable TextStyleBuilder styleBuilder);

    void addEmoji(String emojiName);

    void addEmoji(Typeface emojiTypeface, String emojiName);

    void setBrushDrawingMode(boolean brushDrawingMode);

    /**
     * @return true is brush mode is enabled
     */
    Boolean getBrushDrawableMode();

    /**
     * Set the size of brush user want to paint on canvas i.e {@link DrawingView}
     * @deprecated use {@code setShape} of a ShapeBuilder
     *
     * @param size size of brush
     */
    @Deprecated
    void setBrushSize(float size);

    /**
     * set opacity/transparency of brush while painting on {@link DrawingView}
     * @deprecated use {@code setShape} of a ShapeBuilder
     *
     * @param opacity opacity is in form of percentage
     */
    @Deprecated
    void setOpacity(@IntRange(from = 0, to = 100) int opacity);

    /**
     * set brush color which user want to paint
     * @deprecated use {@code setShape} of a ShapeBuilder
     *
     * @param color color value for paint
     */
    @Deprecated
    void setBrushColor(@ColorInt int color);

    /**
     * set the eraser size
     * <b>Note :</b> Eraser size is different from the normal brush size
     *
     * @param brushEraserSize size of eraser
     */
    void setBrushEraserSize(float brushEraserSize);

    /**
     * @return provide the size of eraser
     * @see ja.burhanrashid52.photoeditor.PhotoEditor#setBrushEraserSize(float)
     */
    float getEraserSize();

    /**
     * @return provide the size of eraser
     * @see ja.burhanrashid52.photoeditor.PhotoEditor#setBrushSize(float)
     */
    float getBrushSize();

    /**
     * @return provide the size of eraser
     * @see ja.burhanrashid52.photoeditor.PhotoEditor#setBrushColor(int)
     */
    int getBrushColor();

    /**
     * <p>
     * Its enables eraser mode after that whenever user drags on screen this will erase the existing
     * paint
     * <br>
     * <b>Note</b> : This eraser will work on paint views only
     * <p>
     */
    void brushEraser();

    /**
     * Undo the last operation perform on the {@link ja.burhanrashid52.photoeditor.PhotoEditor}
     *
     * @return true if there nothing more to undo
     */
    boolean undo();

    /**
     * Redo the last operation perform on the {@link ja.burhanrashid52.photoeditor.PhotoEditor}
     *
     * @return true if there nothing more to redo
     */
    boolean redo();

    /**
     * Removes all the edited operations performed {@link ja.burhanrashid52.photoeditor.PhotoEditorView}
     * This will also clear the undo and redo stack
     */
    void clearAllViews();

    /**
     * Remove all helper boxes from views
     */
    @UiThread
    void clearHelperBox();

    /**
     * Setup of custom effect using effect type and set parameters values
     *
     * @param customEffect {@link ja.burhanrashid52.photoeditor.CustomEffect.Builder#setParameter(String, Object)}
     */
    void setFilterEffect(ja.burhanrashid52.photoeditor.CustomEffect customEffect);


    /**
     * Set pre-define filter available
     *
     * @param filterType type of filter want to apply {@link ja.burhanrashid52.photoeditor.PhotoEditorImpl}
     */
    void setFilterEffect(PhotoFilter filterType);

    /**
     * Save the edited image on given path
     *
     * @param imagePath      path on which image to be saved
     * @param onSaveListener callback for saving image
     * @see OnSaveListener
     */
    @RequiresPermission(allOf = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void saveAsFile(@NonNull String imagePath, @NonNull ja.burhanrashid52.photoeditor.PhotoEditor.OnSaveListener onSaveListener);


    /**
     * Save the edited image on given path
     *
     * @param imagePath      path on which image to be saved
     * @param saveSettings   builder for multiple save options {@link SaveSettings}
     * @param onSaveListener callback for saving image
     * @see OnSaveListener
     */
    @SuppressLint("StaticFieldLeak")
    @RequiresPermission(allOf = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void saveAsFile(@NonNull String imagePath,
                    @NonNull SaveSettings saveSettings,
                    @NonNull ja.burhanrashid52.photoeditor.PhotoEditor.OnSaveListener onSaveListener);


    /**
     * Save the edited image as bitmap
     *
     * @param onSaveBitmap callback for saving image as bitmap
     * @see OnSaveBitmap
     */
    @SuppressLint("StaticFieldLeak")
    void saveAsBitmap(@NonNull OnSaveBitmap onSaveBitmap);

    /**
     * Save the edited image as bitmap
     *
     * @param saveSettings builder for multiple save options {@link SaveSettings}
     * @param onSaveBitmap callback for saving image as bitmap
     * @see OnSaveBitmap
     */
    @SuppressLint("StaticFieldLeak")
    void saveAsBitmap(@NonNull SaveSettings saveSettings,
                      @NonNull OnSaveBitmap onSaveBitmap);

    /**
     * Callback on editing operation perform on {@link ja.burhanrashid52.photoeditor.PhotoEditorView}
     *
     * @param onPhotoEditorListener {@link OnPhotoEditorListener}
     */
    void setOnPhotoEditorListener(@NonNull OnPhotoEditorListener onPhotoEditorListener);

    /**
     * Check if any changes made need to save
     *
     * @return true if nothing is there to change
     */
    boolean isCacheEmpty();

    /**
     * Builder pattern to define {@link ja.burhanrashid52.photoeditor.PhotoEditor} Instance
     */
    class Builder {

        Context context;
        ja.burhanrashid52.photoeditor.PhotoEditorView parentView;
        ImageView imageView;
        View deleteView;
        DrawingView drawingView;
        Typeface textTypeface;
        Typeface emojiTypeface;
        // By default, pinch-to-scale is enabled for text
        boolean isTextPinchScalable = true;

        /**
         * Building a PhotoEditor which requires a Context and PhotoEditorView
         * which we have setup in our xml layout
         *
         * @param context         context
         * @param photoEditorView {@link ja.burhanrashid52.photoeditor.PhotoEditorView}
         */
        public Builder(Context context, ja.burhanrashid52.photoeditor.PhotoEditorView photoEditorView) {
            this.context = context;
            parentView = photoEditorView;
            imageView = photoEditorView.getSource();
            drawingView = photoEditorView.getDrawingView();
        }

        Builder setDeleteView(View deleteView) {
            this.deleteView = deleteView;
            return this;
        }

        /**
         * set default text font to be added on image
         *
         * @param textTypeface typeface for custom font
         * @return {@link Builder} instant to build {@link ja.burhanrashid52.photoeditor.PhotoEditor}
         */
        public Builder setDefaultTextTypeface(Typeface textTypeface) {
            this.textTypeface = textTypeface;
            return this;
        }

        /**
         * set default font specific to add emojis
         *
         * @param emojiTypeface typeface for custom font
         * @return {@link Builder} instant to build {@link ja.burhanrashid52.photoeditor.PhotoEditor}
         */
        public Builder setDefaultEmojiTypeface(Typeface emojiTypeface) {
            this.emojiTypeface = emojiTypeface;
            return this;
        }

        /**
         * Set false to disable pinch-to-scale for text inserts.
         * Set to "true" by default.
         *
         * @param isTextPinchScalable flag to make pinch to zoom for text inserts.
         * @return {@link Builder} instant to build {@link ja.burhanrashid52.photoeditor.PhotoEditor}
         */
        public Builder setPinchTextScalable(boolean isTextPinchScalable) {
            this.isTextPinchScalable = isTextPinchScalable;
            return this;
        }

        /**
         * @return build PhotoEditor instance
         */
        public ja.burhanrashid52.photoeditor.PhotoEditor build() {
            return new ja.burhanrashid52.photoeditor.PhotoEditorImpl(this);
        }
    }


    /**
     * A callback to save the edited image asynchronously
     */
    interface OnSaveListener {

        /**
         * Call when edited image is saved successfully on given path
         *
         * @param imagePath path on which image is saved
         */
        void onSuccess(@NonNull String imagePath);

        /**
         * Call when failed to saved image on given path
         *
         * @param exception exception thrown while saving image
         */
        void onFailure(@NonNull Exception exception);
    }


    // region Shape
    /**
     * Update the current shape to be drawn,
     * through the use of a ShapeBuilder.
     */
    void setShape(ShapeBuilder shapebuilder);
    // endregion

}
