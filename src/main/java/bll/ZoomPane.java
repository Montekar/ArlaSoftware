package bll;

import javafx.scene.layout.Pane;

public class ZoomPane extends Pane {
    private double zoom = 1;

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
