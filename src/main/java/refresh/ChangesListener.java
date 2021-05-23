package refresh;

import be.View;
import be.users.User;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class ChangesListener {

    private static ChangesListener INSTANCE;
    private Notification notification;

    public static ChangesListener getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChangesListener();
        }
        return INSTANCE;
    }

    private ChangesListener() {
        notification = Notification.getInstance();
    }

    /*
        Main method that listens for file change and reloads the view when needed.
        Implemented by using the WatchService a builtin function that extends closable and registers
        objects for changes and events.
     */
    public void listenForChanges(ObservableList<View> viewList, User department, Stage stage) {
        ArrayList pathArrayList = getThePath(viewList);
        ArrayList fileArrayList = getTheFile(viewList);
        String message = "Changes have been made the view will update";

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            for (int i = 0; i < pathArrayList.size(); i++) {
                Path path = Path.of(String.valueOf(pathArrayList.get(i)));
                path.register(watchService,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);
            }
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent event : key.pollEvents()) {
                    for (Object file : fileArrayList) {
                        if (file.equals(event.context())) {
                            System.out.println("Changes have been made");
                            System.out.println(event.kind() + ": " + event.context());
                        }
                    }
                }
                notification.displayAlert(department, stage, message);
                boolean valid = key.reset();
                if (valid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    // Method provides the directory to the WatchService
    private ArrayList getThePath(ObservableList<View> viewList) {
        ArrayList arrayList = new ArrayList();
        for (View view : viewList) {
            if (new File(view.getPath()).isFile()) {
                File file = new File(view.getPath());
                arrayList.add(file.getParent());
            }
        }
        return arrayList;
    }

    // Method provides the files to the WatchService
    private ArrayList getTheFile(ObservableList<View> viewList) {
        ArrayList fileArrayList = new ArrayList();
        for (View view : viewList) {
            if (new File(view.getPath()).isFile()) {
                Path path = Paths.get(view.getPath());
                fileArrayList.add(path.getFileName());
            }
        }
        return fileArrayList;
    }
}
