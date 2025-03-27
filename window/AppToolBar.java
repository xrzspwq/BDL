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
    
    public ToolBar getPanel() {
        return toolbar;
    }


    
}
