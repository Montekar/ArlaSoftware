package bll.vloader;

import be.View;
import com.dansoftware.pdfdisplayer.PDFDisplayer;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;


public class PdfLoader implements IViewLoader{

    /*
        The pdf loader uses a extern library that loads a file that will
        be displayed in a pdf viewer.
     */
    @Override
    public Node loadView(View view) {
        PDFDisplayer displayer = new PDFDisplayer();
        try {
            displayer.displayPdf(new File(view.getPath()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return displayer.toNode();
    }
}
