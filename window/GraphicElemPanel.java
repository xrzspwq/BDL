package window;


import java.io.FileNotFoundException;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.event.EventHandler;
import java.util.*;
import javafx.geometry.Insets;

import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.Node;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

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


    public VBox getPanel() {
        return panel;
    }

    public boolean setDirectories()
    {
        try {
            FileReader libpaths = new FileReader("../libpaths.txt");
            BufferedReader buffRead = new BufferedReader(libpaths);
            String line = buffRead.readLine();   
            do
            {   
                File file = new File(line);
                TreeItem<Label> treeRoot = new TreeItem<>(new Label(file.getName()));
                treeRoot.setExpanded(false);

                TreeView<Label> view = new TreeView<>(treeRoot);

                    treeRoot.getGraphic().setOnMousePressed(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event)
                    {
                        
                    }
                });

                panel.getChildren().addAll(view);
                line = buffRead.readLine(); 

            }while(line != null);

            return true;
        } 
        catch (Exception e) {
            return false;
        }   
    }


}