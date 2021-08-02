package app.mosquito.appmosquito.appmosquito.Editor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.IOException;

import app.mosquito.appmosquito.appmosquito.Editor.Base.BaseActivity;
import app.mosquito.appmosquito.appmosquito.Editor.Filters.FilterListener;
import app.mosquito.appmosquito.appmosquito.Editor.Filters.FilterViewAdapter;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.OnPhotoEditorListener;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.PhotoEditor;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.PhotoEditorView;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.PhotoFilter;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.SaveSettings;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.TextStyleBuilder;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.ViewType;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.shape.ShapeBuilder;
import app.mosquito.appmosquito.appmosquito.Editor.Shapes.shape.ShapeType;
import app.mosquito.appmosquito.appmosquito.Editor.Tools.EditingToolsAdapter;
import app.mosquito.appmosquito.appmosquito.Editor.Tools.ToolType;
import app.mosquito.appmosquito.appmosquito.R;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static app.mosquito.appmosquito.appmosquito.Editor.FileSaveHelper.isSdkHigherThan28;

public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener,

        View.OnClickListener,
        PropertiesBSFragment.Properties,
        ShapeBSFragment.Properties,
        EmojiBSFragment.EmojiListener,
        StickerBSFragment.StickerListener, EditingToolsAdapter.OnItemSelected, FilterListener {

    private static final String TAG = EditImageActivity.class.getSimpleName();
    public static final String FILE_PROVIDER_AUTHORITY = "com.burhanrashid52.photoeditor.fileprovider";
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    public static final String ACTION_NEXTGEN_EDIT = "action_nextgen_edit";
    public static final String PINCH_TEXT_SCALABLE_INTENT_KEY = "PINCH_TEXT_SCALABLE";
    PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
    private PropertiesBSFragment mPropertiesBSFragment;
    private ShapeBSFragment mShapeBSFragment;
    private ShapeBuilder mShapeBuilder;
    private EmojiBSFragment mEmojiBSFragment;
    private StickerBSFragment mStickerBSFragment;
    private TextView mTxtCurrentTool;
    private Typeface mWonderFont;
    private RecyclerView mRvTools, mRvFilters;
    private final EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
    private final FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);
    private ConstraintLayout mRootView;
    private final ConstraintSet mConstraintSet = new ConstraintSet();
    private boolean mIsFilterVisible;
    ImageView myButton;
    ImageView myButtona;

    View myView;
    boolean isUp;

    @Nullable
    @VisibleForTesting
    Uri mSaveImageUri;
    View conteudo;

    private FileSaveHelper mSaveFileHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        makeFullScreen();
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.sky_blue_color_picker));

        setContentView(R.layout.layout_image_editor_main_view);
        conteudo = findViewById(R.id.imgShare);
        initViews();
        myButton = findViewById(R.id.imgShare);
        myView = findViewById(R.id.myd_view);
        myButtona = findViewById(R.id.imageView25);

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) { onSlideViewButtonClick(view); }

        });

        myButtona.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) { onSlideViewButtonClick(view); }

        });

        handleIntentImage(mPhotoEditorView.getSource());
        mWonderFont = Typeface.createFromAsset(getAssets(), "beyond_wonderland.ttf");
        mPropertiesBSFragment = new PropertiesBSFragment();
        mEmojiBSFragment = new EmojiBSFragment();
        mStickerBSFragment = new StickerBSFragment();
        mShapeBSFragment = new ShapeBSFragment();
        mStickerBSFragment.setStickerListener(this);
        mEmojiBSFragment.setEmojiListener(this);
        mPropertiesBSFragment.setPropertiesChangeListener(this);
        mShapeBSFragment.setPropertiesChangeListener(this);
        LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);
        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvFilters.setLayoutManager(llmFilters);
        mRvFilters.setAdapter(mFilterViewAdapter);
        boolean pinchTextScalable = getIntent().getBooleanExtra(PINCH_TEXT_SCALABLE_INTENT_KEY, true);
        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(pinchTextScalable)
                .build();
        mPhotoEditor.setOnPhotoEditorListener(this);
        mPhotoEditorView.getSource().setImageResource(R.drawable.paris_tower);
        mSaveFileHelper = new FileSaveHelper(this);

    }

    private void handleIntentImage(ImageView source) {

        Intent intent = getIntent();

        if (intent != null) {

             if (Intent.ACTION_EDIT.equals(intent.getAction()) ||
                    ACTION_NEXTGEN_EDIT.equals(intent.getAction())) {

                 try {

                    Uri uri = intent.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    source.setImageBitmap(bitmap);

                 } catch (IOException e) {

                    e.printStackTrace();

                 }

             } else {

                String intentType = intent.getType();

                if (intentType != null && intentType.startsWith("image/")) {

                    Uri imageUri = intent.getData();

                    if (imageUri != null) {

                        source.setImageURI(imageUri);

                    }

                }

             }

        }

    }

    private void initViews() {

        ImageView imgUndo;
        ImageView imgRedo;
        ImageView imgCamera;
        ImageView imgGallery;
        ImageView imgSave;
        ImageView imgClose;
        ImageView imgShare;
        mPhotoEditorView = findViewById(R.id.photoEditorView);
        mTxtCurrentTool = findViewById(R.id.txtCurrentTool);
        mRvTools = findViewById(R.id.rvConstraintTools);

        mRvFilters = findViewById(R.id.rvFilterView);
        mRootView = findViewById(R.id.rootView);
        imgUndo = findViewById(R.id.imgUndo);
        imgUndo.setOnClickListener(this);
        imgRedo = findViewById(R.id.imgRedo);
        imgRedo.setOnClickListener(this);
        imgCamera = findViewById(R.id.imgCamera);
        imgCamera.setOnClickListener(this);
        imgGallery = findViewById(R.id.imgGallery);
        imgGallery.setOnClickListener(this);
        imgSave = findViewById(R.id.imgSave);
        imgSave.setOnClickListener(this);
        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        imgShare = findViewById(R.id.imgShare);
        imgShare.setOnClickListener(this);

    }

    @Override
    public void onEditTextChangeListener(final View rootView, String text, int colorCode) {

        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show(this, text, colorCode);

        textEditorDialogFragment.setOnTextEditorListener((inputText, newColorCode) -> {

            final TextStyleBuilder styleBuilder = new TextStyleBuilder();
            styleBuilder.withTextColor(newColorCode);
            mPhotoEditor.editText(rootView, inputText, styleBuilder);
            mTxtCurrentTool.setText(R.string.label_text);

        });

    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");

    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {

        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgUndo:

                mPhotoEditor.undo();
                break;

            case R.id.imgRedo:

                mPhotoEditor.redo();
                break;

            case R.id.imgSave:

                saveImage();
                break;

            case R.id.imgClose:

                onBackPressed();
                break;

            case R.id.imgShare:

                shareImage();
                break;

            case R.id.imgCamera:

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.imgGallery:

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
                break;

        }

    }

    private void shareImage() {

        if (mSaveImageUri == null) {

            showSnackbar(getString(R.string.msg_save_image_to_share));
            return;

        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, buildFileProviderUri(mSaveImageUri));
        startActivity(Intent.createChooser(intent, getString(R.string.msg_share_image)));

    }

    private Uri buildFileProviderUri(@NonNull Uri uri) {

        return FileProvider.getUriForFile(this,
                FILE_PROVIDER_AUTHORITY,
                new File(uri.getPath()));

    }


    private void saveImage() {

        final String fileName = System.currentTimeMillis() + ".png";
        final boolean hasStoragePermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED;

        if (hasStoragePermission || isSdkHigherThan28()) {

            showLoading("Saving...");
            mSaveFileHelper.createFile(fileName, (fileCreated, filePath, error, uri) -> {

                if (fileCreated) {

                    SaveSettings saveSettings = new SaveSettings.Builder()
                            .setClearViewsEnabled(true)
                            .setTransparencyEnabled(true)
                            .build();

                    mPhotoEditor.saveAsFile(filePath, saveSettings, new PhotoEditor.OnSaveListener() {

                        @Override
                        public void onSuccess(@NonNull String imagePath) {

                            mSaveFileHelper.notifyThatFileIsNowPubliclyAvailable(getContentResolver());
                            hideLoading();
                            showSnackbar("Image Saved Successfully");
                            mSaveImageUri = uri;
                            mPhotoEditorView.getSource().setImageURI(mSaveImageUri);

                        }


                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            hideLoading();
                            showSnackbar("Failed to save Image");

                        }

                    });


                } else {

                    hideLoading();
                    showSnackbar(error);

                }

            });

        } else {

            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case CAMERA_REQUEST:

                    mPhotoEditor.clearAllViews();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mPhotoEditorView.getSource().setImageBitmap(photo);
                    break;

                case PICK_REQUEST:

                    try {
                        mPhotoEditor.clearAllViews();
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);

                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                    break;
            }

        }

    }


    @Override

    public void onColorChanged(int colorCode) {

        mPhotoEditor.setShape(mShapeBuilder.withShapeColor(colorCode));
        mTxtCurrentTool.setText(R.string.label_brush);

    }

    @Override
    public void onOpacityChanged(int opacity) {

        mPhotoEditor.setShape(mShapeBuilder.withShapeOpacity(opacity));
        mTxtCurrentTool.setText(R.string.label_brush);

    }

    @Override
    public void onShapeSizeChanged(int shapeSize) {

        mPhotoEditor.setShape(mShapeBuilder.withShapeSize(shapeSize));
        mTxtCurrentTool.setText(R.string.label_brush);

    }

    @Override
    public void onShapePicked(ShapeType shapeType) {

        mPhotoEditor.setShape(mShapeBuilder.withShapeType(shapeType));

    }

    @Override
    public void onEmojiClick(String emojiUnicode) {

        mPhotoEditor.addEmoji(emojiUnicode);
        mTxtCurrentTool.setText(R.string.label_emoji);

    }

    @Override
    public void onStickerClick(Bitmap bitmap) {

        mPhotoEditor.addImage(bitmap);
        mTxtCurrentTool.setText(R.string.label_sticker);

    }

    @Override
    public void isPermissionGranted(boolean isGranted, String permission) {

        if (isGranted) { saveImage(); }

    }

    private void showSaveDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.msg_save_image));
        builder.setPositiveButton("Save", (dialog, which) -> saveImage());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setNeutralButton("Discard", (dialog, which) -> finish());
        builder.create().show();

    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }

    @Override
    public void onToolSelected(ToolType toolType) {

        switch (toolType) {

            case SHAPE:
                mPhotoEditor.setBrushDrawingMode(true);
                mShapeBuilder = new ShapeBuilder();
                mPhotoEditor.setShape(mShapeBuilder);
                mTxtCurrentTool.setText(R.string.label_shape);
                showBottomSheetDialogFragment(mShapeBSFragment);
                break;

            case TEXT:

                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
                textEditorDialogFragment.setOnTextEditorListener((inputText, colorCode) -> {
                    final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                    styleBuilder.withTextColor(colorCode);

                    mPhotoEditor.addText(inputText, styleBuilder);
                    mTxtCurrentTool.setText(R.string.label_text);
                });
                break;

            case ERASER:

                mPhotoEditor.brushEraser();
                mTxtCurrentTool.setText(R.string.label_eraser_mode);
                break;

            case FILTER:

                mTxtCurrentTool.setText(R.string.label_filter);
                showFilter(true);
                break;

            case EMOJI:

                showBottomSheetDialogFragment(mEmojiBSFragment);
                break;

            case STICKER:

                showBottomSheetDialogFragment(mStickerBSFragment);
                break;

        }

    }

    private void showBottomSheetDialogFragment(BottomSheetDialogFragment fragment) {

        if (fragment == null || fragment.isAdded()) { return; }

        fragment.show(getSupportFragmentManager(), fragment.getTag());

    }


    void showFilter(boolean isVisible) {

        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mRootView);

        if (isVisible) {

            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);

        } else {

            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.END);
        }

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);
        mConstraintSet.applyTo(mRootView);
    }

    @Override
    public void onBackPressed() {

        if (mIsFilterVisible) {

            showFilter(false);
            mTxtCurrentTool.setText(R.string.app_name);

        } else if (!mPhotoEditor.isCacheEmpty()) {

            showSaveDialog();

        } else {

            super.onBackPressed();

        }

    }


    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(600);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.bringToFront();
        myButton.setVisibility(View.INVISIBLE);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setEnabled(false);
        view.setFocusableInTouchMode(false);
        view.setFocusable(false);
        myButton.setVisibility(View.VISIBLE);
        conteudo.bringToFront();


    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(myView);

        } else {
            slideUp(myView);

        }
        isUp = !isUp;
    }

}
