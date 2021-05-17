package dal;

import be.View;

import java.util.List;

public interface IContentRepository {
    List<View> getContent(int departmentID);
    void createContent(int departmentID,String title,String path, int column, int row);
    void deleteContent(int departmentID,int column, int row);
}
