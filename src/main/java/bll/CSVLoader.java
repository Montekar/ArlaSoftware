package bll;

import javafx.scene.Node;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVLoader implements IViewLoader {
    @Override
    public Node loadView(String path) {
        ListView csvData = new ListView();
        try {
            Scanner csvScanner = new Scanner(new File(path));
            //csvScanner.useDelimiter(",");
            while (csvScanner.hasNext()) {
                csvData.getItems().add(csvScanner.next());
            }
            csvScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return csvData;
    }
}
