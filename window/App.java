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
    static private BorderPane root;
        
    /*
     * Sets the whiteboard to the center of the root pane and clears the attributes panel.
     *
     * @param whiteboard The whiteboard to be set as the center of the root pane.
     *
     * @return void
     *
     * @since 1.0
     */
    public static void setWhiteBoard(Whiteboard whiteboard)
    {
        root.setCenter(whiteboard.getPanel());
        elemAttributesPanel.clearPanel();
    }


    /*
     * Sets the attributes panel to display the properties of the given graphic element.
     *
     * @param elem The graphic element for which the attributes panel will display properties.
     *
     * @return void
     *
     * @since 1.0
     */
    public static void setAttributesPanel(GraphicElem elem)
    {
        elemAttributesPanel.setPanel(elem);
    }


    /*
     * Returns the instance of the GraphicElemPanel used in the application.
     * This panel is responsible for displaying and managing the available graphic elements.
     *
     * @return The instance of the GraphicElemPanel.
     *
     * @since 1.0
     */
    public final static GraphicElemPanel getGraphicElemPanel() 
    {
        return graphicElemPanel;
    }


    /**
     * Returns the instance of the Circuit used in the application.
     * The Circuit class represents the logical circuit diagram and contains the necessary methods and properties to manipulate and analyze the circuit.
     *
     * @return The instance of the Circuit.
     *
     * @since 1.0
     */
    public final static Circuit getCircuit() 
    {
        return whiteBoard.circuit;
    }


    /**
     * Returns the instance of the HashMap used to store the graphic elements in the application.
     * The HashMap is used to efficiently retrieve and manipulate the graphic elements based on their unique IDs.
     *
     * @return A HashMap containing the unique IDs of the graphic elements as keys and the corresponding GraphicElem objects as values.
     *
     * @since 1.0
     */
    public final static HashMap<Integer,GraphicElem>  getHashMap() 
    {
        return whiteBoard.getHashMap();
    }


    /**
     * Sets the cursor of the main scene to the specified cursor.
     *
     * This method is used to change the cursor's appearance when the mouse pointer is over the main scene.
     * The cursor is set to the provided {@code cursor} parameter.
     *
     * @param cursor The cursor to be set for the main scene.
     *
     * @return void
     *
     * @since 1.0
     */
    static public void setCursor(Cursor cursor)
    {
        mainScene.setCursor(cursor);
    }

    
    
    /**
     * The start method is the entry point for the JavaFX application.
     * It initializes the main window, sets up the user interface components, and handles user interactions.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     *
     * @return void
     *
     * @since 1.0
     */
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

        root = new BorderPane(whiteBoard.getPanel());
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

        mainScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() 
        {

            public void handle(KeyEvent event) 
            {
                if (event.getCode().equals(KeyCode.BACK_SPACE)) 
                {
                    whiteBoard.remove();
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


    /**
     * The main entry point of the JavaFX application.
     *
     * This method is called when the JavaFX runtime environment is ready to launch the application.
     * It initializes the primary stage, sets up the user interface, and handles user interactions.
     *
     * @param args The command line arguments passed to the application.
     *
     * @return void
     *
     * @since 1.0
     */
    public static void main(String[] args) 
    {
        launch(args);
    }


}