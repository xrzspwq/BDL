package window;

import src.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;
import javafx.beans.value.*;

public class App extends Application 
{
    static private AppMenuBar menubar;
    static private AppToolBar toolbar;
    static private GraphicElemPanel graphicElemPanel;
    static private ElemAttributesPanel elemAttributesPanel;
    static private Whiteboard whiteBoard;
    static private Scene mainScene;
        
    static public void setAttributesPanel(GraphicElem elem)
    {
        elemAttributesPanel.setPanel(elem);
    }

    public final static GraphicElemPanel getGraphicElemPanel() 
    {
        return graphicElemPanel;
    }

    public final static Circuit getCircuit() 
    {
        return whiteBoard.circuit;
    }

    public final static HashMap<Integer,GraphicElem>  getHashMap() 
    {
        return whiteBoard.getHashMap();
    }

    static public void setCursor(Cursor cursor)
    {
        mainScene.setCursor(cursor);
    }
    
    
    @Override
    public void start(Stage primaryStage) 
    {
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();

        primaryStage.setResizable(true);
        primaryStage.setTitle("Logique");
        primaryStage.setMaxHeight(screenRes.height);
        primaryStage.setMaxWidth(screenRes.width);
        primaryStage.setMinHeight(screenRes.height / 3);
        primaryStage.setMinWidth(screenRes.width / 3);

        menubar = new AppMenuBar();
        toolbar = new AppToolBar(300);
        graphicElemPanel = new GraphicElemPanel(200);
        elemAttributesPanel = new ElemAttributesPanel(new GraphicElem(new And()),200);
        whiteBoard = new Whiteboard();

        HBox leftPanel = new HBox(toolbar.getPanel(),new VBox(graphicElemPanel.getPanel(),elemAttributesPanel.getPanel()));
        leftPanel.setSpacing(10.0);
        leftPanel.getStyleClass().add("leftPanel");

        BorderPane root = new BorderPane(whiteBoard.getPanel());
        root.setTop(menubar.getPanel());
        root.setLeft(leftPanel);
        leftPanel.setPrefWidth(screenRes.width / 4);

        mainScene = new Scene(root,screenRes.width/1.2,screenRes.height/1.2,true);
        mainScene.getStylesheets().add("window/rsc/css/app.css");


        mainScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() 
        {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.C,KeyCombination.SHIFT_DOWN);
            public void handle(KeyEvent event) 
            {
                if (keyComb.match(event)) 
                {
                    if(whiteBoard.cursorOverHere)
                    {
                        whiteBoard.changeCursorSelectionMode();                        
                    }     
                    event.consume();
                }
            }
        });


        whiteBoard.getPanel().widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable,Number oldValue,Number newValue) 
            {  
                whiteBoard.setBackground();
                Double colNb = (Double) newValue / whiteBoard.getGridRatio();
                System.out.println("whiteBoard width : " + newValue);
                System.out.println("\ncolNb : " + colNb.intValue());
                whiteBoard.setGridSize(whiteBoard.getGridLineNb(),colNb.intValue());
            }
        });

        whiteBoard.getPanel().heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable,Number oldValue,Number newValue) 
            {  
                whiteBoard.setBackground();
                Double linNb = (Double) newValue / whiteBoard.getGridRatio();
                System.out.println("whiteBoard height : " + newValue);
                System.out.println("\nlineNb : " + linNb.intValue());
                whiteBoard.setGridSize(linNb.intValue(),whiteBoard.getGridColNb());
            }
        });


        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }

}