package window;


import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.event.EventHandler;
import java.util.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;


public class GraphicElemPanel 
{
    private VBox panel;


    public GraphicElemPanel(int height)
    {
        panel = new VBox();
        panel.setPrefHeight(height);

        for (GraphicElem component : GraphicElemInfo.getComponents()) 
        {
            panel.getChildren().add(component.getShape());
            component.getShape().setOnDragDetected(new EventHandler<MouseEvent>() 
            {
                @Override public void handle(MouseEvent event) 
                {
                    Dragboard dragboard = component.getShape().startDragAndDrop(TransferMode.COPY);
                    ClipboardContent content = new ClipboardContent();

                    if (component.getShape() instanceof Rectangle)
                        content.putString("rectangle");

                    if (component.getShape() instanceof Circle)
                        content.putString("circle");

                    if (component.getShape() instanceof Polygon)
                        content.putString("polygon");

                    dragboard.setContent(content);  
                    event.consume();
                }
            });
        }

        //Circle circle = new Circle(20.0f);
        //panel.getChildren().addAll(circle);
    }


    public VBox getPanel() {
        return panel;
    }


}
