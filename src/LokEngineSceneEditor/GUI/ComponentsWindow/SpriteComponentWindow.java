package LokEngineSceneEditor.GUI.ComponentsWindow;

import LokEngine.Components.SpriteComponent;
import LokEngine.GUI.AdditionalObjects.GUIButtonScript;
import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.GUI.AdditionalObjects.GUITextFieldScript;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.SceneObjectComponentsPanel;
import LokEngineSceneEditor.SceneInteraction.CameraMovement;

import java.awt.*;

import static LokEngineSceneEditor.GUI.MainStyle.*;

public class SpriteComponentWindow extends GUIObject {

    GUISubWindow subWindow;

    GUIListCanvas list;

    public SpriteComponentWindow(Vector2i position, Vector2i size) {
        super(position, size);

        subWindow = new GUISubWindow(position,size,true,
                new GUIText(new Vector2i(),"","Sprite component settings",textColor,0,12,false,false),
                new GUIPanel(new Vector2i(), new Vector2i(), panelsColor, panelsBlur)
        );

        subWindow.canvas.addObject(new GUIPanel(new Vector2i(),subWindow.canvas.getSize(), panelsColor, panelsBlur));

        GUIButton applyButton = new GUIButton(new Vector2i(0,subWindow.canvas.getSize().y - 35),new Vector2i(size.x,35), pressedButtonColor, buttonColor,
                new GUIText(new Vector2i(),"Apply",textColor,0,14),
                new GUIPanel(new Vector2i(),new Vector2i())
        );

        applyButton.setUnpressScript(guiButton -> {
            SceneObjectComponentsPanel.selectedComponent = null;
        });

        list = new GUIListCanvas(new Vector2i(), size, new Vector2i(size.x,25));
        list.addObject(new GUIText(new Vector2i(), "Sprite path:"));
        list.addObject(new GUITextField(new Vector2i(),new Vector2i(),"",textColor,0,14));
        list.addObject(new GUISpace());
        list.addObject(new GUIText(new Vector2i(), "Sprite size:"));
        list.addObject(new GUITextField(new Vector2i(),new Vector2i(),"",textColor,0,14));

        GUITextField pathField = (GUITextField)list.getObject(1);
        GUITextField sizeField = (GUITextField)list.getObject(4);

        pathField.setStatusChangedScript(guiTextField -> {
            if (SceneObjectComponentsPanel.selectedComponent != null && SceneObjectComponentsPanel.selectedComponent.getName().equals("Sprite Component")) {
                SpriteComponent component = (SpriteComponent) SceneObjectComponentsPanel.selectedComponent;
                try {
                    component.setSprite(guiTextField.getText());
                } catch (Exception e) {}
            }
        });

        pathField.setInactiveScript(guiTextField -> {
            if (SceneObjectComponentsPanel.selectedComponent != null && SceneObjectComponentsPanel.selectedComponent.getName().equals("Sprite Component")) {
                SpriteComponent component = (SpriteComponent) SceneObjectComponentsPanel.selectedComponent;
                if (!guiTextField.getActive()) {
                    guiTextField.updateText(component.getSprite().texture.path);
                }
            }
        });

        sizeField.setStatusChangedScript(guiTextField -> {
            if (SceneObjectComponentsPanel.selectedComponent != null && SceneObjectComponentsPanel.selectedComponent.getName().equals("Sprite Component")) {
                SpriteComponent component = (SpriteComponent) SceneObjectComponentsPanel.selectedComponent;
                try {
                    component.getSprite().size = Float.valueOf(guiTextField.getText());
                } catch (Exception e) {}
            }
        });

        sizeField.setInactiveScript(guiTextField -> {
            if (SceneObjectComponentsPanel.selectedComponent != null && SceneObjectComponentsPanel.selectedComponent.getName().equals("Sprite Component")) {
                SpriteComponent component = (SpriteComponent) SceneObjectComponentsPanel.selectedComponent;
                if (!guiTextField.getActive()) {
                    guiTextField.updateText(String.valueOf(component.getSprite().size));
                }
            }
        });

        subWindow.canvas.addObject(applyButton);
        subWindow.canvas.addObject(list);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties){
        super.update(partsBuilder,parentProperties);
        if (SceneObjectComponentsPanel.selectedComponent != null && SceneObjectComponentsPanel.selectedComponent.getName().equals("Sprite Component")){
            subWindow.update(partsBuilder, parentProperties);
            CameraMovement.accepted = false;
        }
    }


}
