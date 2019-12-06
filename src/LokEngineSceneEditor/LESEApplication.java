package LokEngineSceneEditor;

import LokEngineSceneEditor.UI.Basic.Notification.NotificationTypes.NotificationError;
import LokEngineSceneEditor.UI.Composite.SceneEditor;
import ru.lokinCompany.lokEngine.Applications.ApplicationDefault;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.FileWorker;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

import java.io.IOException;

public class LESEApplication extends ApplicationDefault {

    SceneEditor sceneEditor;
    private static LESEApplication instance;

    public static LESEApplication getInstance(){ return instance; }

    public void loadScene(String path){
        try {
            if (FileWorker.fileExists(path)) {
                FileWorker fileWorker = new FileWorker(path);
                fileWorker.openRead();
                scene.load(fileWorker.read());
                fileWorker.close();
            }
        } catch (Exception e) {
            sceneEditor.notificationListCanvas.addNotification(new NotificationError("Загрузить сцену не удалось!"));
            e.printStackTrace();
        }
    }

    public void saveScene(String path){
        try {
            FileWorker fileWorker = new FileWorker(path);
            fileWorker.openWrite();
            fileWorker.write(scene.save());
            fileWorker.close();
        } catch (Exception e) {
            sceneEditor.notificationListCanvas.addNotification(new NotificationError("Сохранить сцену не удалось!"));
            e.printStackTrace();
        }
    }

    @Override
    protected void initEvent(){
        window.getFrameBuilder().backgroundColor = Colors.engineBackgroundColor();

        sceneEditor = new SceneEditor();
        window.getCanvas().addObject(sceneEditor);
    }

    LESEApplication(){
        start(false,true,true, new Vector2i(1280,720), "LokEngine Scene Editor");
        instance = this;
    }
}
