package bll;

import javafx.scene.layout.Pane;

public class ZoomPane extends Pane {
    private int zoom = 1;

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}
