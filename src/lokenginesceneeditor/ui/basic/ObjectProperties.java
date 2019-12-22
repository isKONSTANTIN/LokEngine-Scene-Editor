package lokenginesceneeditor.ui.basic;

import lokenginesceneeditor.LESEApplication;
import lokenginesceneeditor.sceneintegration.HighlightedObject;
import lokenginesceneeditor.ui.Colors;
import lokenginesceneeditor.ui.basic.objectcomponents.ObjectComponents;
import ru.lokincompany.lokengine.components.*;
import ru.lokincompany.lokengine.components.componentstools.ShapeCreator;
import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.*;
import ru.lokincompany.lokengine.loaders.SoundLoader;
import ru.lokincompany.lokengine.loaders.SpriteLoader;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.sceneenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.rmi.server.ExportException;

public class ObjectProperties extends GUICanvas {
    GUIPanel panel;
    GUIButton addButton;

    GUIListCanvas textFields;
    GUIListCanvas texts;
    ObjectComponents componentsList;

    GUITextField nameField;
    SceneObject sceneObject;

    public ObjectProperties(Vector2i position, Vector2i size) {
        super(position, size);

        panel = new GUIPanel(new Vector2i(), new Vector2i(), new Color(0.25f, 0.25f, 0.25f, 0.6f));
        panel.setSize(guiObject -> this.getSize());

        nameField = new GUITextField(new Vector2i(), new Vector2i(size.x - 6, 20), "", Colors.white(), 0, 14, true);

        texts = new GUIListCanvas(new Vector2i(3, 30), new Vector2i(75, 100), new Vector2i(75, 14));
        textFields = new GUIListCanvas(new Vector2i(21, 30), new Vector2i(this.getSize().x - 18, 100), new Vector2i(this.getSize().x - 18, 14));

        addButton = new GUIButton(new Vector2i(0, texts.getPosition().y + texts.getSize().y), new Vector2i(0, 14), Colors.engineBackgroundColor(), new GUIText(new Vector2i(), "Добавить компонент", Colors.white(), 0, 10));
        addButton.setSize(guiObject -> new Vector2i(this.getSize().x, 14));
        addButton.setUnpressScript(guiButton -> LESEApplication.getInstance().sceneEditor.selectComponentWindow.sendRequest(
                componentName -> {
                    switch (componentName) {
                        case "Sprite component":
                            sceneObject.components.add(new SpriteComponent(""));
                            break;
                        case "Animation component":
                            sceneObject.components.add(new AnimationComponent());
                            break;
                        case "Rigidbody component":
                            sceneObject.components.add(new RigidbodyComponent(ShapeCreator.CreateCircleShape(100)));
                            break;
                        case "Sound component":
                            try {
                                sceneObject.components.add(new SoundComponent(SoundLoader.loadWAV("")));
                            } catch (Exception e) {}
                            break;
                        case "Particle System component":
                            sceneObject.components.add(new ParticleSystemComponent(SpriteLoader.loadSprite("")));
                            break;
                    }
                }
        ));

        componentsList = new ObjectComponents(new Vector2i(0, addButton.getPosition().y + addButton.getSize().y), new Vector2i());
        componentsList.setSize(guiObject -> new Vector2i(this.getSize().x, this.getSize().y - componentsList.getPosition().y));

        GUILocationAlgorithm fieldSize = guiObject -> new Vector2i(textFields.getSize().x, 14);
        Color fieldBackground = new Color(0, 0, 0, 0);

        GUITextField XField = new GUITextField(new Vector2i(), new Vector2i(), "", Colors.white(), 0, 14);
        XField.setSize(fieldSize);
        XField.setBackgroundColor(fieldBackground);

        GUITextField YField = new GUITextField(new Vector2i(), new Vector2i(), "", Colors.white(), 0, 14);
        YField.setSize(fieldSize);
        YField.setBackgroundColor(fieldBackground);

        GUITextField RField = new GUITextField(new Vector2i(), new Vector2i(), "", Colors.white(), 0, 14);
        RField.setSize(fieldSize);
        RField.setBackgroundColor(fieldBackground);

        GUITextField RPField = new GUITextField(new Vector2i(), new Vector2i(), "", Colors.white(), 0, 14);
        RPField.setSize(fieldSize);
        RPField.setBackgroundColor(fieldBackground);

        XField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.position.x = Float.parseFloat(guiTextField.getText());
                } catch (Exception e) {
                }
        });

        XField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(String.valueOf(sceneObject.position.x));
                } catch (Exception e) {
                }
        });

        YField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.position.y = Float.parseFloat(guiTextField.getText());
                } catch (Exception e) {
                }
        });

        YField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(String.valueOf(sceneObject.position.y));
                } catch (Exception e) {
                }
        });

        RField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.rollRotation = Float.parseFloat(guiTextField.getText());
                } catch (Exception e) {
                }
        });

        RField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(String.valueOf(sceneObject.rollRotation));
                } catch (Exception e) {
                }
        });

        RPField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.renderPriority = Float.parseFloat(guiTextField.getText());
                } catch (Exception e) {
                }
        });

        RPField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(String.valueOf(sceneObject.renderPriority));
                } catch (Exception e) {
                }
        });

        nameField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.name = guiTextField.getText();
                } catch (Exception e) {
                }
        });

        nameField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(sceneObject.name);
                } catch (Exception e) {
                }
        });

        textFields.addObject(XField);
        textFields.addObject(YField);
        textFields.addObject(new GUISpace(new Vector2i(), new Vector2i(0, 4)));
        textFields.addObject(RField);
        textFields.addObject(new GUISpace(new Vector2i(), new Vector2i(0, 4)));
        textFields.addObject(RPField);

        texts.addObject(new GUIText(new Vector2i(), "X:", Colors.white(), 0, 14));
        texts.addObject(new GUIText(new Vector2i(), "Y:", Colors.white(), 0, 14));
        texts.addObject(new GUISpace(new Vector2i(), new Vector2i(0, 4)));
        texts.addObject(new GUIText(new Vector2i(), "R:", Colors.white(), 0, 14));
        texts.addObject(new GUISpace(new Vector2i(), new Vector2i(0, 4)));
        texts.addObject(new GUIText(new Vector2i(), "RP:", Colors.white(), 0, 10));

        this.addObject(panel);
        this.addObject(nameField, GUIPosition.TopCenter);
        this.addObject(texts);
        this.addObject(textFields);
        this.addObject(addButton);
        this.addObject(componentsList);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        sceneObject = HighlightedObject.getHighlightedObject();
    }

}
