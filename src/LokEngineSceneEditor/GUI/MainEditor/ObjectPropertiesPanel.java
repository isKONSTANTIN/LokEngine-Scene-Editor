package LokEngineSceneEditor.GUI.MainEditor;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.GUIElement;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;

import static LokEngineSceneEditor.GUI.MainEditor.MainEditor.*;

public class ObjectPropertiesPanel extends GUIElement {

    GUIPanel panel;
    GUICanvas propertiesCanvas;
    GUIText headText;

    GUIFreeTextDrawer textDrawer;

    GUIListCanvas TextFieldsListCanvas;
    GUIListCanvas TextsListCanvas;

    boolean lastXGUITextFieldActive;
    boolean lastYGUITextFieldActive;
    boolean lastRGUITextFieldActive;
    boolean lastRPGUITextFieldActive;

    @Override
    public void init(GUICanvas canvas) {
        propertiesCanvas = new GUICanvas(new Vector2i(canvas.getSize().x - 200,0), new Vector2i(200,canvas.getSize().y));

        panel = new GUIPanel(new Vector2i(canvas.getSize().x - 200,0), new Vector2i(200,canvas.getSize().y), backgroundColor, blurTuning);
        headText = new GUIText(panel.getPosition(),"", standOutTextColor,0,12);
        headText.canResize = true;

        textDrawer = new GUIFreeTextDrawer("Times New Roman",0,24,true);

        TextFieldsListCanvas = new GUIListCanvas(new Vector2i(panel.getPosition().x + 40,panel.getPosition().y + 10),new Vector2i(panel.getSize().x - 40,canvas.getSize().y / 2), new Vector2i(panel.getSize().x - 40,25));
        TextsListCanvas = new GUIListCanvas(new Vector2i(panel.getPosition().x,panel.getPosition().y + 10),new Vector2i(panel.getSize().x - 100,canvas.getSize().y / 2),new Vector2i(100,25));

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
        propertiesCanvas.addObject(textDrawer);
        propertiesCanvas.addObject(TextFieldsListCanvas);
        propertiesCanvas.addObject(TextsListCanvas);

        canvas.addObject(propertiesCanvas);
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
        }else{
            propertiesCanvas.hidden = true;
        }
    }
}