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
        VIEW,
    }

   public static final List<String> fileMenuItems = Collections.unmodifiableList(
    new ArrayList<String>() {{
        add("Open file");
        add("Save circuit");
        add("Import");
        
    }});

}
