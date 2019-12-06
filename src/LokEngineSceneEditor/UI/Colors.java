package LokEngineSceneEditor.UI;

import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.ColorRGB;

public class Colors extends ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors {

    public static Color error(){
        return new ColorRGB(225, 45, 45);
    }

    public static Color warning(){
        return new ColorRGB(255, 210, 30);
    }

    public static Color info(){
        return new ColorRGB(160, 160, 160, 180);
    }

    public static Color setAlpha(Color color, float alpha){
        color.alpha = alpha;
        return color;
    }

    public static Color setAlphaRGB(Color color, int alpha){
        return setAlpha(color,alpha / 255f);
    }
}
