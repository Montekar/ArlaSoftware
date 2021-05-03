import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AdminPage.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.sceneProperty().addListener((observableValue, oldScene, newScene) -> {
            newScene.setOnMousePressed(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                }
            });
            newScene.setOnMouseDragged(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                    primaryStage.setY(mouseEvent.getScreenY() - yOffset);
                }
            });
        });

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
