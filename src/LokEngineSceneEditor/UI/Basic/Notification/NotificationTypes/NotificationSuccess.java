package LokEngineSceneEditor.UI.Basic.Notification.NotificationTypes;

import LokEngineSceneEditor.UI.Colors;

public class NotificationSuccess extends NotificationObject {
    public NotificationSuccess(String text) {
        super(text, Colors.setAlphaRGB(Colors.success(), 180));
    }
}
