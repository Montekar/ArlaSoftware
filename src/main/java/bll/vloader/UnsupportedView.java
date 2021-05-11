package bll.vloader;

import javafx.scene.Node;

public class UnsupportedView implements IViewLoader{
    @Override
    public Node loadView(String path) {
        return null;
    }
}
