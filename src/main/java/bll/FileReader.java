package bll;

import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public List<String> loadData(String path) {
        List<String> data = new ArrayList<>();
        try {
            Scanner csvScanner = new Scanner(new File(path));
            while (csvScanner.hasNext()) {
                data.add(csvScanner.next());
            }
            csvScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int[] getDataPosition(List data, String nameColumn, String dataColumn) {
        int[] position = new int[2];
        String firstLine = (String) data.get(0);
        System.out.println(firstLine);
        String[] itemArray = firstLine.split(",");
        String[] itemsToFound = {nameColumn, dataColumn};
        for (String toBeFound: itemsToFound){
            for (int i = 0; i < itemArray.length; i++){
                if (toBeFound.equals(itemArray[i])){
                    position = ArrayUtils.add(position, i);
                }
            }
        }
        return position;
    }
}
