package LokEngineSceneEditor.UI.Composite;

import LokEngineSceneEditor.LESEApplication;
import LokEngineSceneEditor.UI.Basic.Notification.NotificationListCanvas;
import LokEngineSceneEditor.UI.Basic.ObjectsListCanvas;
import LokEngineSceneEditor.UI.Basic.SceneEditorMenu;
import ru.lokinCompany.lokEngine.Applications.ApplicationDefault;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIPositions.GUIPosition;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUICanvas;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class SceneEditor extends GUICanvas {

    ApplicationDefault application;
    GUICanvas mainCanvas;
    ObjectsListCanvas objectsList;
    public NotificationListCanvas notificationListCanvas;
    SceneEditorMenu menu;

    public SceneEditor() {
        super(new Vector2i(), new Vector2i());
        this.application = LESEApplication.getInstance();

        mainCanvas = new GUICanvas(new Vector2i(0,12),new Vector2i());
        mainCanvas.setSize(guiObject -> new Vector2i(this.application.window.getResolution().x,this.application.window.getResolution().y - 12));
        setSize(guiObject -> this.application.window.getResolution());

        objectsList = new ObjectsListCanvas(new Vector2i(), new Vector2i(100,0), application.scene);
        objectsList.setSize(guiObject -> new Vector2i(guiObject.getSize().x, this.application.window.getResolution().y));

        menu = new SceneEditorMenu(new Vector2i(),12, application.window);

        notificationListCanvas = new NotificationListCanvas(new Vector2i(), new Vector2i(200, application.window.getResolution().y));
        notificationListCanvas.setSize(guiObject -> new Vector2i(200, application.window.getResolution().y));

        mainCanvas.addObject(objectsList);
        mainCanvas.addObject(notificationListCanvas, GUIPosition.TopRight);

        this.addObject(menu);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        mainCanvas.update(partsBuilder, properties);
    }
}
