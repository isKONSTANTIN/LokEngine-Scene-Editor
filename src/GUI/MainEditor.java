package GUI;

import Lang.MainLang;
import Lang.MainLang.*;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.GUIFreeTextDrawer;
import LokEngine.GUI.GUIObjects.GUIPanel;
import LokEngine.GUI.GUIObjects.GUIText;
import LokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import LokEngine.Render.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.ColorRGB;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static Lang.MainLang.coutObjectsOnScene;

public class MainEditor {

    public static GUICanvas mainEditorCanvas;

    static GUIPanel objectsListPanel;
    static GUIText objectListHeadText;

    static BlurTuning blurTuning = new BlurTuning();

    static Color backgroundColor = new ColorRGB(61, 61, 61,128);
    static Color textColor = new ColorRGB(101, 214, 182);
    static Color standOutTextColor = new ColorRGB(209, 149, 0);
    static GUIFreeTextDrawer drawerText;
    static Vector2i objectsListSize;
    static Vector2i objectsSizeInList;

    public static void init(Window window, GUICanvas mainCanvas){
        mainEditorCanvas = new GUICanvas(new Vector2i(0,0), window.getResolution());

        objectsListSize = new Vector2i(200,mainEditorCanvas.getSize().y);
        objectsSizeInList = new Vector2i(200,20);

        objectsListPanel = new GUIPanel(new Vector2i(0,0), objectsListSize, backgroundColor, blurTuning);
        objectListHeadText = new GUIText(new Vector2i(0,-10),"", standOutTextColor,0,12);
        drawerText = new GUIFreeTextDrawer("Times New Roman",0,14,true);
        objectListHeadText.canResize = true;
        addAllOnCanvas();
        mainCanvas.addObject(mainEditorCanvas);
    }

    public static void update(Scene activeScene){
        int countObjects = activeScene.getCountObjects();
        objectListHeadText.updateText(coutObjectsOnScene + countObjects);

        for (int i = 0; i < countObjects; i++){
            drawerText.draw(activeScene.getObjectByID(i).name + " (" + i + ")", new Vector2i(0,10 + objectsSizeInList.y * i), textColor);
        }

    }

    protected static void addAllOnCanvas(){
        mainEditorCanvas.addObject(objectsListPanel);
        mainEditorCanvas.addObject(objectListHeadText);
        mainEditorCanvas.addObject(drawerText);
    }
}
