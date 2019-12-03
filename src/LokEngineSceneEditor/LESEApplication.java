package LokEngineSceneEditor;

import LokEngineSceneEditor.UI.Basic.ObjectsListCanvas;
import LokEngineSceneEditor.UI.Basic.SceneEditor;
import ru.lokinCompany.lokEngine.Applications.ApplicationDefault;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class LESEApplication extends ApplicationDefault {

    SceneEditor sceneEditor;

    @Override
    protected void initEvent(){
        window.getFrameBuilder().backgroundColor = Colors.engineBackgroundColor();

        sceneEditor = new SceneEditor(this);

        window.getCanvas().addObject(sceneEditor);
    }

    LESEApplication(){
        start(false,true,true, new Vector2i(1280,720), "LokEngine Scene Editor");
    }
}
