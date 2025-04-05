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

import java.lang.reflect.Array;
import java.util.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Label;
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

    /**
     * This function sets the directory elements in a TreeView format.
     * It takes a list of {@link GraphicElem} objects as a parameter and returns a list of {@link TreeItem} objects.
     * Each {@link TreeItem} represents a directory element and contains a label with the name of the element.
     * The function also adds a mouse click event handler to each {@link TreeItem} to set the attributes panel with the corresponding {@link GraphicElem}.
     *
     * @param elems A list of {@link GraphicElem} objects representing directory elements.
     * @return A list of {@link TreeItem} objects representing the directory elements in a TreeView format.
     */
    private List<TreeItem<String>> setDirectoryElems(List<GraphicElem> elems)
    {
        ArrayList<TreeItem<String>> items = new ArrayList<>();

        for (GraphicElem elem : elems) 
        {
            TreeItem<String> item = new TreeItem("",new Label(elem.getElem().getName())); 

            item.getGraphic().setOnMousePressed(new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    App.setAttributesPanel(elem);
                }
            });

            items.add(item);
        }
        return items;
    }

    

    /**
     * This function returns the VBox panel containing the graphical elements.
     *
     * @return A VBox panel containing the graphical elements.
     */
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