package window;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class AppMenuBar 
{
    private final MenuBar menuBar;
    private final Window currWindow;

    public AppMenuBar()
    {
        menuBar = new MenuBar(consFileMenu(),consEditMenu(),consViewMenu());
        menuBar.setUseSystemMenuBar(true);
        currWindow = new Window(null);
    }

    public AppMenuBar(Window window)
    {
        menuBar = new MenuBar(consFileMenu(),consEditMenu(),consViewMenu());
        menuBar.setUseSystemMenuBar(true);
        currWindow = window;
    }

    /**
     * Constructs and returns a File menu for the application's menu bar.
     * The File menu contains a list of menu items specified in the {@link MenuBarElem#fileMenuItems} list.
     *
     * @return A {@link Menu} object representing the File menu.
     */
    private Menu consFileMenu()
    {
        // Create an array of MenuItem objects based on the fileMenuItems list
        MenuItem [] items = new MenuItem[ MenuBarElem.fileMenuItems.size()];
        for(int i=0; i < MenuBarElem.fileMenuItems.size()-1; ++i )
        {
            items[i] = new MenuItem( MenuBarElem.fileMenuItems.get(i));
        }
        items[0].setOnAction(new Eventhandler<MouseEvent>(){
            @Override
            public final void handle(MouseEvent event){
                MenuBarEvents.loadFile(currWindow);
            }
        });
        items[1].setOnAction(new Eventhandler<MouseEvent>(){
            @Override
            public final void handle(MouseEvent event){
                MenuBarEvents.saveFile(currWindow);
            }
        });

        Menu importElem = new Menu(items.get(2).getName());
        MenuItem importElemFromFile = new MenuItem();
        importElem.getItems().add(importElemFromFile);

        importElemFromFile.setOnAction(new Eventhandler<MouseEvent>(){
            @Override
            public final void handle(MouseEvent event){
                MenuBarEvents.importElemFromFile(currWindow);
            }
        });

        items.add(importElem);

        // Return a new Menu object with the label "File", a VBox layout, and the created menu items
        return new Menu("File",new VBox(),items);
    }


    /**
     * Constructs and returns an Edit menu for the application's menu bar.
     * The Edit menu currently does not contain any specific menu items.
     *
     * @return A {@link Menu} object representing the Edit menu.
     *         The menu has the label "Edit" and is displayed using a {@link VBox} layout.
     */
    private Menu consEditMenu()
    {
        return new Menu("Edit", new VBox());
    }


    /**
     * Constructs and returns a View menu for the application's menu bar.
     * The View menu currently does not contain any specific menu items.
     *
     * @return A {@link Menu} object representing the View menu.
     *         The menu has the label "View" and is displayed using a {@link VBox} layout.
     */
    private Menu consViewMenu()
    {
        return new Menu("View", new VBox());
    }


    /**
     * Returns the menu bar panel for the application.
     *
     * @return The {@link javafx.scene.control.MenuBar} object representing the menu bar panel.
     *         This menu bar contains the File, Edit, and View menus, which are constructed
     *         using the {@link #consFileMenu()}, {@link #consEditMenu()}, and {@link #consViewMenu()} methods, respectively.
     *         The menu bar is configured to use the system menu bar if the operating system supports it.
     */
    public MenuBar getPanel()
    {
        return menuBar;
    }

}
