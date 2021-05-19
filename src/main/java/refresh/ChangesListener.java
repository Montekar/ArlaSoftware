package refresh;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class ChangesListener {

    private void listenForChanges(ArrayList pathArrayList, ArrayList fileArrayList) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
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
}
