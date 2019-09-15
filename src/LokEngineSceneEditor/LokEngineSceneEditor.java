package LokEngineSceneEditor;

import LokEngine.Application;
import LokEngine.Components.ComponentsTools.ShapeCreator;
import LokEngine.Components.RigidbodyComponent;
import LokEngine.Loaders.SpriteLoader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.MainEditor;
import LokEngineSceneEditor.Misc.DefaultFields;
import LokEngineSceneEditor.Render.FrameParts.SceneObjectHighlightFramePart;
import LokEngineSceneEditor.SceneInteraction.CameraMovement;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class LokEngineSceneEditor extends Application {

    @Override
    public void Init(){
        try {
            DefaultFields.highlightSprite = SpriteLoader.loadSprite("#/resources/textures/frame.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainEditor.init(window, canvas);
        RuntimeFields.setSpeedEngine(0);
        RuntimeFields.getFrameBuilder().glSceneClearColor = new Vector3f(0.25f,0.25f,0.25f);
        scene.addObject(new SceneObject());
    }

    @Override
    public void Update(){
        MainEditor.update(scene);
        ObjectHighlight.update();
        CameraMovement.update(window.getCamera());
    }

    @Override
    public void Exit(){

    }

    LokEngineSceneEditor(){
        start(false,true, new Vector2i(1280,720), "LokEngine Scene Editor");
    }
}
