package bll;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;

public class PathManager {

    public ContentType getType(String path) {
        File file = new File(path);

        if (file.isFile()) {
            if (hasExtension(ContentType.CSV, path)) {
                return ContentType.CSV;
            } else if (hasExtension(ContentType.XLS, path)) {
                return ContentType.XLS;
            } else if (hasExtension(ContentType.PDF, path)) {
                return ContentType.PDF;
            } else if (hasExtension(ContentType.JPG, path)) {
                return ContentType.JPG;
            }
        } else if (UrlValidator.getInstance().isValid(path)) {
            return ContentType.WEB;
        }

        return null;
    }

    private boolean hasExtension(ContentType type, String path) {
        String ext = FilenameUtils.getExtension(path.toUpperCase());
        return type.toString().equalsIgnoreCase(ext);
    }
}
