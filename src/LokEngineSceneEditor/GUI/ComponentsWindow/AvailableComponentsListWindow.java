package LokEngineSceneEditor.GUI.ComponentsWindow;

import LokEngine.GUI.AdditionalObjects.GUIButtonScript;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.SceneInteraction.CameraMovement;

import static LokEngineSceneEditor.GUI.MainStyle.*;

public class AvailableComponentsListWindow extends GUIObject {

    GUISubWindow subWindow;
    GUIListCanvas listCanvas;

    GUIButton spriteComponent;

    public void setGUIButtonScript(GUIButtonScript userScript){

        GUIButtonScript script = guiButton -> {
            userScript.execute(guiButton);
            this.hidden = true;
            CameraMovement.accepted = true;

        };

        spriteComponent.setPressScript(script);
    }

    public AvailableComponentsListWindow(Vector2i position, Vector2i size) {
        super(position, size);

        subWindow = new GUISubWindow(position,size,true,
                new GUIText(new Vector2i(),"","Add component",textColor,0,12,false,false),
                new GUIPanel(new Vector2i(), new Vector2i(), panelsColor, panelsBlur)
        );

        listCanvas = new GUIListCanvas(new Vector2i(),subWindow.canvas.getSize(),new Vector2i(size.x,25));

        spriteComponent = new GUIButton(new Vector2i(),new Vector2i(),panelsColor,"Sprite Component");

        listCanvas.addObject(spriteComponent);

        subWindow.canvas.addObject(listCanvas);
    }

    @Override
    public void update(PartsBuilder partsBuilder, Vector2i globalPos){
        subWindow.update(partsBuilder, globalPos);
        CameraMovement.accepted = false;
    }

}
