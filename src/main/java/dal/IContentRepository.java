package dal;

import be.View;

import java.util.List;

public interface IContentRepository {
    List<View> getContent(int departmentID);
}
