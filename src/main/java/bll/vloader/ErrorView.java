package bll.vloader;

import be.View;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/resources/images/error.gif")));
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
