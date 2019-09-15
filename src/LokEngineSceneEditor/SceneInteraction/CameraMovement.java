package LokEngineSceneEditor.SceneInteraction;

import LokEngine.Render.Camera;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;

public class CameraMovement {

    static Vector2i lastPos = new Vector2i();
    static boolean lastStatus;
    public static void update(Camera camera){

        if (RuntimeFields.getMouseStatus().getPressedStatus() && lastStatus){
            camera.position.x -= (RuntimeFields.getMouseStatus().getMousePosition().x - lastPos.x) * 0.001f;
            camera.position.y -= (RuntimeFields.getMouseStatus().getMousePosition().y - lastPos.y) * 0.001f;
            lastPos = RuntimeFields.getMouseStatus().getMousePosition();
        }else if (!lastStatus){
            lastPos = RuntimeFields.getMouseStatus().getMousePosition();
        }

        lastStatus = RuntimeFields.getMouseStatus().getPressedStatus();
    }

}
