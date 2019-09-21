package LokEngineSceneEditor.GUI.MainEditor.ComponentsEditors;

import LokEngine.Components.SpriteComponent;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Logger;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.ColorRGB;
import LokEngine.Tools.Utilities.Vector2i;

import java.awt.*;

import static LokEngineSceneEditor.GUI.MainEditor.MainEditor.*;

public class SpriteWindow extends GUISubWindow {

    GUIListCanvas listCanvas;
    GUITextField pathText;
    GUITextField sizeText;
    boolean lastPathTextActive;
    boolean lastSizeTextActive;

    public SpriteWindow(Vector2i position) {
        super(position, new Vector2i(324, 612), false,
                new GUIText(new Vector2i(), Font.SERIF, "Sprite edit", textColor, 0, 12, false, true),
                new GUIPanel(new Vector2i(), new Vector2i(), new Color(backgroundColor.red - 0.2f, backgroundColor.green - 0.2f, backgroundColor.blue - 0.2f, backgroundColor.alpha), blurTuning)
        );
        canvas.addObject(new GUIPanel(new Vector2i(), getSize(), backgroundColor, blurTuning));

        listCanvas = new GUIListCanvas(new Vector2i(),canvas.getSize(), new Vector2i(canvas.getSize().x,20));
        pathText = new GUITextField(new Vector2i(),new Vector2i(), new GUIText(new Vector2i(),"","",textColor,0,18,false,true));
        sizeText = new GUITextField(new Vector2i(0,20),new Vector2i(), new GUIText(new Vector2i(),"","",textColor,0,18,false,true));

        listCanvas.addObject(new GUIText(new Vector2i(),"Sprite path: ",textColor,0,18));
        listCanvas.addObject(pathText);
        listCanvas.addObject(new GUISpace());
        listCanvas.addObject(new GUIText(new Vector2i(),"Sprite size: ", textColor,0,18));
        listCanvas.addObject(sizeText);

        canvas.addObject(listCanvas);
    }

    public void update(SpriteComponent component) {
        if (component != null) {
            if (!pathText.getActive() && lastPathTextActive){
                component.setSprite(pathText.getGUIText().getText());
            }else if (!pathText.getActive()){
                pathText.getGUIText().updateText(component.getSprite().texture.path);
            }
            lastPathTextActive = pathText.getActive();

            if (!sizeText.getActive() && lastSizeTextActive){
                component.getSprite().size = Double.parseDouble(sizeText.getGUIText().getText());
            }else if (!sizeText.getActive()){
                sizeText.getGUIText().updateText(String.valueOf(component.getSprite().size));
            }
            lastSizeTextActive = sizeText.getActive();
        }else {
            this.hidden = true;
        }
    }
}
