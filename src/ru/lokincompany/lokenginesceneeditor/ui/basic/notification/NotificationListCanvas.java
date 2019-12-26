package ru.lokincompany.lokenginesceneeditor.ui.basic.notification;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUIListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokenginesceneeditor.ui.basic.notification.notificationtypes.NotificationObject;

public class NotificationListCanvas extends GUIListCanvas {

    public NotificationListCanvas(Vector2i position, Vector2i size) {
        super(position, size, new Vector2i(size.x, size.y / 10), 5);
    }

    public void addNotification(NotificationObject object) {
        addObject(object, true);
    }

    public void showAll() {
        for (GUIObject object : objects) {
            try {
                ((NotificationObject) object).show();
            } catch (ClassCastException ignored) {
            }
        }
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
    }
}
