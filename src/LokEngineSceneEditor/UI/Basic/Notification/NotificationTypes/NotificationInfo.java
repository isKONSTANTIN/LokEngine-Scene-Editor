package LokEngineSceneEditor.UI.Basic.Notification.NotificationTypes;

import LokEngineSceneEditor.UI.Colors;

public class NotificationInfo extends NotificationObject {
    public NotificationInfo(String text) {
        super(text, Colors.setAlphaRGB(Colors.info(), 180));
    }
}
