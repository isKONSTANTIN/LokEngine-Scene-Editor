package lokenginesceneeditor.ui.basic.notification.notificationtypes;

import lokenginesceneeditor.ui.Colors;

public class NotificationWarning extends NotificationObject {
    public NotificationWarning(String text) {
        super(text, Colors.setAlphaRGB(Colors.warning(), 180));
    }
}
