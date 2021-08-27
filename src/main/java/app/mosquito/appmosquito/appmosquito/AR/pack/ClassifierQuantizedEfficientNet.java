
package app.mosquito.appmosquito.appmosquito.AR.pack;

import android.app.Activity;

import java.io.IOException;


public class ClassifierQuantizedEfficientNet extends Classifier {


  public ClassifierQuantizedEfficientNet(Activity activity, Device device, int numThreads)
      throws IOException {
    super(activity, device, numThreads);
  }

  @Override
  protected String getModelPath() {

    return "efficientnet-lite0-int8.tflite";
  }
}
