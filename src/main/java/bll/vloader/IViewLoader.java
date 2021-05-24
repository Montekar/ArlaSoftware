package bll.vloader;

import javafx.scene.Node;

public interface IViewLoader {

    /*
        Interface that routes to the specific file loader
        which hold a loading method unique to the file.
     */
    Node loadView(String path, int width, int height);
}
