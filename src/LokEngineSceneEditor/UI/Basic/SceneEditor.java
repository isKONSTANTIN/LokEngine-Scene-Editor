package LokEngineSceneEditor.UI.Basic;

import ru.lokinCompany.lokEngine.Applications.ApplicationDefault;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUILocationAlgorithm;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUICanvas;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.Render.Window.Window;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class SceneEditor extends GUICanvas {

    ApplicationDefault application;
    ObjectsListCanvas objectsList;

    public SceneEditor(ApplicationDefault application) {
        super(new Vector2i(), new Vector2i());
        this.application = application;
        setSize(guiObject -> this.application.window.getResolution());

        objectsList = new ObjectsListCanvas(new Vector2i(), new Vector2i(100,0), application.scene);
        objectsList.setSize(guiObject -> new Vector2i(guiObject.getSize().x, this.application.window.getResolution().y));

        this.addObject(objectsList);
    }
}
