package bll.vloader;

import javafx.scene.Node;
import javafx.scene.web.WebView;

public class WebViewLoader implements IViewLoader {

    /*
        Loads and web page by using a build in web view that
        has and engine method which loads the web page.
     */
    @Override
    public Node loadView(String path) {
        WebView webView = new WebView();
        webView.getEngine().load(path);
        return webView;
    }
}
