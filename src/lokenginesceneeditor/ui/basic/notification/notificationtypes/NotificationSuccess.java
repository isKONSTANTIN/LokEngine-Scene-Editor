package lokenginesceneeditor.ui.basic.notification.notificationtypes;

import lokenginesceneeditor.ui.Colors;

public class NotificationSuccess extends NotificationObject {
    public NotificationSuccess(String text) {
        super(text, Colors.setAlphaRGB(Colors.success(), 180));
    }
}
