package window;

import src.*;

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
    
    /**
     * Sets the panel with the element's name and attributes.
     *
     * This method populates the panel with the element's name and attributes based on the provided GraphicElem object.
     * The element's name is set in the name Label, and the attributes are displayed in a GridPane.
     * Each attribute is represented by a Label and a ChoiceBox. The ChoiceBox is populated with the options
     * retrieved from the GraphicElemInfo class using the attribute.
     *
     * @param elem The GraphicElem object containing the element's name and attributes.
     * @return void
     * @since 1.0
     */
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


    /**
     * Clears the panel by removing all its children.
     *
     * This method is used to reset the panel to its initial state,
     * by removing all the graphical elements and attributes displayed.
     *
     * @return void
     * @since 1.0
     */
    public void clearPanel()
    {
        panel.getChildren().clear();
    }


    /**
     * Returns the VBox panel containing the element's name and attributes.
     *
     * This method retrieves the VBox panel that is used to display the element's name and attributes.
     * The panel is created in the constructor of the ElemAttributesPanel class and is populated with
     * the element's name and attributes based on the provided GraphicElem object.
     *
     * @return VBox The VBox panel containing the element's name and attributes.
     * @since 1.0
     */
    public VBox getPanel() {
        return panel;
    }

}
