package lokenginesceneeditor.misc;

import ru.lokincompany.lokengine.tools.scripting.Scriptable;

public interface ComponentAddScript extends Scriptable {

    void execute(String componentName);

    @Override
    default void execute() {
        execute(null);
    }
}
