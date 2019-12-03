package LokEngineSceneEditor.UI.Basic;

import LokEngineSceneEditor.SceneIntegration.HighlightedObject;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUILocationAlgorithm;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIPositions.GUIPosition;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUICanvas;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUIScrollCanvas;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIFreeTextDrawer;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIText;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.Scene;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class ObjectsListCanvas extends GUICanvas {

    GUIScrollCanvas scrollCanvas;
    GUIFreeTextDrawer textDrawer;
    GUIText textObjectsCount;
    Scene scene;
    Vector2i textGap = new Vector2i(0,15);

    public ObjectsListCanvas(Vector2i position, Vector2i size, Scene scene) {
        super(position, size);
        this.scene = scene;

        scrollCanvas = new GUIScrollCanvas(new Vector2i(0,10),new Vector2i());
        scrollCanvas.setSize(guiObject -> new Vector2i(this.getSize().x,this.getSize().y - guiObject.getPosition().y));

        textDrawer = new GUIFreeTextDrawer();
        textObjectsCount = new GUIText(new Vector2i(),"",Colors.engineMainColor(),0,11);
        scrollCanvas.addObject(textDrawer);

        this.addObject(textObjectsCount, GUIPosition.TopCenter);
        this.addObject(scrollCanvas);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        int objectsCount = scene.getCountObjects();

        textObjectsCount.updateText("Объектов: " + objectsCount);

        for (int i = 0; i < objectsCount; i++){
            SceneObject sceneObject = scene.getObjectByID(i);
            Vector2i textPos = new Vector2i(getPosition().x,textGap.y * (i+1) + getPosition().y);
            boolean selected = parentProperties.window.getMouse().inField(textPos,new Vector2i(getSize().x,textGap.y));

            if (selected && parentProperties.window.getMouse().getPressedStatus()){
                HighlightedObject.highlight(sceneObject,i);
            }

            textDrawer.draw(sceneObject.name + " [" + i + "]", textPos, selected || HighlightedObject.getHighlightedObjectID() == i ? Colors.engineBrightMainColor() : Colors.engineMainColor());
        }

    }
}
