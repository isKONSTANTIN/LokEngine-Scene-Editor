package ru.lokincompany.lokenginesceneeditor.scenetester;

import org.lwjgl.glfw.GLFW;
import ru.lokincompany.lokengine.applications.ApplicationDefault;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.render.Camera;
import ru.lokincompany.lokengine.tools.TextColorShader;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.input.Mouse;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class SceneTestApplication extends ApplicationDefault {
    String sceneSave;

    public SceneTestApplication(String sceneSave){
        this.sceneSave = sceneSave;
        start(false, true, true, 16, new Vector2i(512, 512), "LESE scene test");
    }

    @Override
    protected void updateEvent() {
        Keyboard keyboard = window.getKeyboard();
        Camera camera = window.getCamera();
        Mouse mouse = window.getMouse();

        float scroll = -(mouse.getMouseScroll().x + mouse.getMouseScroll().y) * camera.getFieldOfView() / 10f;

        if (scroll != 0)
            camera.setFieldOfView(camera.getFieldOfView() + scroll);

        camera.position.x += keyboard.isKeyDown(GLFW.GLFW_KEY_D) ? camera.getFieldOfView() * applicationRuntime.getDeltaTime() / 90f : 0;
        camera.position.x -= keyboard.isKeyDown(GLFW.GLFW_KEY_A) ? camera.getFieldOfView() * applicationRuntime.getDeltaTime() / 90f : 0;
        camera.position.y += keyboard.isKeyDown(GLFW.GLFW_KEY_W) ? camera.getFieldOfView() * applicationRuntime.getDeltaTime() / 90f : 0;
        camera.position.y -= keyboard.isKeyDown(GLFW.GLFW_KEY_S) ? camera.getFieldOfView() * applicationRuntime.getDeltaTime() / 90f : 0;
    }

    @Override
    protected void initEvent() {
        scene.load(sceneSave);

        canvas.addObject(new GUIText()
                .setText("SCENE TEST")
                .setTextShader(vector2i -> {
                    float sin = (float) (Math.sin(applicationRuntime.getEngineRunTime() / 1000000000f) + 1) / 2f;
                    return new Color(sin, sin, sin, 1);
                }),
                GUIPosition.BottomRight);

        window.setCloseEvent((window1, objects) -> close());
    }
}
