package window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToolBarElem 
{
    public static final List<String> pathIcons = Collections.unmodifiableList(
        new ArrayList<String>() {{
            add("window/rsc/img/icons/cursor.svg");
            add("window/rsc/img/icons/font.svg");
            add("window/rsc/img/icons/hand_cursor.svg");
        }});
}
