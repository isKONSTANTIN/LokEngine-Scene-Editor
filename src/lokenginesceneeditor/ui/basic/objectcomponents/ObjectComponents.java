package lokenginesceneeditor.ui.basic.objectcomponents;


import lokenginesceneeditor.sceneintegration.HighlightedObject;
import lokenginesceneeditor.ui.Colors;
import lokenginesceneeditor.ui.basic.objectcomponents.componentseditors.SpriteComponentEditor;
import org.lwjgl.system.CallbackI;
import ru.lokincompany.lokengine.components.Component;
import ru.lokincompany.lokengine.components.ComponentsList;
import ru.lokincompany.lokengine.components.SpriteComponent;
import ru.lokincompany.lokengine.components.additionalobjects.Sprite;
import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.canvases.GUIFlexibleListCanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIFullFlexibleListCanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIScrollCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIButton;
import ru.lokincompany.lokengine.gui.guiobjects.GUISpace;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

import java.util.ArrayList;

public class ObjectComponents extends GUIScrollCanvas {

    GUIFullFlexibleListCanvas content;
    ArrayList<String> componentsList = new ArrayList<>();
    GUILocationAlgorithm componentEditorsSize;
    ComponentsList lastComponents;

    public ObjectComponents(Vector2i position, Vector2i size) {
        super(position, size, new Vector2i(),null);

        content = new GUIFullFlexibleListCanvas(new Vector2i(), true, 5);
        content.autoX = false;
        content.setSize(guiObject -> new Vector2i(this.getSize().x, content.getSize().y));
        componentEditorsSize = guiObject -> new Vector2i(getSize().x, guiObject.getSize().y);

        this.addObject(content);
    }

    private void updateContent(ComponentsList components){
        content.removeAll();
        componentsList.clear();
        for (int i = 0; i < components.getSize(); i++){
            Component component = components.get(i);

            switch (component.getName()) {
                case "Sprite Component":
                    SpriteComponentEditor spriteComponentEditor = new SpriteComponentEditor((SpriteComponent) component);
                    spriteComponentEditor.setSize(componentEditorsSize);

                    componentsList.add("Sprite Component");
                    content.addObject(spriteComponentEditor);
                    break;
            }

        }
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        ComponentsList components = HighlightedObject.getHighlightedObject().components;

        if (lastComponents != components){
            updateContent(components);
            lastComponents = components;
        }else {
            for (int i = 0; i < components.getSize(); i++ ){
                String componentName = components.get(i).getName();
                boolean coincidence = false;

                for (String componentNameInList : componentsList){
                    coincidence = componentName.equals(componentNameInList);
                    if (coincidence) break;
                }

                if (!coincidence){
                    updateContent(components);
                    break;
                }
            }
        }

        super.update(partsBuilder, parentProperties);
    }
}
