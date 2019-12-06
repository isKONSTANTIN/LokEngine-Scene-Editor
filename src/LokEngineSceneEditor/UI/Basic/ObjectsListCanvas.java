package LokEngineSceneEditor.UI.Basic;

import LokEngineSceneEditor.SceneIntegration.HighlightedObject;
import LokEngineSceneEditor.UI.Colors;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUILocationAlgorithm;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIPositions.GUIPosition;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUICanvas;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUIScrollCanvas;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIFreeTextDrawer;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIPanel;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIText;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.Scene;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class ObjectsListCanvas extends GUICanvas {

    GUIScrollCanvas scrollCanvas;
    GUIFreeTextDrawer textDrawer;
    GUIText textObjectsCount;
    GUIPanel panel;
    Scene scene;
    Vector2i textGap = new Vector2i(0,15);

    public ObjectsListCanvas(Vector2i position, Vector2i size, Scene scene) {
        super(position, size);
        this.scene = scene;

        panel = new GUIPanel(new Vector2i(),new Vector2i(), new Color(0.25f,0.25f, 0.25f,0.6f));
        panel.setSize(guiObject -> this.getSize());

        scrollCanvas = new GUIScrollCanvas(new Vector2i(0,10),new Vector2i(),null,new Vector2i());
        scrollCanvas.setSize(guiObject -> new Vector2i(this.getSize().x,this.getSize().y - guiObject.getPosition().y));

        textDrawer = new GUIFreeTextDrawer();
        textObjectsCount = new GUIText(new Vector2i(),"Объектов: 0", Colors.engineMainColor(),0,11);
        scrollCanvas.addObject(textDrawer);

        this.addObject(panel);
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
            Vector2i textPos = new Vector2i(0,textGap.y * (i+1));
            boolean selected = parentProperties.window.getMouse().inField(new Vector2i(scrollCanvas.properties.globalPosition.x + textPos.x, scrollCanvas.properties.globalPosition.y + textPos.y), new Vector2i(getSize().x,textGap.y));

            if (selected && parentProperties.window.getMouse().getPressedStatus()){
                HighlightedObject.highlight(sceneObject,i);
            }

            textDrawer.draw(sceneObject.name + " [" + i + "]", textPos, selected || HighlightedObject.getHighlightedObjectID() == i ? Colors.engineBrightMainColor() : Colors.engineMainColor());
        }

    }
}
