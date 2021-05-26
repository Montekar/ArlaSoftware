package bll.gloader;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ErrorChart implements IChartLoader{

    // Custom error view for charts
    @Override
    public Node loadChart(String path, String nameColumn, String dataColumn) {
        VBox mainBox = new VBox();
        Label message = new Label();
        message.setText("An error occurred while loading your chart");
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/images/chartError.gif");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        mainBox.getChildren().addAll(message,imageView);
        return mainBox;
    }
}
