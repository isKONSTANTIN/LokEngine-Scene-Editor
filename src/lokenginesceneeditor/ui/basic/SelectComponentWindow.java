package lokenginesceneeditor.ui.basic;

import lokenginesceneeditor.misc.ComponentAddScript;
import lokenginesceneeditor.ui.Colors;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIButtonScript;
import ru.lokincompany.lokengine.gui.canvases.GUIListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIButton;
import ru.lokincompany.lokengine.gui.guiobjects.GUIPanel;
import ru.lokincompany.lokengine.gui.guiobjects.GUISubWindow;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.tools.scripting.Scriptable;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public class SelectComponentWindow extends GUISubWindow {
    GUIPanel panel;
    GUIListCanvas listCanvas;
    ComponentAddScript script;
    GUIButtonScript buttonsScript;

    public void sendRequest(ComponentAddScript script){
        this.hidden = false;
        this.script = script;
    }

    public SelectComponentWindow(Vector2i position) {
        super(position, new Vector2i(150,250), true, new GUIText(new Vector2i(),"Выбрать компонент", Colors.engineMainColor(),0,10), new GUIPanel(new Vector2i(), new Vector2i()));

        buttonsScript = guiButton -> {
            this.hidden = true;
            script.execute(guiButton.text.getText());
        };

        panel = new GUIPanel(new Vector2i(), new Vector2i());
        panel.setSize(guiObject -> this.getSize());

        listCanvas = new GUIListCanvas(new Vector2i(), this.getSize(),new Vector2i(this.getSize().x,20));

        GUIButton spriteButton = new GUIButton(new Vector2i(), new Vector2i(), Colors.engineBackgroundColor(),
                new GUIText(new Vector2i(),"Sprite component", Colors.white(), 0, 15),true
        );
        spriteButton.setUnpressScript(buttonsScript);

        listCanvas.addObject(spriteButton);
        this.canvas.addObject(panel);
        this.canvas.addObject(listCanvas);
    }
}
