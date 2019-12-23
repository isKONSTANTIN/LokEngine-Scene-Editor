package ru.lokincompany.lokenginesceneeditor.sceneintegration;

import ru.lokincompany.lokengine.sceneenvironment.SceneObject;

public class HighlightedObject {

    static SceneObject object;
    static int objectID = -1;

    public static void highlight(SceneObject sceneObject, int id) {
        object = sceneObject;
        objectID = id;
    }

    public static void update() {
        if (object == null) return;

        if (object.scene.getObjectByID(objectID) == null) {
            object = null;
            return;
        }

        if (!object.scene.getObjectByID(objectID).equals(object))
            object = null;

    }

    public static SceneObject getHighlightedObject() {
        return object;
    }

    public static int getHighlightedObjectID() {
        return objectID;
    }

    public static void deleteObject() {
        if (object != null) {
            object.scene.removeObject(objectID);
            objectID = -1;
            object = null;
        }
    }

    public static void copyObject() {
        SceneObject newObject = (SceneObject) new SceneObject().load(object.save());
        highlight(newObject, object.scene.addObject(newObject));
    }

}
