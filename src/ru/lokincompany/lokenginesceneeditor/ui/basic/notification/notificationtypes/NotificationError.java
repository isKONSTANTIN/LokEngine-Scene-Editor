package ru.lokincompany.lokenginesceneeditor.ui.basic.notification.notificationtypes;

import ru.lokincompany.lokenginesceneeditor.ui.Colors;

public class NotificationError extends NotificationObject {
    public NotificationError(String text) {
        super(text, Colors.setAlphaRGB(Colors.error(), 180));
    }
}
