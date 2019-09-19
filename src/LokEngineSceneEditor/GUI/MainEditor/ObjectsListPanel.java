package LokEngineSceneEditor.GUI.MainEditor;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.GUIObjects.GUIFreeTextDrawer;
import LokEngine.GUI.GUIObjects.GUIPanel;
import LokEngine.GUI.GUIObjects.GUIText;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.ColorRGB;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.GUIElement;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

import static LokEngineSceneEditor.GUI.MainEditor.MainEditor.*;
import static LokEngineSceneEditor.Lang.MainLang.coutObjectsOnScene;

public class ObjectsListPanel extends GUIElement {

    GUIPanel panel;
    GUIText headText;
    GUIFreeTextDrawer drawerText;

    Vector2i objectsListSize;
    Vector2i objectSizeInList;
    Color objectNameColor = new ColorRGB(101, 214, 182);

    @Override
    public void init(GUICanvas canvas) {
        objectsListSize = new Vector2i(200, canvas.getSize().y);
        objectSizeInList = new Vector2i(200,20);

        panel = new GUIPanel(new Vector2i(0,0), objectsListSize, backgroundColor, blurTuning);
        headText = new GUIText(new Vector2i(0,0),"", standOutTextColor,0,12);
        drawerText = new GUIFreeTextDrawer("",0,14,true);
        headText.canResize = true;

        canvas.addObject(panel);
        canvas.addObject(headText);
        canvas.addObject(drawerText);
    }

    @Override
    public void update() {
        Scene activeScene = RuntimeFields.getScene();

        int countObjects = activeScene.getCountObjects();
        headText.updateText(coutObjectsOnScene + countObjects);

        boolean mousePressed = RuntimeFields.getMouseStatus().getPressedStatus();
        //boolean mouseInListField = Misc.mouseInField(new Vector2i(0,0), objectsListSize);
        for (int i = 0; i < countObjects; i++){

            SceneObject sceneObject = activeScene.getObjectByID(i);
            boolean mouseInTextField = Misc.mouseInField(new Vector2i(0,20 + objectSizeInList.y * i), objectSizeInList);
            boolean selected = sceneObject == ObjectHighlight.getHighlightedObject();
            Color color = objectNameColor;

            if (selected){
                color = standOutTextColor;
            }

            if (mouseInTextField && mousePressed) {
                ObjectHighlight.highlight(sceneObject, i);
                RuntimeFields.getFrameBuilder().window.getCamera().position = new Vector2f(sceneObject.position.x,sceneObject.position.y);
                color = standOutTextColor;
            }else if (mouseInTextField){
                color = standOutTextColor;
            }
            //ObjectHighlight.highlight(null);

            drawerText.draw(sceneObject.name + " (" + i + ")", new Vector2i(selected ? 10 : 0,20 + objectSizeInList.y * i), color);
        }
    }
}
