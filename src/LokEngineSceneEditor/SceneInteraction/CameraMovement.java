package LokEngineSceneEditor.SceneInteraction;

import LokEngine.Render.Camera;
import LokEngine.Render.Window;
import LokEngine.Tools.Input.AdditionalObjects.MouseScrollScript;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;

import static LokEngineSceneEditor.LokEngineSceneEditor.GUISceneIntegrator;

public class CameraMovement {

    static Vector2i lastPos = new Vector2i();
    static boolean lastStatus;

    static float view = 1;
    static Window window;
    public static boolean accepted = true;

    public static void init(Window window){
        CameraMovement.window = window;
        window.getMouse().setMouseScrollScript((v, v1) -> {
            if (accepted){
                view += -(v + v1) / 20 * view;
                view = Math.max(view, 0.001f);
                window.getCamera().setFieldOfView(view);
                window.getCamera().setFieldOfView(view, GUISceneIntegrator);
            }
        });
    }

    public static void update(){
        if (accepted){
            if (window.getMouse().getPressedStatus() && lastStatus){
                window.getCamera().position.x -= (window.getMouse().getMousePosition().x - lastPos.x) * 0.001f * view;
                window.getCamera().position.y += (window.getMouse().getMousePosition().y - lastPos.y) * 0.001f * view;
            }

            lastStatus = window.getMouse().getPressedStatus();
        }
        lastPos = window.getMouse().getMousePosition();
    }

}
