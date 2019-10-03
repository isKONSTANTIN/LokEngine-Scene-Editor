package LokEngineSceneEditor.Render.FrameParts;

import LokEngine.Components.AdditionalObjects.Rigidbody.Shapes.ArbitraryShape;
import LokEngine.Components.AdditionalObjects.Rigidbody.Shapes.BoxShape;
import LokEngine.Components.AdditionalObjects.Rigidbody.Shapes.CircleShape;
import LokEngine.Components.AdditionalObjects.Rigidbody.Shapes.Shape;
import LokEngine.Loaders.BufferLoader;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.BuilderProperties;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Shader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static LokEngineSceneEditor.LokEngineSceneEditor.GUISceneIntegrator;
import static org.jbox2d.common.MathUtils.DEG2RAD;
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
    public void partRender(BuilderProperties builderProperties) {
        int buffer = -1;
        int count = 0;
        float size = 2;

        if (shape.getClass().getName().equals(BoxShape.class.getName())) {
            BoxShape boxShape = (BoxShape) shape;
            Vector2f shapeSize = new Vector2f((boxShape.collideSize.x - 3.55f) * 0.005f / 9.5f,(boxShape.collideSize.y - 3.55f) * 0.005f / 9.5f);

            buffer = BufferLoader.load(new float[] {
                    -shapeSize.x / 2, -shapeSize.y / 2,
                    -shapeSize.x / 2, shapeSize.y / 2,
                    shapeSize.x / 2, shapeSize.y / 2,
                    shapeSize.x / 2, -shapeSize.y / 2
            });
            count = 4;
        }else if (shape.getClass().getName().equals(CircleShape.class.getName())) {
            CircleShape circleShape = (CircleShape) shape;
            float shapeSize = (circleShape.radius - 3.55f) * 0.005f / 9.5f;

            float[] positions = new float[360 * 2];

            for (int i = 0; i < 360; i++)
            {
                float degInRad = i*DEG2RAD;
                positions[i * 2] = (float)Math.cos(degInRad)*shapeSize;
                positions[i * 2 + 1] = (float)Math.sin(degInRad)*shapeSize;
            }
            size = 1;
            buffer = BufferLoader.load(positions);
            count = 360;
        }else if (shape.getClass().getName().equals(ArbitraryShape.class.getName())) {
            ArbitraryShape arbitraryShape = (ArbitraryShape) shape;

            float[] positions = new float[arbitraryShape.collidePoints.length * 2];

            Vector2f maxPos = new Vector2f();
            Vector2f minPos = new Vector2f();

            size = 1;

            for (int i = 0; i < arbitraryShape.collidePoints.length; i++)
            {
                float x = arbitraryShape.collidePoints[i].x * 0.005f / 9.5f;
                float y = arbitraryShape.collidePoints[i].y * 0.005f / 9.5f;
                positions[i * 2] = x;
                positions[i * 2 + 1] = y;

                maxPos.x = Math.max(x,maxPos.x);
                maxPos.y = Math.max(y,maxPos.y);

                minPos.x = Math.min(x,minPos.x);
                minPos.y = Math.min(y,minPos.y);
            }
            buffer = BufferLoader.load(positions);
            count = arbitraryShape.collidePoints.length;
        }

        if (buffer != -1){
            if (builderProperties.getActiveShader() == null || !GUISceneIntegrator.equals(builderProperties.getActiveShader())) {
                builderProperties.useShader(GUISceneIntegrator);
            }

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

            glUniform1f(glGetUniformLocation(builderProperties.getActiveShader().program, "ObjectSize"), size);
            glUniform4f(glGetUniformLocation(builderProperties.getActiveShader().program, "ObjectColor"), 0,1,0,1);
            MatrixCreator.PutMatrixInShader(builderProperties.getActiveShader(), "ObjectModelMatrix", MatrixCreator.CreateModelMatrix(sceneObject.rollRotation, new Vector3f(sceneObject.position.x, sceneObject.position.y, sceneObject.renderPriority)));

            glDrawArrays(GL_LINE_LOOP, 0, count);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            BufferLoader.unload(buffer);
            glDisableVertexAttribArray(0);
        }

    }
}
