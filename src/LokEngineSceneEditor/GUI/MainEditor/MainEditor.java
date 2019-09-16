package LokEngineSceneEditor.GUI.MainEditor;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.GUIFreeTextDrawer;
import LokEngine.GUI.GUIObjects.GUIPanel;
import LokEngine.GUI.GUIObjects.GUIText;
import LokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import LokEngine.Render.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.ColorRGB;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static LokEngineSceneEditor.Lang.MainLang.coutObjectsOnScene;

public class MainEditor {

    public static GUICanvas mainEditorCanvas;

    public static BlurTuning blurTuning = new BlurTuning();
    public static Color backgroundColor = new ColorRGB(61, 61, 61,128);
    public static Color textColor = new ColorRGB(182, 182, 182);
    public static Color standOutTextColor = new ColorRGB(209, 149, 0);

    static ObjectsListPanel objectsListPanel = new ObjectsListPanel();
    static ObjectPropertiesPanel objectPropertiesPanel = new ObjectPropertiesPanel();

    public static void init(Window window, GUICanvas mainCanvas){
        mainEditorCanvas = new GUICanvas(new Vector2i(0,0), window.getResolution());

        objectsListPanel.init(mainEditorCanvas);
        objectPropertiesPanel.init(mainEditorCanvas);

        mainCanvas.addObject(mainEditorCanvas);
    }

    public static void update(){
        objectsListPanel.update();
        objectPropertiesPanel.update();
    }

}
