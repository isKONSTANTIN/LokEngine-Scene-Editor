package LokEngineSceneEditor.GUI;

import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Frame.PartsBuilder;
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

    public SceneObjectPropertiesPanel(Vector2i position, Vector2i size) {
        super(position, size);
        panel = new GUIPanel(new Vector2i(), size, panelsColor, panelsBlur);

        canvas = new GUICanvas(position, size);

        nameField = new GUITextField(new Vector2i(), new Vector2i(size.x, 20),"","",textColor,0,14,true, false);
        textFields = new GUIListCanvas(new Vector2i(18, 15), new Vector2i(size.x - 18,size.y), new Vector2i(size.x - 18,25));
        texts = new GUIListCanvas(new Vector2i(0,15), new Vector2i(75,size.y), new Vector2i(75,25));

        textFields.addObject(new GUITextField(new Vector2i()));
        textFields.addObject(new GUITextField(new Vector2i()));
        textFields.addObject(new GUISpace());
        textFields.addObject(new GUITextField(new Vector2i()));
        textFields.addObject(new GUISpace());
        textFields.addObject(new GUITextField(new Vector2i()));

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
        SceneObject sceneObject = ObjectHighlight.getHighlightedObject();

        if (sceneObject != null){
            GUITextField XField = (GUITextField)textFields.getObject(0);
            GUITextField YField = (GUITextField)textFields.getObject(1);
            GUITextField RField = (GUITextField)textFields.getObject(3);
            GUITextField RPField = (GUITextField)textFields.getObject(5);

            if (!XField.getActive() && lastActives[0]){
                try {
                    sceneObject.position.x = Float.valueOf(XField.getText());
                }catch (Exception e){}
            }else if (!XField.getActive()){
                XField.updateText(String.valueOf(sceneObject.position.x));
            }
            lastActives[0] = XField.getActive();

            if (!YField.getActive() && lastActives[1]){
                try {
                    sceneObject.position.y = Float.valueOf(YField.getText());
                }catch (Exception e){}
            }else if (!YField.getActive()){
                YField.updateText(String.valueOf(sceneObject.position.y));
            }
            lastActives[1] = YField.getActive();

            if (!RField.getActive() && lastActives[2]){
                try {
                    sceneObject.rollRotation = Float.valueOf(RField.getText());
                }catch (Exception e){}
            }else if (!RField.getActive()){
                RField.updateText(String.valueOf(sceneObject.rollRotation));
            }
            lastActives[2] = RField.getActive();

            if (!RPField.getActive() && lastActives[3]){
                try {
                    sceneObject.renderPriority = Float.valueOf(RPField.getText());
                }catch (Exception e){}
            }else if (!RPField.getActive()){
                RPField.updateText(String.valueOf(sceneObject.renderPriority));
            }
            lastActives[3] = RPField.getActive();

            if (!nameField.getActive() && lastActives[4]){
                try {
                    sceneObject.name = nameField.getText();
                }catch (Exception e){}
            }else if (!nameField.getActive()){
                nameField.updateText(sceneObject.name);
            }
            lastActives[4] = nameField.getActive();

            canvas.update(partsBuilder, parentProperties);
        }
    }
}
