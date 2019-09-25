package LokEngineSceneEditor.GUI.ComponentsWindow;

import LokEngine.Components.AdditionalObjects.Animation;
import LokEngine.Components.AnimationComponent;
import LokEngine.Components.SpriteComponent;
import LokEngine.GUI.Canvases.GUIListCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Logger;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.SaveWorker.FileWorker;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.ColorRGB;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.SceneObjectComponentsPanel;
import LokEngineSceneEditor.SceneInteraction.CameraMovement;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static LokEngineSceneEditor.GUI.MainStyle.*;

public class AnimationComponentWindow extends GUIObject {

    GUISubWindow subWindow;

    GUIListCanvas list;

    boolean[] lastActives = new boolean[2];

    public AnimationComponentWindow(Vector2i position, Vector2i size) {
        super(position, size);

        subWindow = new GUISubWindow(position, size, true,
                new GUIText(new Vector2i(), "", "Animation component settings", textColor, 0, 12, false, false),
                new GUIPanel(new Vector2i(), new Vector2i(), panelsColor, panelsBlur)
        );

        subWindow.canvas.addObject(new GUIPanel(new Vector2i(), subWindow.canvas.getSize(), panelsColor, panelsBlur));

        GUIButton applyButton = new GUIButton(new Vector2i(0, size.y - 50), new Vector2i(size.x / 2 - 5, 35), panelsColor, panelsColor,
                new GUIText(new Vector2i(), "Apply", textColor, 0, 14),
                new GUIPanel(new Vector2i(), new Vector2i())
        );

        applyButton.setPressScript(guiButton -> {
            SceneObjectComponentsPanel.selectedComponent = null;
        });

        GUIButton submitButton = new GUIButton(new Vector2i(size.x / 2 + 5, size.y - 50), new Vector2i(size.x / 2 - 5, 35), panelsColor, panelsColor,
                new GUIText(new Vector2i(), "Load", textColor, 0, 14),
                new GUIPanel(new Vector2i(), new Vector2i()));

        submitButton.setPressScript(guiButton -> {
            if (SceneObjectComponentsPanel.selectedComponent != null) {
                AnimationComponent component = (AnimationComponent) SceneObjectComponentsPanel.selectedComponent;
                try {
                    FileWorker fileWorker = new FileWorker(((GUITextField) list.getObject(1)).getGUIText().getText());
                    fileWorker.openRead();
                    component.addAnimation((Animation) new Animation().load(fileWorker.read()), "Animation_1");
                    fileWorker.close();
                } catch (Exception e) {
                    subWindow.canvas.setColor(new Color(1f, 0.6f, 0.6f, 1f));
                    Logger.warning("Fail load animation", "Animation Component Window");
                    Logger.printException(e);
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            subWindow.canvas.setColor(new Color(1f, 1f, 1f, 1f));
                            timer.cancel();
                        }
                    }, 2000);
                }
            }
        });

        list = new GUIListCanvas(new Vector2i(), size, new Vector2i(size.x, 25));

        list.addObject(new GUIText(new Vector2i(), "Animation save path:"));
        list.addObject(new GUITextField(new Vector2i(), new Vector2i(), new GUIText(new Vector2i(), "", textColor, 0, 14)));

        subWindow.canvas.addObject(submitButton);
        subWindow.canvas.addObject(applyButton);
        subWindow.canvas.addObject(list);
    }

    @Override
    public void update(PartsBuilder partsBuilder, Vector2i globalPos){
        if (SceneObjectComponentsPanel.selectedComponent != null && SceneObjectComponentsPanel.selectedComponent.getName().equals("Animation Component")){

            AnimationComponent component = (AnimationComponent)SceneObjectComponentsPanel.selectedComponent;

            component.currectFrame+=0.01f * RuntimeFields.getDeltaTime();

            subWindow.update(partsBuilder, globalPos);
            CameraMovement.accepted = false;
        }
    }


}
