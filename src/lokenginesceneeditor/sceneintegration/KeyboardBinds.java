package lokenginesceneeditor.sceneintegration;

import lokenginesceneeditor.LESEApplication;
import ru.lokincompany.lokengine.applications.applications.Application;
import ru.lokincompany.lokengine.applications.applications.ApplicationDefault;
import ru.lokincompany.lokengine.render.Camera;
import ru.lokincompany.lokengine.sceneenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.input.Mouse;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardBinds {

    static boolean lastPressedCKey;

    public static void update() {
        LESEApplication application = LESEApplication.getInstance();
        Keyboard keyboard = application.window.getKeyboard();
        Mouse mouse = application.window.getMouse();
        Camera camera = application.window.getCamera();

        if (mouse.inField(application.sceneEditor.objectsList.properties.globalPosition, application.sceneEditor.objectsList.properties.size) || mouse.inField(application.sceneEditor.objectProperties.properties.globalPosition, application.sceneEditor.objectProperties.properties.size))
            return;

        float extraSpeed = keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT) ? 3 : 1;
        float scroll = -(mouse.getMouseScroll().x + mouse.getMouseScroll().y) * camera.fieldOfView * extraSpeed;

        if (scroll != 0)
            camera.setFieldOfView(camera.fieldOfView + scroll * application.applicationRuntime.getDeltaTime() / 9f);

        camera.position.x += keyboard.isKeyDown(GLFW_KEY_D) ? camera.fieldOfView * application.applicationRuntime.getDeltaTime() * extraSpeed / 90f : 0;
        camera.position.x -= keyboard.isKeyDown(GLFW_KEY_A) ? camera.fieldOfView * application.applicationRuntime.getDeltaTime() * extraSpeed / 90f : 0;
        camera.position.y += keyboard.isKeyDown(GLFW_KEY_W) ? camera.fieldOfView * application.applicationRuntime.getDeltaTime() * extraSpeed / 90f : 0;
        camera.position.y -= keyboard.isKeyDown(GLFW_KEY_S) ? camera.fieldOfView * application.applicationRuntime.getDeltaTime() * extraSpeed / 90f : 0;

        SceneObject sceneObject = HighlightedObject.getHighlightedObject();

        if (sceneObject != null) {
            if (keyboard.isKeyDown(GLFW_KEY_LEFT_CONTROL))
                if (keyboard.isKeyDown(GLFW_KEY_C) && !lastPressedCKey) {
                    HighlightedObject.copyObject();
                } else if (keyboard.isKeyDown(GLFW_KEY_Z))
                    sceneObject.position = camera.screenPointToScene(mouse.getMousePosition());
        }

        lastPressedCKey = keyboard.isKeyDown(GLFW_KEY_C);
    }
}
