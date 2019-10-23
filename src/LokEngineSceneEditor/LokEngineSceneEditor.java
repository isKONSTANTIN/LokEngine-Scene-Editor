package LokEngineSceneEditor;

import LokEngine.Application;
import LokEngine.Loaders.ShaderLoader;
import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Shader;
import LokEngine.Render.Window.Window;
import LokEngine.Render.Window.WindowEvent;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.SaveWorker.FileWorker;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngineSceneEditor.GUI.ComponentsWindow.AnimationComponentWindow;
import LokEngineSceneEditor.GUI.ComponentsWindow.AvailableComponentsListWindow;
import LokEngineSceneEditor.GUI.ComponentsWindow.RigidbodyComponentWindow;
import LokEngineSceneEditor.GUI.ComponentsWindow.SpriteComponentWindow;
import LokEngineSceneEditor.GUI.SceneObjectComponentsPanel;
import LokEngineSceneEditor.GUI.SceneObjectPropertiesPanel;
import LokEngineSceneEditor.GUI.SceneObjectsListPanel;
import LokEngineSceneEditor.Misc.DefaultFields;
import LokEngineSceneEditor.RuntimeTest.MainTestApplication;
import LokEngineSceneEditor.SceneInteraction.CameraMovement;
import LokEngineSceneEditor.SceneInteraction.ObjectHighlight;
import org.lwjgl.glfw.GLFW;
import sun.awt.X11.XKeyboardFocusManagerPeer;

import java.io.IOException;

public class LokEngineSceneEditor extends Application {

    public static AvailableComponentsListWindow availableComponentsListWindow;
    public static Shader GUISceneIntegrator;

    boolean lastPressedLeftShiftButton;
    boolean lastPressedDeleteButton;
    boolean lastPressedCopyButtons;
    boolean lastPressedF1Button;
    MainTestApplication testApplication;

    @Override
    public void Init() {
        CameraMovement.init(window);
        ObjectHighlight.init(window, applicationRuntime, scene);
        DefaultFields.highlightSprite = SpriteLoader.loadSprite("#/resources/textures/frame.png");
        try {
            GUISceneIntegrator = ShaderLoader.loadShader("#/resources/shaders/GUISceneIntegVert.glsl", "#/resources/shaders/GUISceneIntegFrag.glsl");
            window.getCamera().setFieldOfView(1, GUISceneIntegrator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        window.getCanvas().addObject(new SceneObjectsListPanel(new Vector2i(0, 0), new Vector2i(150, window.getResolution().y), scene, window.getCamera()));
        window.getCanvas().addObject(new SceneObjectPropertiesPanel(new Vector2i(window.getResolution().x - 200, 0), new Vector2i(200, window.getResolution().y)));

        window.getCanvas().addObject(
                new SceneObjectComponentsPanel(new Vector2i(window.getResolution().x - 200, window.getResolution().y / 2), new Vector2i(200, window.getResolution().y / 2))
        );
        window.getCanvas().addObject(new SpriteComponentWindow(new Vector2i(window.getResolution().x / 2 - 150, window.getResolution().y / 2 - 150), new Vector2i(300, 300)));
        window.getCanvas().addObject(new AnimationComponentWindow(new Vector2i(window.getResolution().x / 2 - 150, window.getResolution().y / 2 - 150), new Vector2i(300, 300)));
        window.getCanvas().addObject(new RigidbodyComponentWindow(new Vector2i(window.getResolution().x / 2 - 150, window.getResolution().y / 2 - 150), new Vector2i(300, 300)));


        availableComponentsListWindow = new AvailableComponentsListWindow(new Vector2i(window.getResolution().x / 2 - 150, window.getResolution().y / 2 - 150), new Vector2i(300, 300));
        availableComponentsListWindow.hidden = true;
        window.getCanvas().addObject(availableComponentsListWindow);

        applicationRuntime.setSpeedEngine(0);
        window.getFrameBuilder().glSceneClearColor = new Color(0.25f, 0.25f, 0.25f, 1f);

        try {
            if (FileWorker.fileExists("Scene.save")) {
                FileWorker fileWorker = new FileWorker("Scene.save");
                fileWorker.openRead();
                scene.load(fileWorker.read());
                fileWorker.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Update() {
        ObjectHighlight.update();

        LokEngine.Tools.Input.Keyboard keyboard = window.getKeyboard();
        boolean leftShiftButton = keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT);
        boolean deleteButton = keyboard.isKeyDown(GLFW.GLFW_KEY_DELETE);
        boolean CButton = keyboard.isKeyDown(GLFW.GLFW_KEY_C);
        boolean controlButton = keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL);
        boolean F1Button = keyboard.isKeyDown(GLFW.GLFW_KEY_F1);

        if (leftShiftButton && ObjectHighlight.getHighlightedObject() != null) {
            ObjectHighlight.moveObjectFromCursor();
        } else {
            CameraMovement.update();
        }

        if (!lastPressedDeleteButton && deleteButton && ObjectHighlight.getHighlightedObject() != null) {
            ObjectHighlight.deleteObjectFromScene();
        }
        lastPressedDeleteButton = deleteButton;

        if (!lastPressedCopyButtons && CButton && controlButton) {
            if (ObjectHighlight.getHighlightedObject() != null)
                ObjectHighlight.copyObject();
        }
        lastPressedCopyButtons = CButton && controlButton;

        if (!lastPressedF1Button && F1Button) {
            if (testApplication == null || !testApplication.threadWin.isAlive()){
                testApplication = new MainTestApplication(scene.save());
            }
        }
        lastPressedF1Button = F1Button;

        window.getFrameBuilder().getBuilderProperties().useShader(GUISceneIntegrator);
        MatrixCreator.PutMatrixInShader(GUISceneIntegrator, "View", MatrixCreator.CreateViewMatrix(window.getCamera()));

        if (window.getKeyboard().isKeyDown(GLFW.GLFW_KEY_ESCAPE))
            close();
    }

    @Override
    public void Exit(){
        try {
            FileWorker fileWorker = new FileWorker("Scene.save");
            fileWorker.openWrite();
            fileWorker.write(scene.save());
            fileWorker.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    LokEngineSceneEditor(){
        start(false,true, new Vector2i(1280,720), "LokEngine Scene Editor");
    }
}
