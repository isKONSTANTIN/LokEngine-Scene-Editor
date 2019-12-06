package LokEngineSceneEditor.UI.Basic.Notification;

import LokEngineSceneEditor.UI.Basic.Notification.NotificationTypes.NotificationObject;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUIListCanvas;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class NotificationListCanvas extends GUIListCanvas {

    public NotificationListCanvas(Vector2i position, Vector2i size) {
        super(position, size, new Vector2i(size.x,size.y / 10),5);
    }

    public void addNotification(NotificationObject object){
        addObject(object, true);
    }

    public void showAll(){
        for (GUIObject object : objects){
            try{
                ((NotificationObject)object).show();
            }catch (ClassCastException ignored){}
        }
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
    }
}
