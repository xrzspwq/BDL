package window;


import java.lang.reflect.Array;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import src.Circuit;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import java.util.*;


import javafx.geometry.Insets;

import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;


public class GraphicElemPanel 
{
    private VBox panel;
    
    public GraphicElemPanel(int height)
    {
        panel = new VBox();
        panel.setSpacing(5.0);
        panel.setPrefHeight(height);
        boolean firstLoop = true;

        for (GraphicElem component : GraphicElemInfo.getComponents()) 
        {
            panel.getChildren().add(component.getShape());

            if (firstLoop) 
            {
                VBox.setMargin(component.getShape(),new Insets(5.0,0.0,0.0,0.0));
            }

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

            firstLoop = false;
        }

        //Circle circle = new Circle(20.0f);
        //panel.getChildren().addAll(circle);
    }

    /*
    private VBox setDirectoryElems(List<GraphicElem> elems)
    {
        VBox listItems;

        for (GraphicElem elem : elems) 
        {
            Button item = new Button("", new Label(elem.getElem().getName()));
            
            item.setOnMousePressed(new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    App.setAttributesPanel(elem);
                }
            });

            listItems.getChildren().add(item);
        }
    }*/
    
    public VBox getPanel() 
    {
        return panel;
    } 

}
