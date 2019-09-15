package LokEngineSceneEditor.SceneInteraction;

import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.RuntimeFields;
import LokEngineSceneEditor.Render.FrameParts.SceneObjectHighlightFramePart;

public class ObjectHighlight {

    static long time;
    static SceneObject object;

    public static void highlight(SceneObject sceneObject){
        object = sceneObject;
    }

    public static void update(){
        if (object != null)
            RuntimeFields.getFrameBuilder().addPart(new SceneObjectHighlightFramePart(
                object.position,
                Math.max(Math.abs((float)Math.sin(time/100f)), 0.5f),
                (float)(Math.sin(time/1000f) * 360)
            ));

        time++;
    }

}
