package bll.vloader;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;


public class PdfLoader implements IViewLoader{

    /*
        The pdf loader uses a extern library that loads a file that will
        be displayed in a pdf viewer.
     */
    @Override
    public Node loadView(String path) {
        PDFDisplayer displayer = new PDFDisplayer();
        try {
            displayer.displayPdf(new File(path));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return displayer.toNode();
    }
}
