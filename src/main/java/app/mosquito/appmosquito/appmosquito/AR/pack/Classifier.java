/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package app.mosquito.appmosquito.appmosquito.AR.pack;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;

import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.metadata.MetadataExtractor;
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions;
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions.Orientation;
import org.tensorflow.lite.task.vision.classifier.Classifications;
import org.tensorflow.lite.task.vision.classifier.ImageClassifier;
import org.tensorflow.lite.task.vision.classifier.ImageClassifier.ImageClassifierOptions;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public abstract class Classifier {
  public static final String TAG = "ClassifierWithTaskApi";

  /** The model type used for classification. */
  public enum Model {
    FLOAT_MOBILENET,
    QUANTIZED_MOBILENET,
    FLOAT_EFFICIENTNET,
    QUANTIZED_EFFICIENTNET
  }

  /** The runtime device type used for executing classification. */
  public enum Device {
    CPU,
    NNAPI,
    GPU
  }

  private static final int MAX_RESULTS = 3;

  private final int imageSizeX;

  private final int imageSizeY;

  protected final ImageClassifier imageClassifier;


  public static Classifier create(Activity activity, Model model, Device device, int numThreads)
      throws IOException {
    if (model == Model.QUANTIZED_MOBILENET) {
      return new ClassifierQuantizedMobileNet(activity, device, numThreads);
    } else if (model == Model.FLOAT_MOBILENET) {
      return new ClassifierFloatMobileNet(activity, device, numThreads);
    } else if (model == Model.FLOAT_EFFICIENTNET) {
      return new ClassifierFloatEfficientNet(activity, device, numThreads);
    } else if (model == Model.QUANTIZED_EFFICIENTNET) {
      return new ClassifierQuantizedEfficientNet(activity, device, numThreads);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  public static class Recognition {

    private final String id;
    private final String title;
    private final Float confidence;
    private RectF location;

    public Recognition(
            final String id, final String title, final Float confidence, final RectF location) {
      this.id = id;
      this.title = title;
      this.confidence = confidence;
      this.location = location;
    }

    public String getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public Float getConfidence() {
      return confidence;
    }

    public RectF getLocation() {
      return new RectF(location);
    }

    public void setLocation(RectF location) {
      this.location = location;
    }

    @Override
    public String toString() {
      String resultString = "";
      if (id != null) {
        resultString += "[" + id + "] ";
      }

      if (title != null) {
        resultString += title + " ";
      }

      if (confidence != null) {
        resultString += String.format("(%.1f%%) ", confidence * 100.0f);
      }

      if (location != null) {
        resultString += location + " ";
      }

      return resultString.trim();
    }
  }

  protected Classifier(Activity activity, Device device, int numThreads) throws IOException {
    if (device != Device.CPU) {
      throw new IllegalArgumentException(
          "Manipulating the hardware accelerators is not allowed in the Task"
              + " library currently. Only CPU is allowed.");
    }

    // Create the ImageClassifier instance.
    ImageClassifierOptions options =
        ImageClassifierOptions.builder()
            .setMaxResults(MAX_RESULTS)
            .setNumThreads(numThreads)
            .build();
    imageClassifier = ImageClassifier.createFromFileAndOptions(activity, getModelPath(), options);
    Log.d(TAG, "Created a Tensorflow Lite Image Classifier.");

    // Get the input image size information of the underlying tflite model.
    MappedByteBuffer tfliteModel = FileUtil.loadMappedFile(activity, getModelPath());
    MetadataExtractor metadataExtractor = new MetadataExtractor(tfliteModel);
    // Image shape is in the format of {1, height, width, 3}.
    int[] imageShape = metadataExtractor.getInputTensorShape(/*inputIndex=*/ 0);
    imageSizeY = imageShape[1];
    imageSizeX = imageShape[2];
  }


  public List<Recognition> recognizeImage(final Bitmap bitmap, int sensorOrientation) {
    // Logs this method so that it can be analyzed with systrace.
    Trace.beginSection("recognizeImage");

    TensorImage inputImage = TensorImage.fromBitmap(bitmap);
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    int cropSize = min(width, height);

    ImageProcessingOptions imageOptions =
        ImageProcessingOptions.builder()
            .setOrientation(getOrientation(sensorOrientation))
            // Set the ROI to the center of the image.
            .setRoi(
                new Rect(
                    /*left=*/ (width - cropSize) / 2,
                    /*top=*/ (height - cropSize) / 2,
                    /*right=*/ (width + cropSize) / 2,
                    /*bottom=*/ (height + cropSize) / 2))
            .build();

    // Runs the inference call.
    Trace.beginSection("runInference");
    long startTimeForReference = SystemClock.uptimeMillis();
    List<Classifications> results = imageClassifier.classify(inputImage, imageOptions);
    long endTimeForReference = SystemClock.uptimeMillis();
    Trace.endSection();
    Log.v(TAG, "Timecost to run model inference: " + (endTimeForReference - startTimeForReference));

    Trace.endSection();

    return getRecognitions(results);
  }

  /** Closes the interpreter and model to release resources. */
  public void close() {
    if (imageClassifier != null) {
      imageClassifier.close();
    }
  }

  public int getImageSizeX() {
    return imageSizeX;
  }

  public int getImageSizeY() {
    return imageSizeY;
  }

  private static List<Recognition> getRecognitions(List<Classifications> classifications) {

    final ArrayList<Recognition> recognitions = new ArrayList<>();
    // All the demo models are single head models. Get the first Classifications in the results.
    for (Category category : classifications.get(0).getCategories()) {
      recognitions.add(
          new Recognition(
              "" + category.getLabel(), category.getLabel(), category.getScore(), null));
    }
    return recognitions;
  }

  /* Convert the camera orientation in degree into {@link ImageProcessingOptions#Orientation}.*/
  private static Orientation getOrientation(int cameraOrientation) {
    switch (cameraOrientation / 90) {
      case 3:
        return Orientation.BOTTOM_LEFT;
      case 2:
        return Orientation.BOTTOM_RIGHT;
      case 1:
        return Orientation.TOP_RIGHT;
      default:
        return Orientation.TOP_LEFT;
    }
  }

  /** Gets the name of the model file stored in Assets. */
  protected abstract String getModelPath();
}
