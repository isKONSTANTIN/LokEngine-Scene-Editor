package LokEngineSceneEditor.RuntimeTest;

import LokEngine.Application;
import LokEngine.GUI.GUIObjects.GUIGraph;
import LokEngine.GUI.GUIObjects.GUIText;
import LokEngine.Render.Window.Window;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngine.Tools.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;

import java.util.logging.Logger;

public class MainTestApplication extends Application {

    String startScene;

    GUIGraph fpsGraph;
    GUIGraph deltaGraph;
    Timer timer;

    float fps;
    float delta;

    public Thread threadWin;
    @Override
    public void Update(){

        window.getCamera().position.x += window.getKeyboard().isKeyDown(GLFW.GLFW_KEY_D) ? applicationRuntime.getDeltaTime() * 0.01f : 0;
        window.getCamera().position.x -= window.getKeyboard().isKeyDown(GLFW.GLFW_KEY_A) ? applicationRuntime.getDeltaTime() * 0.01f : 0;
        window.getCamera().position.y += window.getKeyboard().isKeyDown(GLFW.GLFW_KEY_W) ? applicationRuntime.getDeltaTime() * 0.01f : 0;
        window.getCamera().position.y -= window.getKeyboard().isKeyDown(GLFW.GLFW_KEY_S) ? applicationRuntime.getDeltaTime() * 0.01f : 0;

        fps += applicationRuntime.getFps();
        fps /= 2;

        delta += applicationRuntime.getDeltaTime();
        delta /= 2;

        if (timer.checkTime()){
            fpsGraph.addPoint(fps);
            deltaGraph.addPoint(delta);
            fps = 0;
            delta = 0;
            timer.resetTimer();
        }
        if (window.getKeyboard().isKeyDown(GLFW.GLFW_KEY_ESCAPE))
            close();
    }

    @Override
    public void Init(){

        threadWin = new Thread(new Runnable() {
            @Override
            public void run() {
                Window statWin = new Window();
                statWin.open(false,true,new Vector2i(256,150));
                statWin.getFrameBuilder().glSceneClearColor = new Color(0.1f,0.1f,0.1f,1);
                statWin.setTitle("Scene performance [LESE]");

                statWin.getCanvas().addObject(new GUIText(new Vector2i(),"FPS:",new Color(1,1,1,1),0,14));
                fpsGraph = new GUIGraph(new Vector2i(0,15), new Vector2i(120,120),60,0,60,new Color(0,1,1,1));
                statWin.getCanvas().addObject(fpsGraph);

                statWin.getCanvas().addObject(new GUIText(new Vector2i(125,0),"Delta:",new Color(1,1,1,1),0,14));
                deltaGraph = new GUIGraph(new Vector2i(125,15), new Vector2i(120,120),30,0,60,new Color(0,1,1,1));
                statWin.getCanvas().addObject(deltaGraph);

                while (!Thread.currentThread().isInterrupted()){
                    Vector2i winPos = window.getPosition();
                    statWin.setPosition(new Vector2i(winPos.x + window.getResolution().x, winPos.y));
                    statWin.update();
                }

                statWin.close();
            }
        });
        threadWin.start();

        timer = new Timer();
        timer.setDurationInSeconds(0.5f);
        scene.load(startScene);
    }

    @Override
    public void Exit(){
        threadWin.interrupt();
    }

    public MainTestApplication(String startScene){
        this.startScene = startScene;
        start(false,true, new Vector2i(512,512),"Scene test [LESE]");
    }

}
