package LokEngineSceneEditor.GUI;

import LokEngine.GUI.Canvases.GUICanvas;

public abstract class GUIElement{
    abstract public void init(GUICanvas canvas);
    abstract public void update();
}