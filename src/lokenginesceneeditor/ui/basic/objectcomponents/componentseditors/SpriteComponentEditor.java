package lokenginesceneeditor.ui.basic.objectcomponents.componentseditors;

import lokenginesceneeditor.LESEApplication;
import lokenginesceneeditor.ui.Colors;
import lokenginesceneeditor.ui.basic.notification.notificationtypes.NotificationWarning;
import ru.lokincompany.lokengine.components.SpriteComponent;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.canvases.GUIFlexibleListCanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIFullFlexibleListCanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.*;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public class SpriteComponentEditor extends ComponentEditor {
    SpriteComponent component;

    public SpriteComponentEditor(SpriteComponent component) {
        super("Sprite component");
        this.component = component;

        GUIFullFlexibleListCanvas list = new GUIFullFlexibleListCanvas(new Vector2i());
        list.autoX = false;
        list.setSize(guiObject -> new Vector2i(getSize().x, guiObject.getSize().y));

        list.addObject(new GUIText(new Vector2i(), "Sprite path:", Colors.engineMainColor(), 0, 10));
        list.addObject(new GUITextField(new Vector2i(), new Vector2i(),"", Colors.white(),0,14));
        list.addObject(new GUISpace(new Vector2i(), new Vector2i(0,5)));
        list.addObject(new GUIText(new Vector2i(), "Sprite size:", Colors.engineMainColor(), 0, 10));
        list.addObject(new GUITextField(new Vector2i(),new Vector2i(),"", Colors.white(),0,14));

        GUITextField pathField = (GUITextField)list.getObject(1);
        GUITextField sizeField = (GUITextField)list.getObject(4);

        pathField.setSize(guiObject -> new Vector2i(getSize().x, 14));
        sizeField.setSize(guiObject -> new Vector2i(getSize().x,14));

        pathField.setBackgroundColor(Colors.setAlpha(Colors.engineBackgroundColor(),0.4f));
        sizeField.setBackgroundColor(Colors.setAlpha(Colors.engineBackgroundColor(),0.4f));

        pathField.setStatusChangedScript(guiTextField -> {
            try {
                component.setSprite(guiTextField.getText());
            } catch (Exception e) {
                LESEApplication.getInstance().sceneEditor.notificationListCanvas.addNotification(new NotificationWarning("Не удалось загрузить спрайт!"));
            }
        });

        pathField.setInactiveScript(guiTextField -> {
                if (!guiTextField.getActive()) {
                    guiTextField.updateText(component.getSprite().texture.path);
                }
        });

        sizeField.setStatusChangedScript(guiTextField -> {
                try {
                    component.getSprite().size = Float.parseFloat(guiTextField.getText());
                } catch (Exception e) {}
        });

        sizeField.setInactiveScript(guiTextField -> {
            if (!guiTextField.getActive()) {
                guiTextField.updateText(String.valueOf(component.getSprite().size));
            }
        });

        canvas.addObject(list);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
    }
}
