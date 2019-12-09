package lokenginesceneeditor;

import lokenginesceneeditor.ui.basic.notification.notificationtypes.NotificationError;
import lokenginesceneeditor.ui.basic.notification.notificationtypes.NotificationSuccess;
import lokenginesceneeditor.ui.composite.SceneEditor;
import ru.lokinCompany.lokEngine.Applications.ApplicationDefault;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.FileWorker;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class LESEApplication extends ApplicationDefault {

    SceneEditor sceneEditor;
    private static LESEApplication instance;

    public static LESEApplication getInstance(){ return instance; }

    public void loadScene(String path){
        scene.removeAll();
        try {
            if (FileWorker.fileExists(path)) {
                FileWorker fileWorker = new FileWorker(path);
                fileWorker.openRead();
                scene.load(fileWorker.read());
                fileWorker.close();

                sceneEditor.notificationListCanvas.addNotification(new NotificationSuccess("Загрузка успешна!\nОбъектов загружено: " + scene.getCountObjects()));
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

            sceneEditor.notificationListCanvas.addNotification(new NotificationSuccess("Сохранение успешно!"));
        } catch (Exception e) {
            sceneEditor.notificationListCanvas.addNotification(new NotificationError("Сохранить сцену не удалось!"));
            e.printStackTrace();
        }
    }

    @Override
    protected void initEvent(){
        window.getFrameBuilder().backgroundColor = new Color(0.15F, 0.15F, 0.15F, 1.0F);
        window.setCloseEvent((window1, objects) -> close());

        sceneEditor = new SceneEditor();
        window.getCanvas().addObject(sceneEditor);
    }

    LESEApplication(){
        start(false,true,true, new Vector2i(1280,720), "LokEngine Scene Editor");
        instance = this;
    }
}