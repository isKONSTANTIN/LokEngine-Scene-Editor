package lokenginesceneeditor.ui.composite;

import lokenginesceneeditor.LESEApplication;
import lokenginesceneeditor.sceneintegration.HighlightedObject;
import lokenginesceneeditor.ui.basic.ObjectProperties;
import lokenginesceneeditor.ui.basic.ObjectsListCanvas;
import lokenginesceneeditor.ui.basic.SceneEditorMenu;
import lokenginesceneeditor.ui.basic.notification.NotificationListCanvas;
import ru.lokincompany.lokengine.applications.applications.ApplicationDefault;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public class SceneEditor extends GUICanvas {

    ApplicationDefault application;
    GUICanvas mainCanvas;
    ObjectsListCanvas objectsList;
    ObjectProperties objectProperties;
    public NotificationListCanvas notificationListCanvas;
    SceneEditorMenu menu;

    public SceneEditor() {
        super(new Vector2i(), new Vector2i());
        this.application = LESEApplication.getInstance();

        mainCanvas = new GUICanvas(new Vector2i(0,12),new Vector2i());
        mainCanvas.setSize(guiObject -> new Vector2i(this.application.window.getResolution().x,this.application.window.getResolution().y - 12));
        setSize(guiObject -> this.application.window.getResolution());

        objectsList = new ObjectsListCanvas(new Vector2i(), new Vector2i(150,0), application.scene);
        objectsList.setSize(guiObject -> new Vector2i(guiObject.getSize().x, this.application.window.getResolution().y));

        menu = new SceneEditorMenu(new Vector2i(),12, application.window);

        objectProperties = new ObjectProperties(new Vector2i(),new Vector2i(150,0));
        objectProperties.setSize(guiObject -> new Vector2i(guiObject.getSize().x, this.application.window.getResolution().y));

        notificationListCanvas = new NotificationListCanvas(new Vector2i(), new Vector2i(200, application.window.getResolution().y));
        notificationListCanvas.setSize(guiObject -> new Vector2i(200, application.window.getResolution().y));

        mainCanvas.addObject(objectsList);
        mainCanvas.addObject(objectProperties, GUIPosition.TopRight);
        mainCanvas.addObject(notificationListCanvas, GUIPosition.TopRight);

        this.addObject(menu);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        objectProperties.hidden = HighlightedObject.getHighlightedObject() == null;

        mainCanvas.update(partsBuilder, properties);
    }
}
