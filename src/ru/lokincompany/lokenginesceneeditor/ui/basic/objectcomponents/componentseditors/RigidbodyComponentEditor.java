package ru.lokincompany.lokenginesceneeditor.ui.basic.objectcomponents.componentseditors;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.guiobjects.GUIFreeTextDrawer;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.sceneenvironment.components.RigidbodyComponent;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.rigidbody.Rigidbody;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class RigidbodyComponentEditor extends ComponentEditor {

    GUIFreeTextDrawer textDrawer;
    RigidbodyComponent component;
    int fontHeight;

    public RigidbodyComponentEditor(RigidbodyComponent component) {
        super("Rigidbody component");

        this.component = component;
        textDrawer = new GUIFreeTextDrawer(0, 10, true);
        fontHeight = textDrawer.getFont().getFontHeight();
        textDrawer.setSize(guiObject -> new Vector2i(getSize().x, fontHeight * 4));

        canvas.addObject(textDrawer);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        Rigidbody rigidbody = component.getRigidbody();

        textDrawer.draw("Плотность: " + rigidbody.density, new Vector2i(3, 0));
        textDrawer.draw("Трение: " + rigidbody.friction, new Vector2i(3, fontHeight));
        textDrawer.draw("Сенсор: " + (rigidbody.isSensor ? "да" : "нет"), new Vector2i(3, fontHeight * 2));
        textDrawer.draw("Статический: " + (rigidbody.isStatic ? "да" : "нет"), new Vector2i(3, fontHeight * 3));
    }
}
