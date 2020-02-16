package ru.lokincompany.lokenginesceneeditor.ui.basic;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIButtonScript;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.canvases.GUIListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIButton;
import ru.lokincompany.lokengine.gui.guiobjects.GUIPanel;
import ru.lokincompany.lokengine.gui.guiobjects.GUISubWindow;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokenginesceneeditor.misc.ComponentAddScript;
import ru.lokincompany.lokenginesceneeditor.ui.Colors;

public class SelectComponentWindow extends GUISubWindow {
    GUIListCanvas listCanvas;
    ComponentAddScript script;
    GUIButtonScript buttonsScript;

    public SelectComponentWindow(Vector2i position) {
        setSize(new Vector2i(250, 200)).setPosition(position).getTitleText().setText("Выбрать компонент");

        buttonsScript = guiButton -> {
            this.hidden = true;
            script.execute(guiButton.getText().getText());
        };

        listCanvas = new GUIListCanvas(new Vector2i(), this.getSize(), new Vector2i(this.getSize().x, 20), 5);

        FontPrefs fontPrefs = new FontPrefs().setSize(15);

        listCanvas.addObject(new GUIButton()
                .setText(new GUIText(fontPrefs).setText("Sprite component")).setUnpressScript(buttonsScript));

        listCanvas.addObject(new GUIButton()
                .setText(new GUIText(fontPrefs).setText("Animation component")).setUnpressScript(buttonsScript));

        listCanvas.addObject(new GUIButton()
                .setText(new GUIText(fontPrefs).setText("Rigidbody component")).setUnpressScript(buttonsScript));

        listCanvas.addObject(new GUIButton()
                .setText(new GUIText(fontPrefs).setText("Sound component")).setUnpressScript(buttonsScript));

        listCanvas.addObject(new GUIButton()
                .setText(new GUIText(fontPrefs).setText("Particle System component")).setUnpressScript(buttonsScript));

        GUIButton cancelButton = new GUIButton()
                .setText(new GUIText(fontPrefs).setText("Назад"))
                .setUnpressScript(guiButton -> this.hidden = true)
                .setSize(new Vector2i(this.getSize().x, 25));

        this.ignoreCanvasUpdateOrder = true;

        this.canvas.addObject(listCanvas);
        this.canvas.addObject(cancelButton, GUIPosition.BottomCenter);
    }

    public void sendRequest(ComponentAddScript script) {
        this.hidden = false;
        this.script = script;
    }
}
