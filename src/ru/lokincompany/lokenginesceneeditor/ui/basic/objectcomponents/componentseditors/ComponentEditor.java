package ru.lokincompany.lokenginesceneeditor.ui.basic.objectcomponents.componentseditors;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUIAutoResizableCanvas;
import ru.lokincompany.lokengine.gui.guiobjects.GUIPanel;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokenginesceneeditor.ui.Colors;

public class ComponentEditor extends GUIAutoResizableCanvas {

    protected GUIAutoResizableCanvas canvas;
    private GUIPanel panel;
    private GUIText title;

    public ComponentEditor(String name) {
        super(new Vector2i());
        this.autoX = false;

        title = new GUIText(new FontPrefs().setSize(10)).setText(name);
        panel = new GUIPanel().setSize(new Vector2i()).setColor(new Color(0.4f, 0.4f, 0.4f, 0.3f));
        canvas = new GUIAutoResizableCanvas(new Vector2i(0, title.getSize().y + 2));

        this.addObject(panel);
        this.addObject(title);
        this.addObject(canvas);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        panel.setSize(size);
    }
}
