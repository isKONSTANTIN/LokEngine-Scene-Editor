package LokEngineSceneEditor.GUI;

import LokEngine.GUI.AdditionalObjects.GUIButtonScript;
import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Camera;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.SceneEnvironment.SceneObject;
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
    Scene scene;
    public SceneObjectsListPanel(Vector2i position, Vector2i size, Scene scene, Camera camera) {
        super(position, size);
        this.scene = scene;
        this.freeTextDrawer = new GUIFreeTextDrawer("",0,12,true);
        textGap = new Vector2i(0,15);
        panel = new GUIPanel(position,size,panelsColor,panelsBlur);

        GUIButton buttonAdd = new GUIButton(new Vector2i(size.x - 20,0),new Vector2i(20,20),panelsColor,panelsColor,
                new GUIText(new Vector2i(),"+",textColor,0,14),
                new GUIPanel(new Vector2i(),new Vector2i()));

        buttonAdd.setPressScript(guiButton -> {
            SceneObject sceneObject = new SceneObject();
            sceneObject.position = new Vector2f(camera.position.x,camera.position.y);
            ObjectHighlight.highlight(sceneObject, scene.addObject(sceneObject));
        });

        canvas = new GUICanvas(position,size);

        canvas.addObject(panel);
        canvas.addObject(buttonAdd);
        canvas.addObject(freeTextDrawer);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties){
        super.update(partsBuilder,parentProperties);

        int objectsCount = scene.getCountObjects();

        for (int i = 0; i < objectsCount; i++){
            SceneObject sceneObject = scene.getObjectByID(i);
            Vector2i textPos = new Vector2i(getPosition().x,textGap.y * (i+1) + getPosition().y);
            boolean selected = parentProperties.window.getMouse().inField(textPos,new Vector2i(getSize().x,textGap.y));

            if (selected && parentProperties.window.getMouse().getPressedStatus()){
                ObjectHighlight.highlight(sceneObject,i);
            }

            freeTextDrawer.draw(sceneObject.name + " [" + i + "]", textPos, selected || ObjectHighlight.getHighlightedObjectID() == i ? highlightedTextColor : textColor);
        }

        canvas.update(partsBuilder, properties);
    }

}
