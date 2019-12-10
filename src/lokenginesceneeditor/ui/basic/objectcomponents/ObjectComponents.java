package lokenginesceneeditor.ui.basic.objectcomponents;


import lokenginesceneeditor.sceneintegration.HighlightedObject;
import lokenginesceneeditor.ui.basic.objectcomponents.componentseditors.SpriteComponentEditor;
import ru.lokincompany.lokengine.components.Component;
import ru.lokincompany.lokengine.components.ComponentsList;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUIFlexibleListCanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIScrollCanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public class ObjectComponents extends GUIScrollCanvas {

    GUIFlexibleListCanvas content;
    SpriteComponentEditor spriteComponentEditor;

    public ObjectComponents(Vector2i position, Vector2i size) {
        super(position, size,new Vector2i(),null);

        content = new GUIFlexibleListCanvas(new Vector2i(), new Vector2i());
        content.setSize(guiObject -> getSize());

        spriteComponentEditor = new SpriteComponentEditor();
        spriteComponentEditor.setSize(guiObject -> new Vector2i(getSize().x, 0));
        this.addObject(content);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        ComponentsList components = HighlightedObject.getHighlightedObject().components;
        content.removeAll();

        for (int i = 0; i < components.getSize(); i++){
            Component component = components.get(i);

            switch (component.getName()) {
                case "Sprite Component":
                    content.addObject(spriteComponentEditor);
                    spriteComponentEditor.component = component;
                    break;
            }

        }

        super.update(partsBuilder, parentProperties);
    }
}
