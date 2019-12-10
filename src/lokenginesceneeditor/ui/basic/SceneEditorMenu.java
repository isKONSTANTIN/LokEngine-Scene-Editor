package lokenginesceneeditor.ui.basic;

import lokenginesceneeditor.LESEApplication;
import lokenginesceneeditor.ui.Colors;
import ru.lokincompany.lokengine.gui.canvases.GUIListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIButton;
import ru.lokincompany.lokengine.gui.guiobjects.GUIMenu;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;

public class SceneEditorMenu extends GUIMenu {

    GUIListCanvas filePoint;

    private void setupFilePoint(){
        filePoint = new GUIListCanvas(new Vector2i(), new Vector2i(300,40),new Vector2i(300,20));
        filePoint.ignoreCanvasUpdateOrder = true;
        Color buttonsColor = Colors.engineBackgroundColor();
        buttonsColor.alpha -= 0.3f;

        GUIButton openButton = new GUIButton(new Vector2i(), new Vector2i(), buttonsColor,
                new GUIText(new Vector2i(), "Открыть сцену (./Scene.save)", Colors.white(),0,12), false
        );
        openButton.setUnpressScript(guiButton -> {
            LESEApplication.getInstance().loadScene("./Scene.save");
            hideActiveItem();
        });

        GUIButton saveButton = new GUIButton(new Vector2i(), new Vector2i(), buttonsColor,
                new GUIText(new Vector2i(), "Сохранить сцену (./Scene.save)", Colors.white(),0,12), false
        );
        saveButton.setUnpressScript(guiButton -> {
            LESEApplication.getInstance().saveScene("./Scene.save");
            hideActiveItem();
        });

        filePoint.addObject(openButton);
        filePoint.addObject(saveButton);

        this.addPoint("Файл", filePoint);
    }

    public SceneEditorMenu(Vector2i position, int titleSize, Window window) {
        super(position, new Vector2i(), titleSize, Colors.engineMainColor(), Colors.white());
        this.setSize(guiObject -> new Vector2i(window.getResolution().x, titleSize));

        setupFilePoint();

        window.getCanvas().addObject(filePoint);
    }
}
