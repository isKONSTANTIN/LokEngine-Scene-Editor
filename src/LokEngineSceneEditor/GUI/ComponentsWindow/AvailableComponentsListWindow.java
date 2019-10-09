package LokEngineSceneEditor.GUI.ComponentsWindow;

import LokEngine.GUI.AdditionalObjects.GUIButtonScript;
import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
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
    GUIButton animationComponent;
    GUIButton rigidbodyBoxComponent;
    GUIButton rigidbodyCircleComponent;

    public void setGUIButtonScript(GUIButtonScript userScript){

        GUIButtonScript script = guiButton -> {
            userScript.execute(guiButton);
            this.hidden = true;
            CameraMovement.accepted = true;

        };

        spriteComponent.setUnpressScript(script);
        animationComponent.setUnpressScript(script);
        rigidbodyBoxComponent.setUnpressScript(script);
        rigidbodyCircleComponent.setUnpressScript(script);
    }

    public AvailableComponentsListWindow(Vector2i position, Vector2i size) {
        super(position, size);

        subWindow = new GUISubWindow(position,size,true,
                new GUIText(new Vector2i(),"","Add component",textColor,0,12,false,false),
                new GUIPanel(new Vector2i(), new Vector2i(), panelsColor, panelsBlur)
        );

        subWindow.canvas.addObject(new GUIPanel(new Vector2i(),subWindow.canvas.getSize(), panelsColor, panelsBlur));

        listCanvas = new GUIListCanvas(new Vector2i(),subWindow.canvas.getSize(),new Vector2i(size.x,25), 5);

        spriteComponent = new GUIButton(new Vector2i(),new Vector2i(),panelsColor,"Sprite Component");
        animationComponent = new GUIButton(new Vector2i(),new Vector2i(),panelsColor,"Animation Component");
        rigidbodyBoxComponent = new GUIButton(new Vector2i(),new Vector2i(),panelsColor,"Rigidbody Component [box]");
        rigidbodyCircleComponent = new GUIButton(new Vector2i(),new Vector2i(),panelsColor,"Rigidbody Component [circle]");

        listCanvas.addObject(spriteComponent);
        listCanvas.addObject(animationComponent);
        listCanvas.addObject(rigidbodyBoxComponent);
        listCanvas.addObject(rigidbodyCircleComponent);

        subWindow.canvas.addObject(listCanvas);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties){
        super.update(partsBuilder,parentProperties);
        subWindow.update(partsBuilder, parentProperties);
        CameraMovement.accepted = false;
    }

}
