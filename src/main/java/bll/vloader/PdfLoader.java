package bll.vloader;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;


public class PdfLoader implements IViewLoader{
    @Override
    public Node loadView(String path) {
        PDFDisplayer displayer = new PDFDisplayer();
        try {
            displayer.displayPdf(new File("src/main/resources/mockFiles/uk_arla_consolidated_annual_report_2020.pdf"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return displayer.toNode();
    }
}
