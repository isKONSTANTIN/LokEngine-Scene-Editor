package LokEngineSceneEditor.SceneInteraction;

import LokEngine.Render.Camera;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.BuilderProperties;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Shader;
import LokEngine.Render.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.ApplicationRuntime;
import LokEngine.Tools.Logger;
import LokEngine.Tools.MatrixCreator;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static LokEngineSceneEditor.Misc.DefaultFields.highlightSprite;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

class HighlightFramePart extends FramePart {

    public Vector2f position;
    public float size;
    public float rotation;

    public HighlightFramePart(Vector2f position, float size, float rotation) {
        super(FramePartType.Scene);
        this.position = position;
        this.size = size;
        this.rotation = rotation;
    }

    public HighlightFramePart(Vector2f position, float size) {
        this(position, size, 0);
    }

    public HighlightFramePart(Vector2f position) {
        this(position, 0.2f);
    }


    @Override
    public void partRender(BuilderProperties builderProperties) {
        if (!builderProperties.getActiveShader().equals(builderProperties.getObjectShader())) {
            builderProperties.useShader(builderProperties.getObjectShader());
        }
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, highlightSprite.vertexBuffer);
        glVertexAttribPointer(
                0,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(0, 0);

        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, builderProperties.getUVBuffer());
        glVertexAttribPointer(
                1,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(1, 0);
        glUniform1f(glGetUniformLocation(builderProperties.getActiveShader().program, "ObjectSize"), size);

        MatrixCreator.PutMatrixInShader(builderProperties.getActiveShader(),"ObjectModelMatrix", MatrixCreator.CreateModelMatrix(rotation, new Vector3f(position.x,position.y,0)));

        glBindTexture(GL_TEXTURE_2D, highlightSprite.texture.buffer);

        glDrawArrays(GL_QUADS, 0, 8);

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }
}


public class ObjectHighlight {

    static SceneObject object;
    static int objectID = -1;
    static Window window;
    static ApplicationRuntime applicationRuntime;
    static Scene scene;

    public static void highlight(SceneObject sceneObject, int id){
        object = sceneObject;
        objectID = id;
        window.getCamera().position = new Vector2f(object.position.x, object.position.y);
    }

    public static SceneObject getHighlightedObject(){
        return object;
    }

    public static int getHighlightedObjectID() {
        return objectID;
    }
    public static void deleteObjectFromScene(){
        if (object != null){
            scene.removeObject(objectID);
            objectID = -1;
            object = null;
        }
    }

    public static void moveObjectFromCursor(){
        if (object != null && CameraMovement.accepted){
            object.position = window.getCamera().screenPointToScene(window.getMouse().getMousePosition());
        }
    }

    public static void init(Window window, ApplicationRuntime applicationRuntime, Scene scene){
        ObjectHighlight.window = window;
        ObjectHighlight.applicationRuntime = applicationRuntime;
        ObjectHighlight.scene = scene;
    }

    public static void update(){
        float time = applicationRuntime.getEngineRunTime() / 10000000000f;
        if (object != null)
            window.getFrameBuilder().getScenePartsBuilder().addPart(new HighlightFramePart(
                object.position,
                Math.max(Math.abs((float)Math.sin(time * 10)), 0.5f),
                (float)(Math.sin(time) * 360)
            ));
    }

}
