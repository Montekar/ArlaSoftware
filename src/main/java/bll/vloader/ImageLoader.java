package bll.vloader;

import be.View;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLoader implements IViewLoader{

    /*
        Image loader loads image. It streams the path and if it finds and image
        it adds it. The image is displayed inside of a Image View.
     */
    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream(view.getPath())));
            Pane pane = new Pane(imageView);
            imageView.fitWidthProperty().bind(pane.widthProperty());
            imageView.fitHeightProperty().bind(pane.heightProperty());
            return pane;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new Pane();
    }
}
