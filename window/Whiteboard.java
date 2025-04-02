package window;

//import java.nio.file.Path;
import src.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.shape.*;
import javafx.scene.input.DragEvent;
import javafx.event.EventHandler;
import javafx.scene.input.TransferMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.ScrollEvent;
import javafx.geometry.BoundingBox;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.geometry.Bounds;

import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D;
import java.lang.Math;

public class Whiteboard
{
    /*
    private enum State
    {
        DRAGGING_ELEM,
    }
    */

    private Pane panel;
    private HashMap<Integer,GraphicElem> circuitComponents;
    public  Circuit circuit;
    private GraphicElem elemToAdd;
    public  boolean cursorOverHere = false; //cursor is on the whiteBoard
    private boolean cursorSelectionMode = true; //cursor is in selection mode or either hand mod
    private boolean drawingWire = false;
    private double zoomMultiplicator = 1.025;
    private double scaleFactor = 1; 
    private double initMousePos []; //initial mouse position when starting mouse dragging 
    private double transValue [] = null; //accumlated translation value of the scene
    private BoundingBox selectionBounds = null; //boudingBox of the the selection currently being made
    private Rectangle selectionRec = null ;
    private Rectangle selectedElemRec = null; //rectangle containing all selected elements 
    private boolean selectedElemTranslationActive = false; 
    private double[] selectedElemRecTransValue = null; 
    private HashMap<Integer,double[]> selectedElemTransValue;
    private ArrayList<Integer> selectedElemsId; 
    private ArrayList<ArrayList<Boolean>> grid; //true for something present, false otherwise
    private int gridLineNb = 0;
    private int gridColNb = 0;
    private double gridRatio = 20.0; //double unit*gridRatio = height/width of a cell of the grid 
    private final static double backgroundDotRadius = 0.05; 

    public Whiteboard()
    {
        panel = new Pane();
        circuitComponents = new HashMap<>();
        selectedElemsId = new ArrayList<>();
        selectedElemTransValue = new HashMap<>();
        transValue = new double[] {0.0,0.0};
        selectedElemRecTransValue = new double[2];
        grid = new ArrayList<>();

        panel.setOnDragOver(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(DragEvent event) 
            {  
                dragOverComponentHandler(event);
                event.consume();
            }
        });

