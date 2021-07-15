package app.mosquito.appmosquito.appmosquito.GalleryPhotos.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import app.mosquito.appmosquito.appmosquito.GalleryPhotos.utils.imageIndicatorListener;
import app.mosquito.appmosquito.appmosquito.GalleryPhotos.utils.pictureFacer;
import app.mosquito.appmosquito.appmosquito.R;

import static androidx.core.view.ViewCompat.setTransitionName;


public class pictureBrowserFragment extends Fragment implements imageIndicatorListener {

    private  ArrayList<pictureFacer> allImages = new ArrayList<>();
    private int position;
    private Context animeContx;
    private ImageView image;
    private ViewPager imagePager;
    private RecyclerView indicatorRecycler;
    private int viewVisibilityController;
    private int viewVisibilitylooper;
    private ImagesPagerAdapter pagingImages;
    private int previousSelected = -1;


    private final static String TAG = "MainActivity";

    private ImageView mContentImageView;
    private ImageView mStyleImageView;

    private Bitmap mContentImage;
    private Bitmap mStyleImage;

    private RadioGroup mRadioGroup;
    private ImageView mTransferButton;

    private final static int REQUEST_CONTENT_IMG = 1;
    private final static int REQUEST_STYLE_IMG = 2;
    Interpreter interpreter;
    private final static int STYLE_IMG_SIZE = 256;
    private final static int CONTENT_IMG_SIZE = 384;
    private final static int DIM_BATCH_SIZE = 1;
    private final static int DIM_PIXEL_SIZE = 3;

    private final static int USING_CPU = 1;
    private final static int USING_GPU = 2;
    private final static int USING_NNAPI = 3;

    private int mDelegationMode = USING_CPU;

    private final static String PREDICT_MODEL = "model.tflite";
    private final static String TRANSFORM_MODE = "style_transform.tflite";

    private final static int IMAGE_MEAN = 0;
    private final static int IMAGE_STD = 255;


    public pictureBrowserFragment(){

    }

    public pictureBrowserFragment(ArrayList<pictureFacer> allImages, int imagePosition, Context anim) {
        this.allImages = allImages;
        this.position = imagePosition;
        this.animeContx = anim;
    }

    public static pictureBrowserFragment newInstance(ArrayList<pictureFacer> allImages, int imagePosition, Context anim) {
        pictureBrowserFragment fragment = new pictureBrowserFragment(allImages,imagePosition,anim);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.picture_browser, container, false);

        mContentImageView =  root.findViewById(R.id.imasddgeda10);

        mStyleImageView =  root.findViewById(R.id.imasddageda10);



        return root;
    }

    private float[][][][] bitmapToInputArrayRed(Bitmap bitmap) {


        int batchNum = 0;
        float[][][][] input = new float[1][360][360][1];
        for (int x = 0; x < 360; x++) {
            for (int y = 0; y < 360; y++) {
                int pixel = bitmap.getPixel(x, y);

                input[batchNum][x][y][0] = (Color.red(pixel))/ 255.0f;

            }
        }

        return input;
    }

    private float[][][][] bitmapToInputArrayGreen(Bitmap bitmap) {


        int batchNum = 0;
        float[][][][] input = new float[1][360][360][1];
        for (int x = 0; x < 360; x++) {
            for (int y = 0; y < 360; y++) {
                int pixel = bitmap.getPixel(x, y);

                input[batchNum][x][y][0] = (Color.red(pixel))/ 255.0f;

            }
        }

        return input;
    }

    private float[][][][] bitmapToInputArrayBlue(Bitmap bitmap) {


        int batchNum = 0;
        float[][][][] input = new float[1][360][360][1];
        for (int x = 0; x < 360; x++) {
            for (int y = 0; y < 360; y++) {
                int pixel = bitmap.getPixel(x, y);

                input[batchNum][x][y][0] = (Color.red(pixel))/ 255.0f;

            }
        }

        return input;
    }


    private ByteBuffer runPredict(Interpreter tflite, Bitmap styleImage) {

        TensorImage inputTensorImage = getInputTensorImage(tflite, styleImage);
        Tensor outputTensor = tflite.getOutputTensor(/* outputTensorIndex */ 0);
        TensorBuffer outputTensorBuffer = TensorBuffer.createFixedSize(outputTensor.shape(), outputTensor.dataType());
        tflite.run(inputTensorImage.getBuffer(), outputTensorBuffer.getBuffer());

        return outputTensorBuffer.getBuffer();
    }

    private TensorImage getInputTensorImage(Interpreter tflite, Bitmap inputBitmap) {

        DataType imageDataType = tflite.getInputTensor(/* imageTensorIndex */0).dataType();
        TensorImage inputTensorImage = new TensorImage(imageDataType);
        inputTensorImage.load(inputBitmap);

        ImageProcessor imageProcessor =
                new ImageProcessor.Builder().add(new ResizeOp(256, 256, ResizeOp.ResizeMethod.BILINEAR)).build();

        return imageProcessor.process(inputTensorImage);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewVisibilityController = 0;
        viewVisibilitylooper = 0;
        imagePager = view.findViewById(R.id.imagePager);
        pagingImages = new ImagesPagerAdapter();
        imagePager.setAdapter(pagingImages);
        imagePager.setOffscreenPageLimit(3);
        imagePager.setCurrentItem(position);
        imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(previousSelected != -1){
                    allImages.get(previousSelected).setSelected(false);
                    previousSelected = position;
                    allImages.get(position).setSelected(true);
                    indicatorRecycler.getAdapter().notifyDataSetChanged();
                    indicatorRecycler.scrollToPosition(position);
                }else{
                    previousSelected = position;
                    allImages.get(position).setSelected(true);
                    indicatorRecycler.getAdapter().notifyDataSetChanged();
                    indicatorRecycler.scrollToPosition(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onImageIndicatorClicked(int ImagePosition) {

        //the below lines of code highlights the currently select image in  the indicatorRecycler with respect to the viewPager position
        if(previousSelected != -1){
            allImages.get(previousSelected).setSelected(false);
            previousSelected = ImagePosition;
            indicatorRecycler.getAdapter().notifyDataSetChanged();
        }else{
            previousSelected = ImagePosition;
        }

        imagePager.setCurrentItem(ImagePosition);
    }

    private class ImagesPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return allImages.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup containerCollection, int position) {
            LayoutInflater layoutinflater = (LayoutInflater) containerCollection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutinflater.inflate(R.layout.picture_browser_pager,null);
               image = view.findViewById(R.id.image);

            setTransitionName(image, String.valueOf(position)+"picture");

            pictureFacer pic = allImages.get(position);
            Glide.with(animeContx)
                    .load(pic.getPicturePath())
                    .apply(new RequestOptions().fitCenter())
                    .into(image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



            ((ViewPager) containerCollection).addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup containerCollection, int position, Object view) {
            ((ViewPager) containerCollection).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((View) object);
        }
    }


    private MappedByteBuffer loadModelFile(String file) throws IOException {
        AssetFileDescriptor assetFileDescriptor = getContext().getAssets().openFd(file);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();

        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


}
