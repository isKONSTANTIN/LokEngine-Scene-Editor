package ru.lokincompany.lokenginesceneeditor.ui.basic.objectcomponents;


import ru.lokincompany.lokengine.components.*;
import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUIFullFlexibleListCanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIScrollCanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokenginesceneeditor.sceneintegration.HighlightedObject;
import ru.lokincompany.lokenginesceneeditor.ui.basic.objectcomponents.componentseditors.*;

import java.util.ArrayList;

public class ObjectComponents extends GUIScrollCanvas {

    GUIFullFlexibleListCanvas content;
    ArrayList<String> componentsList = new ArrayList<>();
    GUILocationAlgorithm componentEditorsSize;
    ComponentsList lastComponents;

    public ObjectComponents(Vector2i position, Vector2i size) {
        super(position, size, new Vector2i(), null);

        content = new GUIFullFlexibleListCanvas(new Vector2i(), true, 5);
        content.autoX = false;
        content.setSize(guiObject -> new Vector2i(this.getSize().x, content.getSize().y));
        componentEditorsSize = guiObject -> new Vector2i(getSize().x, guiObject.getSize().y);

        this.addObject(content);
    }

    private void updateContent(ComponentsList components) {
        content.removeAll();
        componentsList.clear();
        for (int i = 0; i < components.getSize(); i++) {
            Component component = components.get(i);
            String componentName = component.getClass().getName();

            if (componentName.equals(SpriteComponent.class.getName())) {
                SpriteComponentEditor spriteComponentEditor = new SpriteComponentEditor((SpriteComponent) component);
                spriteComponentEditor.setSize(componentEditorsSize);

                componentsList.add(SpriteComponent.class.getName());
                content.addObject(spriteComponentEditor);
            } else if (componentName.equals(AnimationComponent.class.getName())) {
                AnimationComponentEditor animationComponentEditor = new AnimationComponentEditor((AnimationComponent) component);
                animationComponentEditor.setSize(componentEditorsSize);

                componentsList.add(AnimationComponent.class.getName());
                content.addObject(animationComponentEditor);
            } else if (componentName.equals(RigidbodyComponent.class.getName())) {
                RigidbodyComponentEditor rigidbodyComponentEditor = new RigidbodyComponentEditor((RigidbodyComponent) component);
                rigidbodyComponentEditor.setSize(componentEditorsSize);

                componentsList.add(RigidbodyComponent.class.getName());
                content.addObject(rigidbodyComponentEditor);
            } else if (componentName.equals(SoundComponent.class.getName())) {
                SoundComponentEditor soundComponentEditor = new SoundComponentEditor();
                soundComponentEditor.setSize(componentEditorsSize);

                componentsList.add(SoundComponent.class.getName());
                content.addObject(soundComponentEditor);
            } else if (componentName.equals(ParticleSystemComponent.class.getName())) {
                ParticleSystemComponentEditor particleSystemComponentEditor = new ParticleSystemComponentEditor();
                particleSystemComponentEditor.setSize(componentEditorsSize);

                componentsList.add(ParticleSystemComponent.class.getName());
                content.addObject(particleSystemComponentEditor);
            }

        }
    }

    public void updateContent() {
        updateContent(lastComponents);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        ComponentsList components = HighlightedObject.getHighlightedObject().components;

        if (lastComponents != components || components.getSize() > componentsList.size()) {
            updateContent(components);
            lastComponents = components;
        } else {
            for (int i = 0; i < components.getSize(); i++) {
                String componentName = components.get(i).getClass().getName();
                boolean coincidence = false;

                for (String componentNameInList : componentsList) {
                    coincidence = componentName.equals(componentNameInList);
                    if (coincidence) break;
                }

                if (!coincidence) {
                    updateContent();
                    break;
                }
            }
        }

        super.update(partsBuilder, parentProperties);
    }
}
