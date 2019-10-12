package LokEngineSceneEditor.GUI.ComponentsWindow;

import LokEngine.Components.RigidbodyComponent;
import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.SceneObjectComponentsPanel;
import LokEngineSceneEditor.Render.FrameParts.ShapesRenderFramePart;
import LokEngineSceneEditor.SceneInteraction.CameraMovement;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;

import static LokEngineSceneEditor.GUI.MainStyle.*;

public class RigidbodyComponentWindow extends GUIObject {

    GUISubWindow subWindow;

    GUICanvas boxShapeCanvas;
    GUICanvas circleShapeCanvas;

    public RigidbodyComponentWindow(Vector2i position, Vector2i size) {
        super(position, size);

        subWindow = new GUISubWindow(position,size,true,
                new GUIText(new Vector2i(),"","Rigidbody component settings",textColor,0,12,false,false),
                new GUIPanel(new Vector2i(), new Vector2i(), panelsColor, panelsBlur)
        );

        subWindow.canvas.addObject(new GUIPanel(new Vector2i(), subWindow.canvas.getSize(), panelsColor, panelsBlur));

        GUIButton applyButton = new GUIButton(new Vector2i(0,subWindow.canvas.getSize().y - 35),new Vector2i(size.x,35),pressedButtonColor, buttonColor,
                new GUIText(new Vector2i(),"Apply",textColor,0,14),
                new GUIPanel(new Vector2i(),new Vector2i())
        );

        applyButton.setUnpressScript(guiButton -> {
            SceneObjectComponentsPanel.selectedComponent = null;
        });

        subWindow.canvas.addObject(applyButton);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties){
        if (SceneObjectComponentsPanel.selectedComponent != null && SceneObjectComponentsPanel.selectedComponent.getName().equals("Rigidbody Component")){
            subWindow.update(partsBuilder, parentProperties);
            if (SceneObjectComponentsPanel.selectedComponent != null){
                CameraMovement.accepted = false;
                parentProperties.window.getFrameBuilder().getScenePartsBuilder().addPart(new ShapesRenderFramePart(((RigidbodyComponent)SceneObjectComponentsPanel.selectedComponent).polygons, ObjectHighlight.getHighlightedObject()));
            }
       }
    }
}
