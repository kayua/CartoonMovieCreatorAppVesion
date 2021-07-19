package app.mosquito.appmosquito.appmosquito.Editor.Shapes;

import android.graphics.Bitmap;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;


public class SaveSettings {

    private boolean isTransparencyEnabled;
    private boolean isClearViewsEnabled;
    private Bitmap.CompressFormat compressFormat;
    private int compressQuality;

    boolean isTransparencyEnabled() { return isTransparencyEnabled; }

    boolean isClearViewsEnabled() { return isClearViewsEnabled; }

    Bitmap.CompressFormat getCompressFormat() { return compressFormat; }

    int getCompressQuality() { return compressQuality; }

    private SaveSettings(Builder builder) {

        this.isClearViewsEnabled = builder.isClearViewsEnabled;
        this.isTransparencyEnabled = builder.isTransparencyEnabled;
        this.compressFormat = builder.compressFormat;
        this.compressQuality = builder.compressQuality;

    }

    public static class Builder {

        private boolean isTransparencyEnabled = true;
        private boolean isClearViewsEnabled = true;
        private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
        private int compressQuality = 100;

        public Builder setTransparencyEnabled(boolean transparencyEnabled) {

            isTransparencyEnabled = transparencyEnabled;
            return this;

        }

        public Builder setClearViewsEnabled(boolean clearViewsEnabled) {

            isClearViewsEnabled = clearViewsEnabled;
            return this;

        }

        public Builder setCompressFormat(@NonNull Bitmap.CompressFormat compressFormat) {

            this.compressFormat = compressFormat;
            return this;

        }

        public Builder setCompressQuality(@IntRange(from=0,to=100) int compressQuality) {

            this.compressQuality = compressQuality;
            return this;

        }

        public SaveSettings build() { return new SaveSettings(this); }

    }
}
