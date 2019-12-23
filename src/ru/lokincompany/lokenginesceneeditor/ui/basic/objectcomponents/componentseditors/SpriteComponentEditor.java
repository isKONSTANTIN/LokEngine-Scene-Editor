package ru.lokincompany.lokenginesceneeditor.ui.basic.objectcomponents.componentseditors;

import ru.lokincompany.lokengine.components.SpriteComponent;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUIFullFlexibleListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUISpace;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.gui.guiobjects.GUITextField;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokenginesceneeditor.LESEApplication;
import ru.lokincompany.lokenginesceneeditor.ui.Colors;
import ru.lokincompany.lokenginesceneeditor.ui.basic.notification.notificationtypes.NotificationWarning;

public class SpriteComponentEditor extends ComponentEditor {
    SpriteComponent component;

    public SpriteComponentEditor(SpriteComponent component) {
        super("Sprite component");
        this.component = component;

        GUIFullFlexibleListCanvas list = new GUIFullFlexibleListCanvas(new Vector2i(3, 0));
        list.autoX = false;
        list.setSize(guiObject -> new Vector2i(getSize().x - 3, guiObject.getSize().y));

        list.addObject(new GUIText(new Vector2i(), "Путь спрайта:", Colors.engineMainColor(), 0, 10));
        list.addObject(new GUITextField(new Vector2i(), new Vector2i(), "", Colors.white(), 0, 10));
        list.addObject(new GUISpace(new Vector2i(), new Vector2i(0, 5)));
        list.addObject(new GUIText(new Vector2i(), "Размер спрайта:", Colors.engineMainColor(), 0, 10));
        list.addObject(new GUITextField(new Vector2i(), new Vector2i(), "", Colors.white(), 0, 10));

        GUITextField pathField = (GUITextField) list.getObject(1);
        GUITextField sizeField = (GUITextField) list.getObject(4);

        pathField.setSize(guiObject -> new Vector2i(getSize().x - 6, 14));
        sizeField.setSize(guiObject -> new Vector2i(getSize().x - 6, 14));

        pathField.setBackgroundColor(Colors.setAlpha(Colors.engineBackgroundColor(), 0.4f));
        sizeField.setBackgroundColor(Colors.setAlpha(Colors.engineBackgroundColor(), 0.4f));

        pathField.setStatusChangedScript(guiTextField -> {
            if (guiTextField.getActive()) return;
            try {
                if (!component.getSprite().texture.path.equals(guiTextField.getText()))
                    component.setSprite(guiTextField.getText());
            } catch (Exception e) {
                LESEApplication.getInstance().sceneEditor.notificationListCanvas.addNotification(new NotificationWarning("Не удалось загрузить спрайт!"));
            }
        });

        pathField.setInactiveScript(guiTextField -> {
            guiTextField.updateText(component.getSprite().texture.path);
        });

        sizeField.setStatusChangedScript(guiTextField -> {
            if (guiTextField.getActive()) return;
            try {
                component.getSprite().size = Float.parseFloat(guiTextField.getText());
            } catch (Exception e) {
            }
        });

        sizeField.setInactiveScript(guiTextField -> {
            guiTextField.updateText(String.valueOf(component.getSprite().size));
        });

        canvas.addObject(list);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
    }
}