        panel.setOnDragExited(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(DragEvent event) 
            {  
                panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                event.consume();
            }
        });

        panel.setOnDragDropped(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(DragEvent event) 
            {
                panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                add(elemToAdd,event.getX(),event.getY());        
                event.setDropCompleted(elemToAdd.getShape() != null);
                event.consume();
             }
        });
            
        panel.setOnMouseEntered(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                if (!cursorSelectionMode) 
                    App.setCursor(Cursor.OPEN_HAND);
                cursorOverHere = true;  
                event.consume();
             }
             
        });

        panel.setOnMousePressed(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                mousePressedHandler(event);
                event.consume();
            }
        });
     
        panel.setOnMouseDragged(new EventHandler<MouseEvent >() 
        {
            @Override
            public void handle(MouseEvent  event) 
            {
                mouseDraggedHandler(event);
                event.consume();
            }
        });

        panel.setOnMouseReleased(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                mouseReleasedHandler(event);
                event.consume();
            }
        });

        panel.setOnMouseExited(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
               App.setCursor(Cursor.DEFAULT);
               cursorOverHere = false;
               event.consume();
            }
        });

        panel.setOnScroll(new EventHandler<ScrollEvent>() 
        {
            @Override 
            public void handle(ScrollEvent event) 
            {
                for (Node elem : panel.getChildren()) 
                {
                    /*
                    if(event.getDeltaY() < 0)
                        scaleFactor += 1/(event.getDeltaY()/event.getMultiplierY()*zoomMultiplicator);
                    else
                        scaleFactor += event.getDeltaY()/event.getMultiplierY()*zoomMultiplicator;

                    elem.setScaleX(scaleFactor);
                    elem.setScaleY(scaleFactor);
              
                    System.err.println( "event.getDeltaY() : " + event.getDeltaY() + "event.getMultiplierY() : " + event.getMultiplierY());
                    System.err.println( "scaleFactor : " + scaleFactor);
                     */
                }
            }
        });

    }

    public Whiteboard(HashMap<Integer,GraphicElem> circuit)
    {
        this();
        //not finished 
    }

    public void changeCursorSelectionMode() 
    {
        if (cursorSelectionMode) 
        {
            cursorSelectionMode = false;
            App.setCursor(Cursor.OPEN_HAND);
            for (Node elem : panel.getChildren()) 
            {
                //elem.getStyleClass().clear();
                //elem.getStyleClass().add("jsp");
            }
        } 
        
        else
        {
            cursorSelectionMode = true;
            App.setCursor(Cursor.DEFAULT);
            for (Node elem : panel.getChildren()) 
            {
                //elem.getStyleClass().add("whiteBoardElem");
            }
        }
    }

    public void setGridSize(int lineNb,int colNb)
    {
        if(lineNb < gridLineNb)
        {
            for(int i = lineNb-1; i < gridLineNb; ++i)
            {
                grid.remove(i);
            }
            grid.trimToSize();
        }

        if(colNb < gridColNb)
        {
            for(int i = 0; i < gridColNb; ++i)
            {
                for (int j = colNb-1; j < gridColNb; ++j) 
                {
                    grid.get(i).remove(j);                    
                }
                grid.get(i).trimToSize();
            }
        }
        
        if (lineNb > gridLineNb) 
        {
            grid.ensureCapacity(lineNb);
            for(int line = 0; line < lineNb-gridLineNb; ++line)
            {
                grid.add(new ArrayList<>());

                grid.get(line+gridLineNb).ensureCapacity(gridColNb);
                
                for (int col = 0; col < gridColNb; ++col) 
                {
                    grid.get(line+gridLineNb).add(false);
                }
            }
            gridLineNb = lineNb;

            setGrid(); 
        }

        if (colNb > gridColNb) 
        {
            for(int line = 0; line < gridLineNb; ++line)
            {
                grid.get(line).ensureCapacity(colNb);

                for (int col = 0; col < colNb-gridColNb; ++col) 
                {
                    grid.get(line).add(false);
                }
            }
           setGrid(); 
        }
    }

    private void setGrid()
    {
        for(GraphicElem elem : circuitComponents.values())
        {
            Bounds elemBounds = elem.getShape().getBoundsInParent();

            if(elemBounds.getMinX() > panel.getBoundsInLocal().getMaxX()
                ||
                elemBounds.getMaxX() < 0
                ||
                elemBounds.getMinY() > panel.getBoundsInLocal().getMaxY()
                ||
                elemBounds.getMaxY() < 0 )
                continue;
            
            
            int[] gridStart = new int[2];
            int[] gridEnd = new int[2];

            gridStart[0] = (int) (elemBounds.getMinX() / gridRatio);
            gridStart[1] = (int) (elemBounds.getMinY() / gridRatio);

            gridEnd[0] = gridStart[0] + ((int) Math.floor(Math.abs(elemBounds.getMaxX() - elemBounds.getMinX()) / gridRatio));
            gridEnd[1] = gridStart[1] + ((int) Math.floor(Math.abs(elemBounds.getMaxY() - elemBounds.getMinY()) / gridRatio));

            for(int line = gridStart[0]; line < gridEnd[0]; ++line )
            {
                for(int col = gridStart[1]; col < gridEnd[1]; ++col)
                {
                    grid.get(line).set(col,true);
                }
            }
        }
    }

    public void setBackground()
    {
        int i=0;
        
        for (int line=1; line <= panel.getBoundsInLocal().getMaxX() / gridRatio; ++line) 
        {
            ArrayList<Circle> background = new ArrayList<>();
            for (int col=1; col <= panel.getBoundsInLocal().getMaxY() / gridRatio; ++col) 
            {
                Circle circle = new Circle(line*gridRatio,col*gridRatio,backgroundDotRadius);
                background.add(circle);
                circle.getStyleClass().add("whiteBoardBackgroundDot");
                circle.toBack();
            
            }
            panel.getChildren().addAll(background);
        }

        /* 
        SVGPath background = new SVGPath();
        String svgContent = new String();

        for (int line=1; line <= panel.getBoundsInLocal().getMaxX() / gridRatio; ++line) 
        {
            for (int col=1; col <= panel.getBoundsInLocal().getMaxY() / gridRatio; ++col) 
            {
                svgContent+= circleSvg(new double[]{line*gridRatio,col*gridRatio});
            }
        }

        background.setContent(svgContent);
        background.getStyleClass().add("whiteBoardBackgroundDot");
        panel.getChildren().addAll(background);
        */     

    }

    private String circleSvg(double[] centerPos)
    {
        String res1 = "M"+centerPos[0]+','+(centerPos[1]+backgroundDotRadius)+" A"+backgroundDotRadius+","+backgroundDotRadius;
        String res2 = 4.4847698*backgroundDotRadius+","+ 4.4847698*backgroundDotRadius;

        return res1+"0 0,0"+res2+res1+"0 1,0"+res2;

        /*
        String res;
        res+="M"+centerPos[0]+','+(centerPos[1]+backgroundDotRadius)+" A"+backgroundDotRadius+","+backgroundDotRadius+"0 0,0"
        +Math.cos(Math.PI/2)*backgroundDotRadius+","+Math.sin(Math.PI/2)*backgroundDotRadius

        +"M"+centerPos[0]+','+(centerPos[1]+backgroundDotRadius)+" A"+backgroundDotRadius+","+backgroundDotRadius+"0 1,0"
        +Math.cos(Math.PI/2)*backgroundDotRadius+","+Math.sin(Math.PI/2)*backgroundDotRadius ;
        */
    }

    private void add(GraphicElem elem,double xPos,double yPos) 
    {
        circuitComponents.put(elem.getId(), elem);
        Shape shape = elem.getShape();
        elemToAdd.getShape().relocate(xPos,yPos); 
        elem.setPos(new Point2D.Double(xPos, yPos));

        shape.getStyleClass().add("whiteBoardElem");
        panel.getChildren().addAll(shape);  

        shape.setScaleX(scaleFactor);
        shape.setScaleY(scaleFactor);
        
        setInOut(elem);

        //add to the grid 
        int[] gridStart = new int[2];
        int[] gridEnd = new int[2];

        gridStart[0] = (int) (shape.getBoundsInParent().getMinX() / gridRatio);
        gridStart[1] = (int) (shape.getBoundsInParent().getMinY() / gridRatio);
        gridEnd[0] = gridStart[0] + ((int) Math.floor(Math.abs(shape.getBoundsInParent().getMaxX() - shape.getBoundsInParent().getMinX()) / gridRatio));
        gridEnd[1] = gridStart[1] + ((int) Math.floor(Math.abs(shape.getBoundsInParent().getMaxY() - shape.getBoundsInParent().getMinY()) / gridRatio));
        for(int line = gridStart[0]; line < gridEnd[0]; ++line )
        {
            for(int col = gridStart[1]; col < gridEnd[1]; ++col)
            {
                grid.get(line).set(col,true);
            }
        }


        System.err.println("\nelem.getBoundsInParent().getMinX() : " + elem.getShape().getBoundsInParent().getMinX() 
        + " elem.getBoundsInParent().getMinY() : " + elem.getShape().getBoundsInParent().getMinY()  );

        for(Node ah : panel.getChildren())
        {
            if (ah instanceof Circle) 
            {   
                System.err.println("getBoundsInParent().getMinX() : " + ah.getBoundsInParent().getMinX() 
                                    + " getBoundsInParent().getMinY() : " + ah.getBoundsInParent().getMinY()  );
            }
        }

        shape.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                //en attendant juste pour test   
                if (shape instanceof Rectangle)
                    App.setAttributesPanel(new GraphicElem(new Circuit("project")));

                if (shape instanceof Circle)
                    App.setAttributesPanel(new GraphicElem(new And()));

                if (shape instanceof Polygon)
                    App.setAttributesPanel(new GraphicElem(new Or()));
                
                event.consume();
            }
        });
    }

    private void remove() //remove all currenly selected elem
    {
        if(selectedElemsId == null)
            return; 

        for(int id : selectedElemsId)
        {

        }

    }
    
    private void setInOut(GraphicElem elem)
    {
        System.err.println("LIST INPUT SIZE : " + elem.getInputs().size());
        elem.setInOut();

        for (Circle inputCircle : elem.getInputs()) 
        {
            panel.getChildren().addAll(inputCircle);  
        }
    }

    private void dragOverComponentHandler(DragEvent event) //add corresponding elemLogic dragged from graphicElem
    {
        Node source = (Node) event.getGestureSource();
            if ( (source.getParent().equals(App.getGraphicElemPanel().getPanel())) && event.getDragboard().hasString()) 
            {            
                panel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
                event.acceptTransferModes(TransferMode.COPY);

                switch (event.getDragboard().getString()) 
                {
                    case "circle":
                        elemToAdd = new GraphicElem(new And());
                        break;

                    case "rectangle":
                        elemToAdd = new GraphicElem(new Circuit());
                        break;    

                    default:
                        elemToAdd = new GraphicElem(new Or());
                        break;
                }
            }
    }

    private void setSelectedElemRec(double[] minPos,double [] maxPos) //bounding box containing all selected elements 
    {
        selectedElemRec = new Rectangle(minPos[0],minPos[1],Math.abs(maxPos[0]-minPos[0]),Math.abs(maxPos[1]-minPos[1]));
        selectedElemRec.getStyleClass().add("recSelectedElem");
        panel.getChildren().addAll(selectedElemRec);  
    }
    
    private void mousePressedHandler(MouseEvent event)
    {
        initMousePos = new double[2];
        initMousePos[0] = event.getX();
        initMousePos[1] = event.getY();

        if (selectedElemRec!=null) 
        {
            if( initMousePos[0] > selectedElemRec.getX() &&
                initMousePos[0] < selectedElemRec.getX() + selectedElemRec.getWidth()  &&
                initMousePos[1] > selectedElemRec.getY() && 
                initMousePos[1] < selectedElemRec.getY() + selectedElemRec.getHeight())
                    selectedElemTranslationActive = true;

            else 
            {
                selectedElemTranslationActive = false;
                {
                    panel.getChildren().removeAll(selectedElemRec);
                    selectedElemRec = null;
                }
            }
        }

        if (!cursorSelectionMode) 
        {
          App.setCursor(Cursor.CLOSED_HAND);    
          System.err.println("Initial mouseXPos: " + initMousePos[0]);
          System.err.println("Initial mouseYPos: " + initMousePos[1]);
        }   

        else
        { 
          selectionRec = new Rectangle();
          selectionRec.getStyleClass().add("selectionSquare");
          panel.getChildren().addAll(selectionRec);  
        }         
    }
    
    private void mouseDraggedHandler(MouseEvent event)
    {
        if (!cursorSelectionMode) //hand mode 
        {
            double deltaMousePos [] = new double[2];
            deltaMousePos[0] = event.getX() - initMousePos[0];
            deltaMousePos[1] = event.getY() - initMousePos[1];
            
            for (Map.Entry<Integer,GraphicElem> entry : circuitComponents.entrySet()) 
            {
                translateElem(entry.getValue(),deltaMousePos);
            }
        }

        else //selection mode 
        {
            if(selectedElemTranslationActive)
            {
                double[] transVec = new double[2];
                transVec[0] = event.getX() - initMousePos[0];
                transVec[1] = event.getY() - initMousePos[1];

                for (int id : selectedElemsId) 
                {
                    if (selectedElemTransValue.get(id) == null ) 
                    {
                        double[] init = new double[2];
                        init[0] = 0; 
                        init[1] = 0;
                        selectedElemTransValue.put(id, init);
                    }

                    translateElem(circuitComponents.get(id),transVec);
                }

                selectedElemRec.setTranslateX(transVec[0] + selectedElemRecTransValue[0]);
                selectedElemRec.setTranslateY(transVec[1] + selectedElemRecTransValue[1]);
            }

            else
            {
                double xMin;
                double yMin;

                if(event.getX() < initMousePos[0]) 
                {
                    xMin =  event.getX();
                    selectionRec.setX(event.getX());
                }

                else
                {
                    xMin = initMousePos[0];
                    selectionRec.setX(initMousePos[0]);
                }

                if(event.getY() < initMousePos[1]) 
                {
                    yMin =  event.getY();
                    selectionRec.setY(event.getY());
                }

                else
                {
                    yMin =  initMousePos[1];
                    selectionRec.setY(initMousePos[1]);
                }

                selectionBounds = new BoundingBox(xMin,yMin,Math.abs(initMousePos[0] - event.getX()),Math.abs(initMousePos[1] - event.getY()));

                selectionRec.setHeight(selectionBounds.getHeight());
                selectionRec.setWidth(selectionBounds.getWidth());  
            }
        }
    }

    private void mouseReleasedHandler(MouseEvent event)
    {
        selectedElemTranslationActive = false;

        if (!cursorSelectionMode) 
         {
             App.setCursor(Cursor.OPEN_HAND);

             transValue[0] += event.getX() - initMousePos[0];
             transValue[1] += event.getY() - initMousePos[1];
         }

         else //selection mode  
         {
            if(selectedElemRec!=null)
            {
                for (int id : selectedElemsId) 
                {
                    selectedElemTransValue.get(id)[0] += event.getX() - initMousePos[0];
                    selectedElemTransValue.get(id)[1] += event.getX() - initMousePos[0];
                }
            }
            
            else
            {
                panel.getChildren().removeAll(selectionRec);
                selectionRec = null;
                boolean firstLoop = true;
                
                if(selectionBounds!=null)
                {
                    selectedElemsId.clear();
                    double[] minPos = new double[2];
                    double[] maxPos = new double[2];
                    ArrayList<Shape> circuitComponentsShapes = new ArrayList<>();
                    
                    for (GraphicElem elem : circuitComponents.values()) 
                    {
                        circuitComponentsShapes.add(elem.getShape());
                    }
                    

                    for (Node shape : panel.getChildren()) 
                    {
                        if (selectionBounds.contains(shape.getBoundsInParent()) && circuitComponentsShapes.contains(shape)  ) 
                        {
                            if (firstLoop) 
                            {
                                minPos[0] = shape.getBoundsInParent().getMinX();
                                minPos[1] = shape.getBoundsInParent().getMinY();
                                maxPos[0] = shape.getBoundsInParent().getMaxX();
                                maxPos[1] = shape.getBoundsInParent().getMaxY();
                                firstLoop = false;
                            }
                            else
                            {
                                if (shape.getBoundsInParent().getMinX() < minPos[0])
                                    minPos[0] = shape.getBoundsInParent().getMinX();
                                
                                if (shape.getBoundsInParent().getMinY() < minPos[1])
                                    minPos[1] = shape.getBoundsInParent().getMinY();
                                if (shape.getBoundsInParent().getMaxX() > maxPos[0])
                                    maxPos[0] = shape.getBoundsInParent().getMaxX();
                                if (shape.getBoundsInParent().getMaxY() > maxPos[1])
                                    maxPos[1] = shape.getBoundsInParent().getMaxY();
                            }
                            for(Map.Entry<Integer,GraphicElem> entry : circuitComponents.entrySet()) 
                            {
                                if (shape == entry.getValue().getShape()) 
                                {
                                    selectedElemsId.add(entry.getKey());
                                    break;
                                }
                            }
                        }
                    }
                    setSelectedElemRec(minPos,maxPos); 
                }
            }

            selectionBounds = null;  
        }
    }

    private void translateElem(GraphicElem elem,double[] transVec)
    {
        double[] transVector  = new double[2];
        transVector[0] = transVec[0]+transValue[0];
        transVector[1] = transVec[1]+transValue[1];

        
        if (selectedElemTransValue.get(elem.getId())!=null) 
        {
             transVector[0] += selectedElemTransValue.get(elem.getId())[0];
             transVector[0] += selectedElemTransValue.get(elem.getId())[1];
        } 

        elem.getShape().setTranslateX(transVector[0]);
        elem.getShape().setTranslateY(transVector[1]);
        
        for (Circle inputCircle : elem.getInputs()) 
        {
            inputCircle.setTranslateX(transVector[0]);
            inputCircle.setTranslateY(transVector[1]);
        }
        
        //System.err.println("transVec[0]: " + transVector[0]  + "transVec[1] : " + transVector[1]);  
        System.err.println("elemMinX " + elem.getShape().getBoundsInParent().getMinX()  + "elemMinY"  + elem.getShape().getBoundsInParent().getMinY() );      
    } 

 
    private void zoom(double zoomFactor)
    {

    }

    public Pane getPanel() 
    {
        return panel;
    }

    public int getGridColNb()
    {
        return gridColNb;
    }

    public double getGridRatio()
    {
        return gridRatio;
    }

    public int getGridLineNb()
    {
        return gridLineNb;
    }
  
    public HashMap<Integer,GraphicElem> getHashMap()
    {
        return circuitComponents;
    }

}


