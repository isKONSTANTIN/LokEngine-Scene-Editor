package LokEngineSceneEditor.GUI;

import LokEngine.Components.Component;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.GUIObjects.GUIFreeTextDrawer;
import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;
import org.lwjgl.input.Mouse;

import static LokEngineSceneEditor.GUI.MainStyle.*;
import static LokEngineSceneEditor.GUI.SceneObjectsListPanel.freeTextDrawer;

public class SceneObjectComponentsPanel extends GUIObject {

    GUIFreeTextDrawer freeTextDrawer;
    Vector2i textGap;
    GUICanvas canvas;

    public static Component selectedComponent;

    public SceneObjectComponentsPanel(Vector2i position, Vector2i size) {
        super(position, size);
        freeTextDrawer = new GUIFreeTextDrawer("",0,12,true);
        textGap = new Vector2i(0,15);
        canvas = new GUICanvas(position,size);

        canvas.addObject(freeTextDrawer);
    }

    @Override
    public void update(PartsBuilder partsBuilder, Vector2i globalPos){
        SceneObject sceneObject = ObjectHighlight.getHighlightedObject();

        if (sceneObject != null){
            int componentsSize = sceneObject.components.getSize();

            boolean thisIsThatObject = false;

            for (int i = 0; i < componentsSize; i++){
                Component component = sceneObject.components.get(i);

                Vector2i textPos = new Vector2i(getPosition().x,textGap.y * i + getPosition().y);
                boolean selected = Misc.mouseInField(textPos, new Vector2i(getSize().x, textGap.y));

                if (selected && RuntimeFields.getMouseStatus().getPressedStatus()){
                    selectedComponent = component;
                }

                if (selectedComponent != null && !thisIsThatObject){
                    thisIsThatObject = selectedComponent == component;
                }

                freeTextDrawer.draw(component.getName(), new Vector2i(0,textGap.y * i), selected || selectedComponent == component ? highlightedTextColor : textColor);
            }

            if (selectedComponent != null && !thisIsThatObject){
                selectedComponent = null;
            }

            canvas.update(partsBuilder,globalPos);
        }
    }

}
