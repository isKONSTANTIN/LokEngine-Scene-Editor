package LokEngineSceneEditor.GUI.ComponentsWindow;

import LokEngine.Components.SpriteComponent;
import LokEngine.GUI.AdditionalObjects.GUIButtonScript;
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

    boolean[] lastActives = new boolean[2];

    public SpriteComponentWindow(Vector2i position, Vector2i size) {
        super(position, size);

        subWindow = new GUISubWindow(position,size,true,
                new GUIText(new Vector2i(),"","Sprite component settings",textColor,0,12,false,false),
                new GUIPanel(new Vector2i(), new Vector2i(), panelsColor, panelsBlur)
        );

        subWindow.canvas.addObject(new GUIPanel(new Vector2i(),subWindow.canvas.getSize(), panelsColor, panelsBlur));

        GUIButton applyButton = new GUIButton(new Vector2i(0,subWindow.canvas.getSize().y - 35),new Vector2i(size.x,35),panelsColor,panelsColor,
                new GUIText(new Vector2i(),"Apply",textColor,0,14),
                new GUIPanel(new Vector2i(),new Vector2i())
        );

        applyButton.setPressScript(guiButton -> {
            SceneObjectComponentsPanel.selectedComponent = null;
        });

        list = new GUIListCanvas(new Vector2i(), size, new Vector2i(size.x,25));
        list.addObject(new GUIText(new Vector2i(), "Sprite path:"));
        list.addObject(new GUITextField(new Vector2i(), new Vector2i(),new GUIText(new Vector2i(),"",textColor,0,14)));
        list.addObject(new GUISpace());
        list.addObject(new GUIText(new Vector2i(), "Sprite size:"));
        list.addObject(new GUITextField(new Vector2i(), new Vector2i(),new GUIText(new Vector2i(),"",textColor,0,14)));

        subWindow.canvas.addObject(applyButton);
        subWindow.canvas.addObject(list);
    }

    @Override
    public void update(PartsBuilder partsBuilder, Vector2i globalPos){
        if (SceneObjectComponentsPanel.selectedComponent != null && SceneObjectComponentsPanel.selectedComponent.getName().equals("Sprite Component")){

            SpriteComponent component = (SpriteComponent)SceneObjectComponentsPanel.selectedComponent;

            GUITextField pathField = (GUITextField)list.getObject(1);
            GUITextField sizeField = (GUITextField)list.getObject(4);

            if (!pathField.getActive() && lastActives[0]){
                try {
                    component.setSprite(pathField.getGUIText().getText());
                }catch (Exception e){}
            }else if (!pathField.getActive()){
                pathField.getGUIText().updateText(component.getSprite().texture.path);
            }
            lastActives[0] = pathField.getActive();

            if (!sizeField.getActive() && lastActives[1]){
                try {
                    component.getSprite().size = Float.valueOf(sizeField.getGUIText().getText());
                }catch (Exception e){}
            }else if (!sizeField.getActive()){
                sizeField.getGUIText().updateText(String.valueOf(component.getSprite().size));
            }
            lastActives[1] = sizeField.getActive();

            subWindow.update(partsBuilder, globalPos);
            CameraMovement.accepted = false;
        }
    }


}
