package LokEngineSceneEditor.UI.Basic;

import LokEngineSceneEditor.SceneIntegration.HighlightedObject;
import LokEngineSceneEditor.UI.Colors;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUICanvas;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUIListCanvas;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIPanel;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUISpace;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIText;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUITextField;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class ObjectProperties extends GUICanvas {
    GUIPanel panel;

    GUIListCanvas textFields;
    GUIListCanvas texts;

    GUITextField nameField;
    SceneObject sceneObject;

    public ObjectProperties(Vector2i position, Vector2i size) {
        super(position, size);

        panel = new GUIPanel(new Vector2i(),new Vector2i(), new Color(0.25f,0.25f, 0.25f,0.6f));
        panel.setSize(guiObject -> this.getSize());

        nameField = new GUITextField(new Vector2i(), new Vector2i(size.x, 20),"","", Colors.white(),0,14,true, false);

        texts = new GUIListCanvas(new Vector2i(0,30), new Vector2i(75,0), new Vector2i(75,14));
        texts.setSize(guiObject -> new Vector2i(guiObject.getSize().x, this.getSize().y));

        textFields = new GUIListCanvas(new Vector2i(18, 30), new Vector2i(size.x - 18,size.y), new Vector2i(size.x - 18,14));
        textFields.setSize(guiObject -> new Vector2i(guiObject.getSize().x, this.getSize().y));

        GUITextField XField = new GUITextField(new Vector2i(),new Vector2i(),"",Colors.white(),0,14);
        GUITextField YField = new GUITextField(new Vector2i(),new Vector2i(),"",Colors.white(),0,14);
        GUITextField RField = new GUITextField(new Vector2i(),new Vector2i(),"",Colors.white(),0,14);
        GUITextField RPField = new GUITextField(new Vector2i(),new Vector2i(),"",Colors.white(),0,14);

        XField.setStatusChangedScript(guiTextField -> {
            if (sceneObject != null)
                try {
                    sceneObject.position.x = Float.parseFloat(guiTextField.getText());
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
                    sceneObject.position.y = Float.parseFloat(guiTextField.getText());
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
                    sceneObject.rollRotation = Float.parseFloat(guiTextField.getText());
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
                    sceneObject.renderPriority = Float.parseFloat(guiTextField.getText());
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

        texts.addObject(new GUIText(new Vector2i(),"X:", Colors.white(),0,14));
        texts.addObject(new GUIText(new Vector2i(),"Y:", Colors.white(),0,14));
        texts.addObject(new GUISpace());
        texts.addObject(new GUIText(new Vector2i(),"R:", Colors.white(),0,14));
        texts.addObject(new GUISpace());
        texts.addObject(new GUIText(new Vector2i(),"RP:", Colors.white(),0,10));

        this.addObject(panel);
        this.addObject(nameField);
        this.addObject(texts);
        this.addObject(textFields);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties){
        super.update(partsBuilder,parentProperties);
        sceneObject = HighlightedObject.getHighlightedObject();
    }

}
