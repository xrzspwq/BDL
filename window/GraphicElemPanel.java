package window;

import javafx.scene.input.*;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

import java.lang.reflect.Array;
import java.util.*;

import org.w3c.dom.events.MouseEvent;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

public class GraphicElemPanel 
{
    private VBox panel;


    public GraphicElemPanel(int height)
    {
        panel = new VBox();
        panel.setSpacing(5.0);
        panel.setPrefHeight(height);
        boolean firstLoop = true;

        ScrollPane scrollPane = new ScrollPane(setDirectories());
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        panel.getChildren().add(scrollPane);
    }

    /**
     * This function returns the VBox panel containing the graphical elements.
     *
     * @return A VBox panel containing the graphical elements.
     */
    public VBox getPanel() {
        return panel;
    }


    public TreeView<String> setDirectories()
    {
        try {
            TreeItem<String> gates = new TreeItem<>("Gates");

            gates.setExpanded(true);

            String[] gateNames = {"OR", "AND", "NAND", "NOT", "XOR", "NOR", "NXOR"};

            for (String name : gateNames) {
                Label label = new Label(name);
                
                label.setOnDragDetected(event -> {
                    Dragboard dragboard = label.startDragAndDrop(TransferMode.COPY);
                    ClipboardContent content = new ClipboardContent();

                    content.putString(name);

                    dragboard.setContent(content);
                    event.consume();
                });

                TreeItem<String> item = new TreeItem<>("",label);
                gates.getChildren().add(item);
            }

            TreeView<String> res = new TreeView<>(gates);

            return res;
        } 
        catch (Exception e) {
           return null;
        }   
    }


}