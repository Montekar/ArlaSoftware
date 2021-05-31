package dal;

import be.View;

import java.util.List;

public interface IContentRepository {
    List<View> getContent(int departmentID);
    int createContent(View view);
    boolean deleteContent(View view);
    boolean editContent(View oldView, View newView);
}
