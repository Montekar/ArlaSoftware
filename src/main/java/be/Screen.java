package be;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicReference;

public class Screen {
    private String title;
    private Node node;

    public Screen(String title, Node node) {
        this.title = title;
        this.node = node;
    }

    public Node getWindow() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(new Label(title),node);
        return vBox;
    }
}
