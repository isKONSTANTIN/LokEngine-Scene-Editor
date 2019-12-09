package lokenginesceneeditor.ui.basic.notification.notificationtypes;

import lokenginesceneeditor.ui.Colors;

public class NotificationInfo extends NotificationObject {
    public NotificationInfo(String text) {
        super(text, Colors.setAlphaRGB(Colors.info(), 180));
    }
}
