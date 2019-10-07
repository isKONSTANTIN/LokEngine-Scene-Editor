package LokEngineSceneEditor.GUI;

import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.GUI.AdditionalObjects.GUITextFieldScript;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;

import static LokEngineSceneEditor.GUI.MainStyle.*;

public class SceneObjectPropertiesPanel extends GUIObject {

    GUIPanel panel;
    GUIListCanvas textFields;
    GUIListCanvas texts;
    GUICanvas canvas;

    GUITextField nameField;

    boolean[] lastActives = new boolean[5];
    SceneObject sceneObject;
    public SceneObjectPropertiesPanel(Vector2i position, Vector2i size) {
        super(position, size);
        panel = new GUIPanel(new Vector2i(), size, panelsColor, panelsBlur);

        canvas = new GUICanvas(position, size);

        nameField = new GUITextField(new Vector2i(), new Vector2i(size.x, 20),"","",textColor,0,14,true, false);
        textFields = new GUIListCanvas(new Vector2i(18, 15), new Vector2i(size.x - 18,size.y), new Vector2i(size.x - 18,25));
        texts = new GUIListCanvas(new Vector2i(0,15), new Vector2i(75,size.y), new Vector2i(75,25));

        GUITextField XField = new GUITextField(new Vector2i());
        GUITextField YField = new GUITextField(new Vector2i());
        GUITextField RField = new GUITextField(new Vector2i());
        GUITextField RPField = new GUITextField(new Vector2i());

        XField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.position.x = Float.valueOf(guiTextField.getText());
                } catch (Exception e) {}
        });

        XField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(String.valueOf(sceneObject.position.x));
                } catch (Exception e){}
        });

        YField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.position.y = Float.valueOf(guiTextField.getText());
                } catch (Exception e){}
        });

        YField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(String.valueOf(sceneObject.position.y));
                } catch (Exception e){}
        });

        RField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.rollRotation = Float.valueOf(guiTextField.getText());
                } catch (Exception e){}
        });

        RField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(String.valueOf(sceneObject.rollRotation));
                } catch (Exception e){}
        });

        RPField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.renderPriority = Float.valueOf(guiTextField.getText());
                } catch (Exception e){}
        });

        RPField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(String.valueOf(sceneObject.renderPriority));
                } catch (Exception e){}
        });

        nameField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.name = guiTextField.getText();
                } catch (Exception e){}
        });

        nameField.setInactiveScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    guiTextField.updateText(sceneObject.name);
                } catch (Exception e){}
        });

        textFields.addObject(XField);
        textFields.addObject(YField);
        textFields.addObject(new GUISpace());
        textFields.addObject(RField);
        textFields.addObject(new GUISpace());
        textFields.addObject(RPField);

        texts.addObject(new GUIText(new Vector2i(), "X:"));
        texts.addObject(new GUIText(new Vector2i(), "Y:"));
        texts.addObject(new GUISpace());
        texts.addObject(new GUIText(new Vector2i(), "R:"));
        texts.addObject(new GUISpace());
        texts.addObject(new GUIText(new Vector2i(), "RP:"));

        canvas.addObject(panel);
        canvas.addObject(nameField);
        canvas.addObject(texts);
        canvas.addObject(textFields);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties){
        super.update(partsBuilder,parentProperties);
        sceneObject = ObjectHighlight.getHighlightedObject();
        if (sceneObject != null){
            canvas.update(partsBuilder, parentProperties);
        }
    }
}
