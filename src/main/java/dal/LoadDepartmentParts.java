package dal;

import be.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadDepartmentParts {

    private ArrayList<View> viewArrayList = new ArrayList<>();
    private View view;
    private File file = new File("src/main/resources/department/cocio");

    public ArrayList<View> getScreenParts(String department) {

        try (Scanner scanner = new Scanner(file)){
        while (scanner.hasNext()){
            String data[] = scanner.nextLine().split(" ");
            System.out.println(data.length);
            view = new View(data[0], data[1]);
            viewArrayList.add(view);
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return viewArrayList;
    }
}
