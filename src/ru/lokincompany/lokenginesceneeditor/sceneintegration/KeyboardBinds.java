package ru.lokincompany.lokenginesceneeditor.sceneintegration;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.render.Camera;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.input.Mouse;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokenginesceneeditor.LESEApplication;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardBinds {

    static boolean lastCKeyPressed;
    static boolean lastDKeyPressed;
    static Vector2f mouseDistance;

    static boolean checkObjectActions(Keyboard keyboard, Camera camera, Mouse mouse) {
        SceneObject sceneObject = HighlightedObject.getHighlightedObject();
        if (sceneObject == null || !keyboard.isKeyDown(GLFW_KEY_LEFT_CONTROL)) return false;

        boolean actionHappened = false;
        boolean CKeyPressed = keyboard.isKeyDown(GLFW_KEY_C);
        boolean DKeyPressed = keyboard.isKeyDown(GLFW_KEY_D);
        boolean ZKeyPressed = keyboard.isKeyDown(GLFW_KEY_Z);

        boolean mousePressed = mouse.getPressedStatus();

        if (CKeyPressed && !lastCKeyPressed) {
            HighlightedObject.copyObject();
            actionHappened = true;
        } else if (DKeyPressed && !lastDKeyPressed) {
            HighlightedObject.deleteObject();
            actionHappened = true;
        } else if (ZKeyPressed) {
            Vector2i mouseScreenPos = mouse.getMousePosition();
            Vector2f mouseScenePos = camera.screenPointToScene(mouseScreenPos);

            if (mouseDistance == null) {
                if (mousePressed) {
                    mouseDistance = new Vector2f(0, 0);
                } else
                    mouseDistance = new Vector2f(sceneObject.position.x - mouseScenePos.x, sceneObject.position.y - mouseScenePos.y);
            } else if (mousePressed) {
                mouseDistance.set(0, 0);
            }

            sceneObject.position = new Vector2f(mouseScenePos.x + mouseDistance.x, mouseScenePos.y + mouseDistance.y);
            actionHappened = true;
        }

        if (!ZKeyPressed) {
            mouseDistance = null;
        }

        lastCKeyPressed = CKeyPressed;
        lastDKeyPressed = DKeyPressed;

        return actionHappened;
    }

    public static void update() {
        LESEApplication application = LESEApplication.getInstance();
        Keyboard keyboard = application.window.getKeyboard();
        Mouse mouse = application.window.getMouse();
        Camera camera = application.window.getCamera();

        if (checkObjectActions(keyboard, camera, mouse) ||
                mouse.inField(application.sceneEditor.objectsList.properties.globalPosition, application.sceneEditor.objectsList.properties.size) ||
                mouse.inField(application.sceneEditor.objectProperties.properties.globalPosition, application.sceneEditor.objectProperties.properties.size))
            return;

        float extraSpeed = keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT) ? 3 : 1;
        float scroll = -(mouse.getMouseScroll().x + mouse.getMouseScroll().y) * camera.getFieldOfView() * extraSpeed;

        if (scroll != 0)
            camera.setFieldOfView(camera.getFieldOfView() + scroll * application.applicationRuntime.getDeltaTime() / 9f);

        camera.position.x += keyboard.isKeyDown(GLFW_KEY_D) ? camera.getFieldOfView() * application.applicationRuntime.getDeltaTime() * extraSpeed / 90f : 0;
        camera.position.x -= keyboard.isKeyDown(GLFW_KEY_A) ? camera.getFieldOfView() * application.applicationRuntime.getDeltaTime() * extraSpeed / 90f : 0;
        camera.position.y += keyboard.isKeyDown(GLFW_KEY_W) ? camera.getFieldOfView() * application.applicationRuntime.getDeltaTime() * extraSpeed / 90f : 0;
        camera.position.y -= keyboard.isKeyDown(GLFW_KEY_S) ? camera.getFieldOfView() * application.applicationRuntime.getDeltaTime() * extraSpeed / 90f : 0;
    }
}
