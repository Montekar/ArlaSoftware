package bll.vloader;

import be.View;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
        Pane pane = new Pane();
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        pane.getChildren().add(imageView);

        pane.widthProperty().addListener((observableValue, number, t1) -> {
            imageView.setFitWidth(t1.doubleValue());
        });
        pane.heightProperty().addListener((observableValue, number, t1) -> {
            imageView.setFitHeight(t1.doubleValue());
        });
        return pane;
    }
}
