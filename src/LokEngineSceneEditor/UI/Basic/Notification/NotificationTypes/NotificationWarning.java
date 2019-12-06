package LokEngineSceneEditor.UI.Basic.Notification.NotificationTypes;

import LokEngineSceneEditor.UI.Colors;

public class NotificationWarning extends NotificationObject {
    public NotificationWarning(String text) {
        super(text, Colors.setAlphaRGB(Colors.warning(), 180));
    }
}
