package ru.lokincompany.lokenginesceneeditor.ui.basic.notification.notificationtypes;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.gui.guiobjects.GUIPanel;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokenginesceneeditor.ui.Colors;

public class NotificationObject extends GUIObject {
    GUIText text;
    GUIPanel panel;
    long addTime;
    int showTime;

    public NotificationObject(String text, Color color, int showTime) {
        super(new Vector2i(), new Vector2i());

        this.text = new GUIText(new Vector2i(), text, Colors.white(), 0, 11);
        this.panel = new GUIPanel(new Vector2i(), new Vector2i(), color);

        this.panel.setPosition(guiObject -> getPosition());
        this.panel.setSize(guiObject -> getSize());
        this.text.setPosition(guiObject -> getPosition());

        this.showTime = showTime;

        addTime = System.currentTimeMillis();
    }

    public NotificationObject(String text, Color color) {
        this(text, color, 5);
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
    }

    public void show() {
        addTime = System.currentTimeMillis();
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        if (System.currentTimeMillis() - addTime < showTime * 1000) {
            panel.update(partsBuilder, properties);
            text.setMaxSize(panel.getSize());
            text.update(partsBuilder, properties);
        }
    }
}
