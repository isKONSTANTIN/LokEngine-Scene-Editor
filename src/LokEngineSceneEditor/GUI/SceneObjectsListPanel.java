package LokEngineSceneEditor.GUI;

import LokEngine.GUI.AdditionalObjects.GUIButtonScript;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Camera;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.ComponentsWindow.SpriteComponentWindow;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;
import org.lwjgl.util.vector.Vector2f;

import static LokEngineSceneEditor.GUI.MainStyle.*;

public class SceneObjectsListPanel extends GUIObject {

    public static GUIFreeTextDrawer freeTextDrawer;
    GUIPanel panel;
    GUICanvas canvas;

    Vector2i textGap;

    public SceneObjectsListPanel(Vector2i position, Vector2i size) {
        super(position, size);
        this.freeTextDrawer = new GUIFreeTextDrawer("",0,12,true);
        textGap = new Vector2i(0,15);
        panel = new GUIPanel(position,size,panelsColor,panelsBlur);

        GUIButton buttonAdd = new GUIButton(new Vector2i(size.x - 20,0),new Vector2i(20,20),panelsColor,panelsColor,
                new GUIText(new Vector2i(),"+",textColor,0,14),
                new GUIPanel(new Vector2i(),new Vector2i()));

        buttonAdd.setPressScript(guiButton -> {
            SceneObject sceneObject = new SceneObject();
            Camera camera = RuntimeFields.getFrameBuilder().window.getCamera();
            sceneObject.position = new Vector2f(camera.position.x,camera.position.y);
            ObjectHighlight.highlight(sceneObject, RuntimeFields.getScene().addObject(sceneObject));
        });

        canvas = new GUICanvas(position,size);

        canvas.addObject(panel);
        canvas.addObject(buttonAdd);
        canvas.addObject(freeTextDrawer);
    }

    @Override
    public void update(PartsBuilder partsBuilder, Vector2i globalPos){
        Vector2i myGlobalPos = new Vector2i(globalPos.x + getPosition().x, globalPos.y + getPosition().y);
        Scene scene = RuntimeFields.getScene();

        int objectsCount = scene.getCountObjects();

        for (int i = 0; i < objectsCount; i++){
            SceneObject sceneObject = scene.getObjectByID(i);
            Vector2i textPos = new Vector2i(getPosition().x,textGap.y * (i+1) + getPosition().y);
            boolean selected = Misc.mouseInField(textPos,new Vector2i(getSize().x,textGap.y));

            if (selected && RuntimeFields.getMouseStatus().getPressedStatus()){
                ObjectHighlight.highlight(sceneObject,i);
            }

            freeTextDrawer.draw(sceneObject.name + " [" + i + "]", textPos, selected || ObjectHighlight.getHighlightedObjectID() == i ? highlightedTextColor : textColor);
        }

        canvas.update(partsBuilder, myGlobalPos);
    }

}
