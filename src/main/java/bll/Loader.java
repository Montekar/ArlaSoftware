package bll;

import be.View;
import dal.LoadDepartmentParts;

import java.util.ArrayList;

public class Loader {

    private LoadDepartmentParts loadDepartmentParts = new LoadDepartmentParts();


    public ArrayList<View> getScreenParts(String department){
        return loadDepartmentParts.getScreenParts(department);
    }


}
