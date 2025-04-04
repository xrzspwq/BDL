package window;

import logic.*;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ChoiceBox;
import java.util.*;

public class ElemAttributesPanel 
{
    private VBox panel;
    private Label name;
    private GridPane attributesGrid;

    public ElemAttributesPanel(int height)
    {
        panel = new VBox();
        panel.setPrefHeight(height);
        panel.setSpacing(3.0);
    }

    public ElemAttributesPanel(GraphicElem elem,int height)
    {
        panel = new VBox();
        panel.setPrefHeight(height);
        panel.setSpacing(3.0);
        
        name = new Label(elem.getElem().getName());
        VBox.setVgrow(name, Priority.ALWAYS);
        name.setFont(new Font(30));
        name.setPrefHeight(height/4);

        attributesGrid = new GridPane();
        VBox.setVgrow(attributesGrid, Priority.ALWAYS);
        attributesGrid.setPrefHeight(height/4*3);
        attributesGrid.setHgap(8.0);
        attributesGrid.setVgap(5.0);

        setPanel(new GraphicElem(new Circuit("test")));

        panel.getChildren().addAll(name,attributesGrid);
    }   
    
    public void setPanel(GraphicElem elem)
    {
        name.setText(elem.getElem().getName());

        attributesGrid.getChildren().clear();

        List<Object> elemAttributes = elem.getAttributes();
        for (int i=0; i < elemAttributes.size(); ++i) 
        {
            if (! (elemAttributes.get(i) instanceof Attribute<?>) )
                System.err.println("throw un truc");
           
            Attribute<?> attribute = (Attribute<?>) elemAttributes.get(i);
            ChoiceBox choiceBox = new ChoiceBox();

            for (Object option : GraphicElemInfo.getOptions(attribute)) 
            {
                choiceBox.getItems().add(option.toString());
            }

            attributesGrid.add(new Label(attribute.getName()),0,i);
            attributesGrid.add(choiceBox,1,i);   
        }
    }

    public void clearPanel()
    {
        panel.getChildren().clear();
    }

    public VBox getPanel() {
        return panel;
    }
}
