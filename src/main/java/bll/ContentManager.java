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
}
