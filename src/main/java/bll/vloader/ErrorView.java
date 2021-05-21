package bll.vloader;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ErrorView implements IViewLoader{
    @Override
    public Node loadView(String path) {
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/images/error.gif");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}
