package lokenginesceneeditor.ui;

import ru.lokincompany.lokengine.tools.utilities.color.Color;
import ru.lokincompany.lokengine.tools.utilities.color.ColorRGB;

public class Colors extends ru.lokincompany.lokengine.tools.utilities.color.Colors {

    public static Color error(){
        return new ColorRGB(225, 45, 45);
    }
    public static Color warning(){
        return new ColorRGB(255, 210, 30);
    }
    public static Color info(){
        return new ColorRGB(160, 160, 160);
    }
    public static Color success(){
        return new ColorRGB(40, 237, 70);
    }

    public static Color setAlpha(Color color, float alpha){
        color.alpha = alpha;
        return color;
    }

    public static Color setAlphaRGB(Color color, int alpha){
        return setAlpha(color,alpha / 255f);
    }
}
