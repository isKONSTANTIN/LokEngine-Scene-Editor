package LokEngineSceneEditor;

import LokEngine.Application;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.MainEditor;
import org.lwjgl.util.vector.Vector3f;

public class LokEngineSceneEditor extends Application {

    @Override
    public void Init(){
        MainEditor.init(window, canvas);
        RuntimeFields.setSpeedEngine(0);
        RuntimeFields.getFrameBuilder().glSceneClearColor = new Vector3f(0.25f,0.25f,0.25f);
        scene.addObject(new SceneObject());
    }

    @Override
    public void Update(){
        MainEditor.update(scene);
    }

    @Override
    public void Exit(){

    }

    LokEngineSceneEditor(){
        start(false,true, new Vector2i(1280,720), "LokEngine Scene Editor");
    }
}
