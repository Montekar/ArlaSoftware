package bll;

import be.View;
import dal.IContentRepository;
import dal.db.DBContentRepository;

import java.util.List;

public class ContentManager {
    private final IContentRepository contentRepository;

    public ContentManager() {
        contentRepository = new DBContentRepository();
    }

    public List<View> getContent(int departmentID) {
        return contentRepository.getContent(departmentID);
    }
    public void createContent(int departmentID,String title,String path, int column, int row){
        contentRepository.createContent(departmentID, title, path, column, row);
    }
    public void deleteContent(int departmentID, int column, int row){
        contentRepository.deleteContent(departmentID, column, row);
    }
}
