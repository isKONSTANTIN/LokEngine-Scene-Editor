package ru.lokincompany.lokenginesceneeditor.ui.basic.notification.notificationtypes;

import ru.lokincompany.lokenginesceneeditor.ui.Colors;

public class NotificationSuccess extends NotificationObject {
    public NotificationSuccess(String text) {
        super(text, Colors.setAlphaRGB(Colors.success(), 180));
    }
}
