package dal;

import be.View;

import java.util.List;

public interface IContentRepository {
    List<View> getContent(int departmentID);
    void createContent(View view);
    void deleteContent(View view);
    void editContent(View oldView, View newView);
}
