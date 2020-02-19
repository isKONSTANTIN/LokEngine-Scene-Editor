package ru.lokincompany.lokenginesceneeditor.ui.basic;

import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.gui.canvases.GUIFullFlexibleListCanvas;
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
    GUIFullFlexibleListCanvas filePoint;

    public SceneEditorMenu(Vector2i position, int titleSize, GUICanvas source) {
        super(new FontPrefs().setSize(titleSize - 4));
        setPosition(position);

        this.setSize(guiObject -> new Vector2i(source.getSize().x, titleSize));

        setupFilePoint();

        source.addObject(filePoint);
    }

    private void setupFilePoint() {
        filePoint = new GUIFullFlexibleListCanvas(new Vector2i());
        filePoint.autoX = false;
        filePoint.setSize(new Vector2i(250,0));
        filePoint.ignoreCanvasUpdateOrder = true;

        GUIButton openButton = new GUIButton()
                .setText(new GUIText().setText("Открыть сцену (./Scene.save)"))
                .setUnpressScript(guiButton -> {
                    LESEApplication.getInstance().loadScene("./Scene.save");
                    hideActiveItem();
                })
                .setSize(guiObject -> new Vector2i(filePoint.getSize().x, guiObject.getSize().y));

        openButton.getCalmStateColor().setAlpha(0.6f);
        openButton.getPressedColor().setAlpha(0.7f);

        GUIButton saveButton = new GUIButton()
                .setText(new GUIText().setText("Сохранить сцену (./Scene.save)"))
                .setUnpressScript(guiButton -> {
                    LESEApplication.getInstance().saveScene("./Scene.save");
                    hideActiveItem();
                })
                .setSize(guiObject -> new Vector2i(filePoint.getSize().x, guiObject.getSize().y));

        saveButton.getCalmStateColor().setAlpha(0.6f);
        saveButton.getPressedColor().setAlpha(0.7f);

        GUIButton runButton = new GUIButton()
                .setText(new GUIText().setText("Запустить сцену"))
                .setUnpressScript(guiButton -> {
                    LESEApplication.getInstance().runTest();
                    hideActiveItem();
                })
                .setSize(guiObject -> new Vector2i(filePoint.getSize().x, guiObject.getSize().y));

        runButton.getCalmStateColor().setAlpha(0.6f);
        runButton.getPressedColor().setAlpha(0.7f);

        filePoint.addObject(openButton);
        filePoint.addObject(saveButton);
        filePoint.addObject(runButton);

        this.addPoint("Сцена", filePoint);
    }

    private void setupScenePoint(){

    }
}
