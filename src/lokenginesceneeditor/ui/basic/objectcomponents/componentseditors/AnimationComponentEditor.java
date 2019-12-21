package lokenginesceneeditor.ui.basic.objectcomponents.componentseditors;

import lokenginesceneeditor.ui.Colors;
import ru.lokincompany.lokengine.components.AnimationComponent;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUIFullFlexibleListCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIFreeTextDrawer;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.gui.guiobjects.GUITextField;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public class AnimationComponentEditor extends ComponentEditor {

    GUIFreeTextDrawer textDrawer;
    AnimationComponent component;
    int fontHeight;

    public AnimationComponentEditor(AnimationComponent component) {
        super("Animation component");

        this.component = component;
        textDrawer = new GUIFreeTextDrawer(0, 10, true);
        fontHeight = textDrawer.getFont().getFontHeight();
        textDrawer.setSize(guiObject -> new Vector2i(getSize().x,fontHeight * 4));

        canvas.addObject(textDrawer);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        String animationName = component.getActiveAnimationName();

        textDrawer.draw("Пропуск кадров: " + (component.frameSkipping ? "да" : "нет"), new Vector2i(3, 0));
        textDrawer.draw("Скорость анимации: " + component.speedAnimation, new Vector2i(3, fontHeight ));
        textDrawer.draw("Имя активной анимации:", new Vector2i(3, fontHeight * 2));
        textDrawer.draw(animationName != null ? animationName : "Нет активной анимации", new Vector2i(6, fontHeight * 3), Colors.red());
    }
}
