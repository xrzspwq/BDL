package window;

import java.io.File;
import java.util.ArrayList;

import filemanager.*;
import src.*;
import window.App;
import window.GraphicElem;

import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class MenuBarEvents 
{

   /* public static void openFile(Window window){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setSelectedExtensionFilter(new ExtensionFilter("JSON files","*.json"));

        File file = fileChooser.showSaveDialog(window);
        /*try{
            CircuitLoader cirLoad = new CircuitLoader(file);
            cirLoad.loadCircuit();
        } catch(Exception e) {
            ButtonType button = new ButtonType("Ok");
            Alert errAlert = new Alert(AlertType.ERROR, "Loading circuit has failed",button);
            errAlert.showAndWait();
        }
    } */

    /*public static void saveFile(Window window){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file to");
        File file = fileChooser.showSaveDialog(window);

        try {
            CircuitSaver cirSaver = new CircuitSaver();
            cirSaver.saveCircuit(App.getHashMap());
        }
        catch(Exception e) {
            ButtonType button = new ButtonType("Ok");
            Alert errAlert = new Alert(AlertType.ERROR, "Saving circuit has failed",button);
            errAlert.showAndWait(); 
        }
    } */ 

    /*public static void importElemFromFile(Window window){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import component from file");
        fileChooser.setSelectedExtensionFilter(new ExtensionFilter("JSON files","*.json"));

        File file = fileChooser.showSaveDialog(window);
        try{
            ImportElem impElem = new ImportElem(file);
            GraphicElem newElem = impElem.importElem();
            impElem().addToStdlib(newElem);

        } catch(Exception e) {
            ButtonType button = new ButtonType("Ok");
            Alert errAlert = new Alert(AlertType.ERROR, "Importing new component has failed",button);
            errAlert.showAndWait();
        }
    }*/
    
     /*
    private void saveHandler()
    {
        new CircuitSaver("saves/" + App.getCircuit().getName()).saveCircuit(new ArrayList<GraphicElem>(App.getHashMap().values()));
    } 
    */   
}
