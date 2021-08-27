package app.mosquito.appmosquito.appmosquito.AR.pack;

import android.app.Activity;

import java.io.IOException;

public class ClassifierFloatMobileNet extends Classifier {

  public ClassifierFloatMobileNet(Activity activity, Device device, int numThreads)
      throws IOException {
    super(activity, device, numThreads);
  }

  @Override
  protected String getModelPath() {

    return "mobilenet_v1_1.0_224.tflite";
  }
}
