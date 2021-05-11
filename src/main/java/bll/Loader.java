package bll;

import be.View;
import dal.ILoadDepartmentParts;
import dal.db.LoadDepartmentParts;

import java.util.ArrayList;

public class Loader {

    private ILoadDepartmentParts loadDepartmentParts = new LoadDepartmentParts();


    public ArrayList<View> getScreenParts(int departmentId){
        return loadDepartmentParts.getScreenParts(departmentId);
    }


}
