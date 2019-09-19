package LokEngineSceneEditor.GUI.MainEditor.ComponentsEditors;

import LokEngine.GUI.GUIObjects.GUIPanel;
import LokEngine.GUI.GUIObjects.GUISubWindow;
import LokEngine.GUI.GUIObjects.GUIText;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.ColorRGB;
import LokEngine.Tools.Utilities.Vector2i;

import java.awt.*;

import static LokEngineSceneEditor.GUI.MainEditor.MainEditor.*;

public class SpriteWindow extends GUISubWindow {

    public SpriteWindow(Vector2i position) {
        super(position, new Vector2i(324,612), false,
                new GUIText(new Vector2i(), Font.SERIF,"Sprite edit",textColor,0,12,false,true),
                new GUIPanel(new Vector2i(), new Vector2i(), new Color(backgroundColor.red - 0.1f,backgroundColor.green - 0.1f,backgroundColor.blue - 0.1f,backgroundColor.alpha), blurTuning)
        );
        canvas.addObject(new GUIPanel(new Vector2i(),getSize(), backgroundColor, blurTuning));
    }
}
