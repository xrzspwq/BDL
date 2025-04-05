package window;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.control.ToolBar;
import javafx.scene.shape.SVGPath;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.geometry.Orientation;
import javafx.scene.layout.Region;

public class AppToolBar 
{
    private final ToolBar toolbar;

    /**
     * Constructs an instance of the AppToolBar class with the specified width.
     *
     * @param width The width of the toolbar. This value is used to set the width of the JavaFX ToolBar instance.
     *
     * The AppToolBar class is responsible for creating and managing a vertical toolbar in a JavaFX application.
     * It initializes the toolbar with various icons and controls, and provides a method to retrieve the JavaFX ToolBar instance.
     *
     * The constructor initializes the toolbar with the specified width, sets its orientation to vertical, and adds a CSS style class.
     * It then iterates through a list of SVG paths, creates SVG icons, and adds them as buttons to the toolbar.
     * Finally, it adds all the buttons to the toolbar and returns the JavaFX ToolBar instance.
     */
    public AppToolBar(double width)
    {
        toolbar = new ToolBar();  
        toolbar.setOrientation(Orientation.VERTICAL);
        toolbar.getStyleClass().add("toolBar");

        ArrayList<Button> iconsButtons = new ArrayList<>(ToolBarElem.pathIcons.size());

        for(String svgPath : ToolBarElem.pathIcons )
        {
            final SVGPath icon = new SVGPath();
            try 
            {
                icon.setContent( (new ReaderSVGPath(svgPath)).getPath());
            } catch ( FileNotFoundException | IllegalArgumentException e) { System.out.println(e.getMessage());}


            final Button button = new Button();
            button.setShape(icon);

            button.setPrefSize(40, 40);
            button.getStyleClass().add("toolBarIcon");

            iconsButtons.add(button);
        }

        toolbar.getItems().addAll(iconsButtons);
    }

    
    /**
     * Returns the JavaFX ToolBar instance associated with this AppToolBar.
     *
     * @return The JavaFX ToolBar instance representing the application's toolbar.
     * 
     * This method provides access to the JavaFX ToolBar instance that is used to display
     * various icons and controls in the application's toolbar. The returned ToolBar instance
     * can be added to a JavaFX Scene to display the toolbar in the user interface.
     */
    public ToolBar getPanel() {
        return toolbar;
    }



    
}
