package ru.lokincompany.lokenginesceneeditor.ui.composite;

import ru.lokincompany.lokengine.applications.ApplicationDefault;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPositionAlgorithms;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokenginesceneeditor.LESEApplication;
import ru.lokincompany.lokenginesceneeditor.sceneintegration.HighlightedObject;
import ru.lokincompany.lokenginesceneeditor.ui.basic.ObjectProperties;
import ru.lokincompany.lokenginesceneeditor.ui.basic.ObjectsListCanvas;
import ru.lokincompany.lokenginesceneeditor.ui.basic.SceneEditorMenu;
import ru.lokincompany.lokenginesceneeditor.ui.basic.SelectComponentWindow;
import ru.lokincompany.lokenginesceneeditor.ui.basic.notification.NotificationListCanvas;

public class SceneEditor extends GUICanvas {

    public NotificationListCanvas notificationListCanvas;
    public SelectComponentWindow selectComponentWindow;
    public ObjectsListCanvas objectsList;
    public ObjectProperties objectProperties;
    ApplicationDefault application;
    GUICanvas mainCanvas;
    SceneEditorMenu menu;

    public SceneEditor() {
        super(new Vector2i(), new Vector2i());
        this.application = LESEApplication.getInstance();

        mainCanvas = new GUICanvas(new Vector2i(0, 12), new Vector2i());
        mainCanvas.setSize(guiObject -> new Vector2i(this.application.window.getResolution().x, this.application.window.getResolution().y - 12));
        setSize(guiObject -> this.application.window.getResolution());

        objectsList = new ObjectsListCanvas(new Vector2i(), new Vector2i(150, 0), application.scene);
        objectsList.setSize(guiObject -> new Vector2i(guiObject.getSize().x, this.application.window.getResolution().y));

        menu = new SceneEditorMenu(new Vector2i(), 12, application.canvas);

        objectProperties = new ObjectProperties(new Vector2i(), new Vector2i(150, 0));
        objectProperties.setSize(guiObject -> new Vector2i(guiObject.getSize().x, this.application.window.getResolution().y));

        notificationListCanvas = new NotificationListCanvas(new Vector2i(), new Vector2i(200, application.window.getResolution().y));
        notificationListCanvas.setSize(guiObject -> new Vector2i(200, application.window.getResolution().y));

        selectComponentWindow = new SelectComponentWindow(new Vector2i());
        selectComponentWindow.setPosition(GUIPositionAlgorithms.getAlgorithm(application.canvas, GUIPosition.Center).calculate(selectComponentWindow));
        selectComponentWindow.hidden = true;

        mainCanvas.addObject(objectsList);
        mainCanvas.addObject(selectComponentWindow);
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
