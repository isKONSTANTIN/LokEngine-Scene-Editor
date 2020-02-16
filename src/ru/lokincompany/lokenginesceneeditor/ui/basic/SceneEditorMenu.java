package ru.lokincompany.lokenginesceneeditor.ui.basic;

import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIButton;
import ru.lokincompany.lokengine.gui.guiobjects.GUIMenu;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokenginesceneeditor.LESEApplication;
import ru.lokincompany.lokenginesceneeditor.ui.Colors;

public class SceneEditorMenu extends GUIMenu {
    GUIListCanvas filePoint;

    public SceneEditorMenu(Vector2i position, int titleSize, GUICanvas source) {
        super(new FontPrefs().setSize(titleSize - 4));
        setPosition(position);

        this.setSize(guiObject -> new Vector2i(source.getSize().x, titleSize));

        setupFilePoint();

        source.addObject(filePoint);
    }

    private void setupFilePoint() {
        filePoint = new GUIListCanvas(new Vector2i(), new Vector2i(300, 40), new Vector2i(300, 20));
        filePoint.ignoreCanvasUpdateOrder = true;
        Color buttonsColor = Colors.engineBackgroundColor();
        buttonsColor.alpha -= 0.3f;

        GUIButton openButton = new GUIButton().setText(new GUIText().setText("Открыть сцену (./Scene.save)"))
                .setUnpressScript(guiButton -> {
                    LESEApplication.getInstance().loadScene("./Scene.save");
                    hideActiveItem();
                });

        GUIButton saveButton  = new GUIButton().setText(new GUIText().setText("Сохранить сцену (./Scene.save)\""))
                .setUnpressScript(guiButton -> {
                    LESEApplication.getInstance().saveScene("./Scene.save");
                    hideActiveItem();
                });

        filePoint.addObject(openButton);
        filePoint.addObject(saveButton);

        this.addPoint("Файл", filePoint);
    }
}
