package refresh;

import javafx.scene.control.Button;

public class RefreshButton extends Button {

    public RefreshButton(){
        setText("Refresh");
        setOnMouseClicked(event -> {
            System.out.println("Clicked");
        });
    }



}
