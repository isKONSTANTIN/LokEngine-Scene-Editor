package LokEngineSceneEditor.SceneInteraction;

import LokEngine.Render.Camera;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.input.Mouse;

public class CameraMovement {

    static Vector2i lastPos = new Vector2i();
    static boolean lastStatus;

    static float view = 1;

    public static boolean accepted = true;

    public static void update(Camera camera){
        if (accepted){
            if (RuntimeFields.getMouseStatus().getPressedStatus() && lastStatus){
                camera.position.x -= (RuntimeFields.getMouseStatus().getMousePosition().x - lastPos.x) * 0.001f * view;
                camera.position.y -= (RuntimeFields.getMouseStatus().getMousePosition().y - lastPos.y) * 0.001f * view;
                lastPos = RuntimeFields.getMouseStatus().getMousePosition();
            }else if (!lastStatus){
                lastPos = RuntimeFields.getMouseStatus().getMousePosition();
            }

            int wheel = Mouse.getDWheel();

            if (wheel != 0){
                view += -wheel / 1000f * view;
                view = Math.max(view, 0.001f);
                camera.setFieldOfView(view);
            }

            lastStatus = RuntimeFields.getMouseStatus().getPressedStatus();
        }
    }

}
