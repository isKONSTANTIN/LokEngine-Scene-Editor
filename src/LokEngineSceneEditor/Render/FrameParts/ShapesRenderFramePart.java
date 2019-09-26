package LokEngineSceneEditor.Render.FrameParts;

import LokEngine.Components.AdditionalObjects.Rigidbody.Shapes.BoxShape;
import LokEngine.Components.AdditionalObjects.Rigidbody.Shapes.Shape;
import LokEngine.Loaders.BufferLoader;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Shader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static LokEngineSceneEditor.LokEngineSceneEditor.GUISceneIntegrator;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class ShapesRenderFramePart extends FramePart {

    public Shape shape;
    public SceneObject sceneObject;

    public ShapesRenderFramePart(Shape shape, SceneObject sceneObject) {
        super(FramePartType.Scene);
        this.sceneObject = sceneObject;
        this.shape = shape;
    }

    @Override
    public void partRender() {

        if (shape.getClass().getName().equals(BoxShape.class.getName())) {
            BoxShape boxShape = (BoxShape) shape;
            Vector2f shapeSize = new Vector2f((boxShape.collideSize.x - 3.55f) * 0.005f / 9.5f,(boxShape.collideSize.y - 3.55f) * 0.005f / 9.5f);
            if (!GUISceneIntegrator.equals(Shader.currentShader)) {
                Shader.use(GUISceneIntegrator);
            }

            int buffer = BufferLoader.load(new float[] {
                    -shapeSize.x / 2, -shapeSize.y / 2,
                    -shapeSize.x / 2, shapeSize.y / 2,
                    shapeSize.x / 2, shapeSize.y / 2,
                    shapeSize.x / 2, -shapeSize.y / 2
            });

            glEnableVertexAttribArray(0);
            glBindBuffer(GL_ARRAY_BUFFER, buffer);
            glVertexAttribPointer(
                    0,
                    2,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(0, 0);

            glUniform1f(glGetUniformLocation(Shader.currentShader.program, "ObjectSize"), 2);
            glUniform4f(glGetUniformLocation(Shader.currentShader.program, "ObjectColor"), 0,1,0,1);
            MatrixCreator.PutMatrixInShader(Shader.currentShader, "ObjectModelMatrix", MatrixCreator.CreateModelMatrix(sceneObject.rollRotation, new Vector3f(sceneObject.position.x, sceneObject.position.y, sceneObject.renderPriority)));

            glDrawArrays(GL_LINE_LOOP, 0, 4);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            BufferLoader.unload(buffer);
            glDisableVertexAttribArray(0);
        }
    }
}
