package app.mosquito.appmosquito.appmosquito.AR.Engine;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

public class SurfaceComponent extends GLSurfaceView {

    public EngineRenderer cr;

    public SurfaceComponent(Context context) {
        super(context);
        cr = new EngineRenderer();
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.setRenderer(cr);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        this.getHolder().setFormat(PixelFormat.TRANSPARENT);
    }


}