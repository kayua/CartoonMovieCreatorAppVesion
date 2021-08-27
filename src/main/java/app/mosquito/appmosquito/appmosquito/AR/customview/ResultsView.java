package app.mosquito.appmosquito.appmosquito.AR.customview;

import java.util.List;

import app.mosquito.appmosquito.appmosquito.AR.models.Classifier;

public interface ResultsView {
  public void setResults(final List<Classifier.Recognition> results);
}
