package LokEngineSceneEditor;

import LokEngine.Application;
import LokEngine.Components.SpriteComponent;
import LokEngine.Loaders.SpriteLoader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.SaveWorker.FileWorker;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.ComponentsWindow.SpriteComponentWindow;
import LokEngineSceneEditor.GUI.SceneObjectComponentsPanel;
import LokEngineSceneEditor.GUI.SceneObjectPropertiesPanel;
import LokEngineSceneEditor.GUI.SceneObjectsListPanel;
import LokEngineSceneEditor.Misc.DefaultFields;
import LokEngineSceneEditor.SceneInteraction.CameraMovement;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class LokEngineSceneEditor extends Application {

    @Override
    public void Init(){
        try {
            if (FileWorker.fileExists("Scene.save")){
                FileWorker fileWorker = new FileWorker("Scene.save");
                fileWorker.openRead();
                scene.load(fileWorker.read());
            }else{
                scene.addObject(new SceneObject());
                scene.addObject(new SceneObject());
                scene.getObjectByID(0).components.add(new SpriteComponent("#/resources/lol.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultFields.highlightSprite = SpriteLoader.loadSprite("#/resources/textures/frame.png");

        canvas.addObject(new SceneObjectsListPanel(new Vector2i(0,0),new Vector2i(150,window.getResolution().y)));
        canvas.addObject(new SceneObjectPropertiesPanel(new Vector2i(window.getResolution().x - 150,0),new Vector2i(150,window.getResolution().y)));
        canvas.addObject(
                new SceneObjectComponentsPanel(new Vector2i(window.getResolution().x - 150,window.getResolution().y / 2), new Vector2i(150,window.getResolution().y / 2))
        );
        canvas.addObject(new SpriteComponentWindow(new Vector2i(window.getResolution().x / 2 - 150,window.getResolution().y / 2 - 150),new Vector2i(300,300)));
        RuntimeFields.setSpeedEngine(0);
        RuntimeFields.getFrameBuilder().glSceneClearColor = new Color(0.25f,0.25f,0.25f, 1f);
    }

    @Override
    public void Update(){
        ObjectHighlight.update();

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && ObjectHighlight.getHighlightedObject() != null){
            ObjectHighlight.moveObjectFromCursor();
        }else{
            CameraMovement.update(window.getCamera());
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DELETE) && ObjectHighlight.getHighlightedObject() != null){
            ObjectHighlight.deleteObjectFromScene();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            close();
    }

    @Override
    public void Exit(){
        try {
            FileWorker fileWorker = new FileWorker("Scene.save");
            fileWorker.openWrite();
            fileWorker.write(scene.save());
            fileWorker.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    LokEngineSceneEditor(){
        start(false,true, new Vector2i(1280,720), "LokEngine Scene Editor");
    }
}
