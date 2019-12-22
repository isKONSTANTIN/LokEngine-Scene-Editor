package lokenginesceneeditor.ui.basic;

import lokenginesceneeditor.misc.ComponentAddScript;
import lokenginesceneeditor.ui.Colors;
import ru.lokincompany.lokengine.components.Component;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIButtonScript;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.canvases.GUIListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIButton;
import ru.lokincompany.lokengine.gui.guiobjects.GUIPanel;
import ru.lokincompany.lokengine.gui.guiobjects.GUISubWindow;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;

public class SelectComponentWindow extends GUISubWindow {
    GUIPanel panel;
    GUIListCanvas listCanvas;
    ComponentAddScript script;
    GUIButtonScript buttonsScript;

    public SelectComponentWindow(Vector2i position) {
        super(position, new Vector2i(250, 200), true, new GUIText(new Vector2i(), "Выбрать компонент", Colors.engineMainColor(), 0, 10), new GUIPanel(new Vector2i(), new Vector2i()));

        buttonsScript = guiButton -> {
            this.hidden = true;
            script.execute(guiButton.text.getText());
        };

        panel = new GUIPanel(new Vector2i(), new Vector2i());
        panel.setSize(guiObject -> this.getSize());

        listCanvas = new GUIListCanvas(new Vector2i(), this.getSize(), new Vector2i(this.getSize().x, 20), 5);

        GUIButton spriteButton = new GUIButton(new Vector2i(), new Vector2i(), Colors.engineBackgroundColor(),
                new GUIText(new Vector2i(), "Sprite component", Colors.white(), 0, 15), true
        );
        spriteButton.setUnpressScript(buttonsScript);
        listCanvas.addObject(spriteButton);

        GUIButton animationButton = new GUIButton(new Vector2i(), new Vector2i(), Colors.engineBackgroundColor(),
                new GUIText(new Vector2i(), "Animation component", Colors.white(), 0, 15), true
        );
        animationButton.setUnpressScript(buttonsScript);
        listCanvas.addObject(animationButton);

        GUIButton rigidbodyButton = new GUIButton(new Vector2i(), new Vector2i(), Colors.engineBackgroundColor(),
                new GUIText(new Vector2i(), "Rigidbody component", Colors.white(), 0, 15), true
        );
        rigidbodyButton.setUnpressScript(buttonsScript);
        listCanvas.addObject(rigidbodyButton);

        GUIButton soundButton = new GUIButton(new Vector2i(), new Vector2i(), Colors.engineBackgroundColor(),
                new GUIText(new Vector2i(), "Sound component", Colors.white(), 0, 15), true
        );
        soundButton.setUnpressScript(buttonsScript);
        listCanvas.addObject(soundButton);

        GUIButton particleSystemButton = new GUIButton(new Vector2i(), new Vector2i(), Colors.engineBackgroundColor(),
                new GUIText(new Vector2i(), "Particle System component", Colors.white(), 0, 15), true
        );
        particleSystemButton.setUnpressScript(buttonsScript);
        listCanvas.addObject(particleSystemButton);

        GUIButton cancelButton = new GUIButton(new Vector2i(), new Vector2i(this.getSize().x, 25), Colors.engineBackgroundColor(), new GUIText(new Vector2i(), "Назад", Colors.white(), 0, 15), true);
        cancelButton.setUnpressScript(guiButton -> this.hidden = true);

        this.ignoreCanvasUpdateOrder = true;

        this.canvas.addObject(panel);
        this.canvas.addObject(listCanvas);
        this.canvas.addObject(cancelButton, GUIPosition.BottomCenter);
    }

    public void sendRequest(ComponentAddScript script) {
        this.hidden = false;
        this.script = script;
    }
}
