package dal;

import be.View;

import java.util.ArrayList;

public interface ILoadDepartmentParts {
    ArrayList<View> getScreenParts(int departmentId);
}
