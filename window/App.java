package window;

import java.awt.Cursor;
import java.awt.Dimension;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.awt.Toolkit;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class App extends Application 
{
    static private AppMenuBar menubar;
    static private AppToolBar toolbar;
    static private GraphicElemPanel graphicElemPanel;
    static private ElemAttributesPanel elemAttributesPanel;
    static private Whiteboard whiteBoard;
    
    public void setAttributesPanel(GraphicElem elem)
    {
        elemAttributesPanel.setPanel(elem);
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
        elemAttributesPanel = new ElemAttributesPanel(new GraphicElem(new AndGate()),200);
        whiteBoard = new Whiteboard();

        HBox leftPanel = new HBox(toolbar.getPanel(),new VBox(graphicElemPanel.getPanel(),elemAttributesPanel.getPanel()));
        leftPanel.setSpacing(10.0);
        leftPanel.getStyleClass().add("leftPanel");

        BorderPane root = new BorderPane(whiteBoard.getPanel());
        root.setTop(menubar.getPanel());
        root.setLeft(leftPanel);
        leftPanel.setPrefWidth(screenRes.width / 4);

        Scene mainScene = new Scene(root,screenRes.width/1.2,screenRes.height/1.2);
        mainScene.getStylesheets().add("window/rsc/css/app.css");

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }

}