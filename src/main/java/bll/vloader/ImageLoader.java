package bll.vloader;

import be.View;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLoader implements IViewLoader{

    /*
        Image loader loads image. It streams the path and if it finds and image
        it adds it. The image is displayed inside of a Image View.
     */
    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        FileInputStream input = null;
        try {
            input = new FileInputStream(view.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        if (!autoResizeEnabled) {
            imageView.setFitHeight(view.getHeight());
            imageView.setFitWidth(view.getWidth());
        }
        return imageView;
    }
}
