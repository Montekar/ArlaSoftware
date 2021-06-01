package bll.vloader;

import be.View;
import javafx.scene.Node;
import javafx.scene.web.WebView;

public class WebViewLoader implements IViewLoader {

    /*
        Loads and web page by using a build in web view that
        has and engine method which loads the web page.
     */
    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        WebView webView = new WebView();
        webView.getEngine().load(view.getPath());
        if(!autoResizeEnabled){
            webView.setPrefSize(view.getHeight(),view.getWidth());
        }
        return webView;
    }
}
