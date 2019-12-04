package LokEngineSceneEditor.UI.Basic;

import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUILocationAlgorithm;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUIListCanvas;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIPanel;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIText;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Timer;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.ColorRGB;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class ErrorsListCanvas extends GUIListCanvas {

    class ErrorObject extends GUIObject {
        GUIText text;
        GUIPanel panel;
        long addTime;

        public ErrorObject(String text) {
            super(new Vector2i(), new Vector2i());

            this.text = new GUIText(new Vector2i(), text,Colors.white(),0,11);
            this.panel = new GUIPanel(new Vector2i(),new Vector2i(), new ColorRGB(235, 90, 90, 180));

            this.panel.setPosition(guiObject -> getPosition());
            this.panel.setSize(guiObject -> getSize());
            this.text.setPosition(guiObject -> getPosition());

            addTime = System.currentTimeMillis();
        }

        @Override
        public void setSize(Vector2i size) {
            super.setSize(size);
        }

        public void show(){
            addTime = System.currentTimeMillis();
        }

        @Override
        public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
            super.update(partsBuilder, parentProperties);

            if (System.currentTimeMillis() - addTime < 5 * 1000){
                panel.update(partsBuilder, properties);
                text.update(partsBuilder, properties);
            }
        }
    }

    public ErrorsListCanvas(Vector2i position, Vector2i size) {
        super(position, size, new Vector2i(size.x,size.y / 10),5);
    }

    public void addError(String text){
        addObject(new ErrorObject(text), true);
    }

    public void showAll(){
        for (GUIObject object : objects){
            if (object.getClass().equals(ErrorObject.class)){
                ((ErrorObject)object).show();
            }
        }
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
    }
}
