package refresh;

import be.View;
import javafx.collections.ObservableList;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class ChangesListener {

    private static ChangesListener INSTANCE;
    private Notification notification;

    public static ChangesListener getInstance() {
        if (INSTANCE == null){
            INSTANCE = new ChangesListener();
        }
        return INSTANCE;
    }

    private ChangesListener(){
        notification = Notification.getInstance();
    }

    public void listenForChanges(ObservableList<View> viewList, int departmentID) {
        fileChangeListener(viewList);
    }

    private void fileChangeListener(ObservableList<View> viewList){
        ArrayList pathArrayList = getThePath(viewList);
        ArrayList fileArrayList = getTheFile(viewList);

        try (WatchService watchService = FileSystems.getDefault().newWatchService()){
            for (int i = 0; i < pathArrayList.size(); i++){
                Path path = Path.of(String.valueOf(pathArrayList.get(i)));
                path.register(watchService,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);
            }
            while (true){
                WatchKey key = watchService.take();
                for (WatchEvent event: key.pollEvents()){
                    for (Object file : fileArrayList){
                        if (file.equals(event.context())){
                            System.out.println("Changes have been made");
                            System.out.println(event.kind() + ": " + event.context() );
                        }
                    }
                }
                boolean valid = key.reset();
                if (!valid){
                    break;
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList getThePath(ObservableList<View> viewList){
        ArrayList arrayList = new ArrayList();
        for (View view : viewList){
            if (new File(view.getPath()).isFile()) {
                File file = new File(view.getPath());
                arrayList.add(file.getParent());
            }
        }
        return arrayList;
    }

    private ArrayList getTheFile(ObservableList<View> viewList){
        ArrayList fileArrayList = new ArrayList();
        for (View view : viewList){
            if (new File(view.getPath()).isFile()) {
                Path path = Paths.get(view.getPath());
                fileArrayList.add(path.getFileName());
            }
        }
        return fileArrayList;
    }
}
