package app.mosquito.appmosquito.appmosquito.Editor.Shapes;



interface BrushViewChangeListener {
    void onViewAdd(DrawingView drawingView);

    void onViewRemoved(DrawingView drawingView);

    void onStartDrawing();

    void onStopDrawing();
}
