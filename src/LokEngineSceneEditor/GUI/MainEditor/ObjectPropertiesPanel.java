package LokEngineSceneEditor.GUI.MainEditor;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.GUIPanel;
import LokEngine.GUI.GUIObjects.GUIText;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.GUIElement;

import static LokEngineSceneEditor.GUI.MainEditor.MainEditor.*;

public class ObjectPropertiesPanel extends GUIElement {

    GUIPanel panel;
    GUIListCanvas listCanvas;
    GUIText headText;

    @Override
    public void init(GUICanvas canvas) {
        panel = new GUIPanel(new Vector2i(canvas.getSize().x - 200,0), new Vector2i(200,canvas.getSize().y), backgroundColor, blurTuning);
        headText = new GUIText(new Vector2i(panel.getPosition().x,-10),"", standOutTextColor,0,12);
        listCanvas = new GUIListCanvas(panel.getPosition(),panel.getSize(),new Vector2i(200,350));

        canvas.addObject(panel);
        canvas.addObject(listCanvas);
        canvas.addObject(headText);
    }

    @Override
    public void update() {

    }
}
