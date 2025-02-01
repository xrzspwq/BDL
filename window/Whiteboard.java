package window;

import javafx.scene.shape.*;
import javafx.scene.input.DragEvent;
import javafx.event.EventHandler;
import javafx.scene.input.TransferMode;
import java.util.HashMap;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Whiteboard
{
    private Pane panel;
    private HashMap<Integer,GraphicElem> circuitComponents;
    Shape shape;

    public Whiteboard()
    {
        panel = new Pane();

        panel.setOnDragOver(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(DragEvent event) 
            {
                if (/*event.getGestureSource() instanceof GraphicElemPanel)&&*/  event.getDragboard().hasString()) 
                {
                    panel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
                    event.acceptTransferModes(TransferMode.COPY);

                    switch (event.getDragboard().getString()) 
                    {
                        case "circle":
                            shape = new Circle(20.0f);
                            break;

                        case "rectangle":
                            shape = new Rectangle(20.0f,20.0f);
                            break;    
                        
                        default: 
                            shape = null;
                            break;
                    }
                }
                event.consume();
            }
        });

        panel.setOnDragDropped(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(DragEvent event) 
            {
                panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                shape.relocate(event.getX(),event.getY()); 
                panel.getChildren().addAll(shape);        
                event.setDropCompleted(shape != null);
                event.consume();
             }
        });
    }


    public Pane getPanel() {
        return panel;
    }
    
}
