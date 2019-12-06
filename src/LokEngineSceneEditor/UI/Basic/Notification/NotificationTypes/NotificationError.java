package LokEngineSceneEditor.UI.Basic.Notification.NotificationTypes;

import LokEngineSceneEditor.UI.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;

public class NotificationError extends NotificationObject {
    public NotificationError(String text) {
        super(text, Colors.setAlphaRGB(Colors.error(), 180));
    }
}
