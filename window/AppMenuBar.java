package window;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AppMenuBar 
{
    private final MenuBar menuBar;

    public AppMenuBar()
    {
        menuBar = new MenuBar(consFileMenu(),consEditMenu(),consViewMenu());
        menuBar.setUseSystemMenuBar(true);

    }

    private Menu consFileMenu()
    {
        MenuItem [] items = new MenuItem[ MenuBarElem.fileMenuItems.size()];
        for(int i=0; i < MenuBarElem.fileMenuItems.size(); ++i )
        {
            items[i] = new MenuItem( MenuBarElem.fileMenuItems.get(i));
        }
        return new Menu("File",new VBox(),items);
    
    }

    private Menu consEditMenu()
    {
        return new Menu("Edit",new VBox());
    }

    private Menu consViewMenu()
    {
        return new Menu("View",new VBox());
    }

    public MenuBar getPanel()
    {
        return menuBar;
    } 
}
