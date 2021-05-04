package bll;

import javafx.scene.Node;
import javafx.scene.web.WebView;

public class WebViewLoader implements IViewLoader {


    @Override
    public Node loadView(String path) {
        WebView webView = new WebView();
        webView.getEngine().load(path);
        return webView;
    }
}
