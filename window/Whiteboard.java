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
import javafx.scene.Cursor;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.ScrollEvent;


public class Whiteboard
{
    private Pane panel;
    private HashMap<Integer,GraphicElem> circuitComponents;
    Shape shapeToAdd;
    public boolean cursorOverHere = false;
    private boolean cursorSelectionMode = true;
    private boolean mouseTranslation = false;
    private Double zoomMultiplicator = 1.025;
    private Double mousePos [];

    //GraphicElem elemToAdd;

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
                            shapeToAdd = new Circle(20);  
                            break;

                        case "rectangle":
                            shapeToAdd = new Rectangle(30,30);
                            break;    
                        
                        default:
                            Polygon polygon = new Polygon();
                            polygon.getPoints().addAll(new Double[]{
                            40.0,0.0, 
                            0.0,0.0,
                            20.0,20.0,
                           });
                            shapeToAdd = polygon; 
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
                shapeToAdd.relocate(event.getX(),event.getY()); 
                add(shapeToAdd);        
                event.setDropCompleted(shapeToAdd != null);
                event.consume();
             }
        });
            
        panel.setOnMouseEntered(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                if (!cursorSelectionMode) 
                    App.setCursor(Cursor.OPEN_HAND);

                cursorOverHere = true;

                /*
                App.setCursor(Cursor.OPEN_HAND);
                changeCursorSelectionMode();
                */
                event.consume();
             }
        });

        panel.setOnMousePressed(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
              if (!cursorSelectionMode) 
              {
                App.setCursor(Cursor.CLOSED_HAND);
                mouseTranslation = true;
                mousePos = new Double[2];
                mousePos[0] = event.getX();
                mousePos[1] = event.getY();
                event.consume();
              }
            }
        });
     
        panel.setOnMouseDragged(new EventHandler<MouseEvent >() 
        {
            @Override
            public void handle(MouseEvent  event) 
            {
                if (mouseTranslation && !cursorSelectionMode) 
                {
                    Double deltaMousePos [] = new Double[2];
                    deltaMousePos[0] =  event.getX() - mousePos[0];
                    deltaMousePos[1] =  event.getY() - mousePos[1];
                    
                    for (Node elem : panel.getChildren()) 
                    {
                        elem.setTranslateX(deltaMousePos[0]);
                        elem.setTranslateY(deltaMousePos[1]);
                    }
                }
                event.consume();
            }
        });

        panel.setOnMouseReleased(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                if (mouseTranslation && !cursorSelectionMode) 
                {
                    App.setCursor(Cursor.OPEN_HAND);
                    mouseTranslation = false;
                }
                event.consume();
            }
        });

        panel.setOnMouseExited(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
               App.setCursor(Cursor.DEFAULT);
               cursorOverHere = false;
               //changeCursorSelectionMode();
               event.consume();
            }
        });

        panel.setOnScroll(new EventHandler<ScrollEvent>() 
        {
            @Override 
            public void handle(ScrollEvent event) 
            {
                for (Node elem : panel.getChildren()) 
                {
                    //elem.setScaleX(elem.getScaleX()*event.getDeltaY()*zoomMultiplicator);
                    //elem.setScaleY(elem.getScaleY()*event.getDeltaY()*zoomMultiplicator);

                }
            }
        });

    }

    private void add(Shape shape) 
    {
        shape.getStyleClass().add("whiteBoardElem");
        panel.getChildren().addAll(shape);  
        
        shape.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                //en attendant juste pour test   
                if (shape instanceof Rectangle)
                    App.setAttributesPanel(new GraphicElem(new Circuit("project")));

                if (shape instanceof Circle)
                    App.setAttributesPanel(new GraphicElem(new AndGate()));

                if (shape instanceof Polygon)
                    App.setAttributesPanel(new GraphicElem(new OrGate()));
            }
        });
    }

    public void changeCursorSelectionMode()
    {
        if (cursorSelectionMode) 
        {
            cursorSelectionMode = false;
            App.setCursor(Cursor.OPEN_HAND);
            for (Node elem : panel.getChildren()) 
            {
                elem.getStyleClass().add("whiteBoardElem");
            }
        } 
        
        else
        {
            cursorSelectionMode = true;
            App.setCursor(Cursor.DEFAULT);
            for (Node elem : panel.getChildren()) 
            {
                elem.getStyleClass().removeAll();
            }
        }
    }

    public Pane getPanel() {
        return panel;
    }
}
