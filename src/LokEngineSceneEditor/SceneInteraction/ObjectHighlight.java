package LokEngineSceneEditor.SceneInteraction;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Shader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Logger;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.RuntimeFields;
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
    public void partRender(){
        if (!highlightSprite.shader.equals(Shader.currentShader)) {
            Shader.use(highlightSprite.shader);
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
        glBindBuffer(GL_ARRAY_BUFFER, highlightSprite.uvBuffer);
        glVertexAttribPointer(
                1,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(1, 0);
        glUniform1f(glGetUniformLocation(highlightSprite.shader.program, "ObjectSize"), size);

        MatrixCreator.PutMatrixInShader(highlightSprite.shader,"ObjectModelMatrix", MatrixCreator.CreateModelMatrix(rotation, new Vector3f(position.x,position.y,0)));

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
    static int objectID;
    public static void highlight(SceneObject sceneObject, int id){
        object = sceneObject;
        objectID = id;
    }

    public static SceneObject getHighlightedObject(){
        return object;
    }

    public static void deleteObjectFromScene(){
        if (object != null){
            RuntimeFields.getScene().removeObject(objectID);
        }
    }

    public static void moveObjectFromCursor(){
        if (object != null && CameraMovement.accepted){
            object.position = RuntimeFields.getFrameBuilder().window.getCamera().screenPointToScene(RuntimeFields.getMouseStatus().getMousePosition());
        }
    }

    public static void update(){
        float time = RuntimeFields.getEngineRunTime() / 10000000000f;
        if (object != null)
            RuntimeFields.getFrameBuilder().addPart(new HighlightFramePart(
                object.position,
                Math.max(Math.abs((float)Math.sin(time * 10)), 0.5f),
                (float)(Math.sin(time) * 360)
            ));
    }

}
