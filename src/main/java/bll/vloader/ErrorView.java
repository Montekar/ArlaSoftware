package bll.vloader;

import be.View;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ErrorView implements IViewLoader{

    /*
        Error View is show when the file is not supported or wasn't found.
        The reason for this is to have a better user experience than showing an
        empty window.
     */
    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/images/error.gif");
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
