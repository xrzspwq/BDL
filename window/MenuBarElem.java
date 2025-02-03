package window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuBarElem 
{
    enum Menus
    {
        FILE,
        EDIT,
        View,
    }

   public static final List<String> fileMenuItems = Collections.unmodifiableList(
    new ArrayList<String>() {{
        add("open");
        add("save");
    }});

}
