package LokEngineSceneEditor.UI.Basic.Notification.NotificationTypes;

import LokEngineSceneEditor.UI.Colors;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIPanel;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIText;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.ColorRGB;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class NotificationObject extends GUIObject {
    GUIText text;
    GUIPanel panel;
    long addTime;
    int showTime;

    public NotificationObject(String text, Color color, int showTime) {
        super(new Vector2i(), new Vector2i());

        this.text = new GUIText(new Vector2i(), text, Colors.white(),0,11);
        this.panel = new GUIPanel(new Vector2i(),new Vector2i(), color);

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

    public void show(){
        addTime = System.currentTimeMillis();
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        if (System.currentTimeMillis() - addTime < showTime * 1000){
            panel.update(partsBuilder, properties);
            text.update(partsBuilder, properties);
        }
    }
}
