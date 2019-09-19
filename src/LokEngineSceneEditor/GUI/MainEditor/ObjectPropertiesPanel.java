package LokEngineSceneEditor.GUI.MainEditor;

import LokEngine.Components.Component;
import LokEngine.Components.SpriteComponent;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.GUIElement;
import LokEngineSceneEditor.GUI.MainEditor.ComponentsEditors.SpriteWindow;
import LokEngineSceneEditor.SceneInteraction.CameraMovement;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;

import static LokEngineSceneEditor.GUI.MainEditor.MainEditor.*;

public class ObjectPropertiesPanel extends GUIElement {

    GUIPanel panel;
    GUICanvas propertiesCanvas;
    GUIText headText;

    GUIFreeTextDrawer componentsListDrawer;

    GUIListCanvas TextFieldsListCanvas;
    GUIListCanvas TextsListCanvas;

    Vector2i componentsListPos;

    boolean lastXGUITextFieldActive;
    boolean lastYGUITextFieldActive;
    boolean lastRGUITextFieldActive;
    boolean lastRPGUITextFieldActive;

    SpriteWindow spriteWindow;

    @Override
    public void init(GUICanvas canvas) {
        propertiesCanvas = new GUICanvas(new Vector2i(canvas.getSize().x - 200,0), new Vector2i(200,canvas.getSize().y));

        panel = new GUIPanel(new Vector2i(0,0), propertiesCanvas.getSize(), backgroundColor, blurTuning);
        headText = new GUIText(panel.getPosition(),"", standOutTextColor,0,12);
        headText.canResize = true;

        componentsListDrawer = new GUIFreeTextDrawer("",0,14,true);

        TextFieldsListCanvas = new GUIListCanvas(new Vector2i(20,10),new Vector2i(panel.getSize().x - 40,canvas.getSize().y / 2), new Vector2i(panel.getSize().x - 40,25));
        TextsListCanvas = new GUIListCanvas(new Vector2i(0,10),new Vector2i(panel.getSize().x - 100,canvas.getSize().y / 2),new Vector2i(100,25));

        componentsListPos = new Vector2i(0, 10);

        TextFieldsListCanvas.addObject(new GUITextField(new Vector2i(0,0),new Vector2i(0,0),new GUIText(new Vector2i(0,0), "X")));
        TextFieldsListCanvas.addObject(new GUITextField(new Vector2i(0,0),new Vector2i(0,0),new GUIText(new Vector2i(0,0), "Y")));
        TextFieldsListCanvas.addObject(new GUISpace());
        TextFieldsListCanvas.addObject(new GUITextField(new Vector2i(0,0),new Vector2i(0,0),new GUIText(new Vector2i(0,0), "rotation")));
        TextFieldsListCanvas.addObject(new GUITextField(new Vector2i(0,0),new Vector2i(0,0),new GUIText(new Vector2i(0,0), "renderPriority")));

        TextsListCanvas.addObject(new GUIText(new Vector2i(0,0),"X:"));
        TextsListCanvas.addObject(new GUIText(new Vector2i(0,0),"Y:"));
        TextsListCanvas.addObject(new GUISpace());
        TextsListCanvas.addObject(new GUIText(new Vector2i(0,0),"R:"));
        TextsListCanvas.addObject(new GUIText(new Vector2i(0,0),"RP:"));

        propertiesCanvas.addObject(panel);
        propertiesCanvas.addObject(headText);
        propertiesCanvas.addObject(componentsListDrawer);
        propertiesCanvas.addObject(TextFieldsListCanvas);
        propertiesCanvas.addObject(TextsListCanvas);

        canvas.addObject(propertiesCanvas);

        spriteWindow = new SpriteWindow(new Vector2i(canvas.getSize().x / 2 - 324 / 2,canvas.getSize().y / 2 - 612 / 2));
        spriteWindow.hidden = true;
        CameraMovement.accepted = true;
        canvas.addObject(spriteWindow);
    }

    @Override
    public void update() {
        SceneObject highlightedObject = ObjectHighlight.getHighlightedObject();
        if (highlightedObject != null){
            headText.updateText(highlightedObject.name);

            GUITextField xTextField = (GUITextField)TextFieldsListCanvas.getObject(0);
            GUITextField yTextField = (GUITextField)TextFieldsListCanvas.getObject(1);
            GUITextField rTextField = (GUITextField)TextFieldsListCanvas.getObject(3);
            GUITextField rPTextField = (GUITextField)TextFieldsListCanvas.getObject(4);

            if (!xTextField.getActive() && lastXGUITextFieldActive){
                highlightedObject.position.x = Float.valueOf(xTextField.getGUIText().getText());
            }else if (!xTextField.getActive()){
                xTextField.getGUIText().updateText(String.valueOf(highlightedObject.position.x));
            }

            if (!yTextField.getActive() && lastYGUITextFieldActive){
                highlightedObject.position.y = Float.valueOf(yTextField.getGUIText().getText());
            }else if (!yTextField.getActive()){
                yTextField.getGUIText().updateText(String.valueOf(highlightedObject.position.y));
            }

            if (!rTextField.getActive() && lastRGUITextFieldActive){
                highlightedObject.rollRotation = Float.valueOf(rTextField.getGUIText().getText());
            }else if (!rTextField.getActive()){
                rTextField.getGUIText().updateText(String.valueOf(highlightedObject.rollRotation));
            }

            if (!rPTextField.getActive() && lastRPGUITextFieldActive){
                highlightedObject.renderPriority = Float.valueOf(rPTextField.getGUIText().getText());
            }else if (!rPTextField.getActive()){
                rPTextField.getGUIText().updateText(String.valueOf(highlightedObject.renderPriority));
            }

            lastXGUITextFieldActive = xTextField.getActive();
            lastYGUITextFieldActive = yTextField.getActive();
            lastRGUITextFieldActive = rTextField.getActive();
            lastRPGUITextFieldActive = rPTextField.getActive();

            propertiesCanvas.hidden = false;

            for (int i = 0; i < highlightedObject.components.getSize(); i++){
                Component component = highlightedObject.components.get(i);
                componentsListDrawer.draw(component.getName(),new Vector2i(0,TextsListCanvas.getSize().y + 20 * i));
            }

        }else{
            propertiesCanvas.hidden = true;
        }
    }
}
