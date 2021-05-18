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
    public void createContent(View view){
        contentRepository.createContent(view);
    }
    public void deleteContent(View view){
        contentRepository.deleteContent(view);
    }

    public void editContent(View oldView, View newView){
        contentRepository.editContent(oldView,newView);
    }
}
