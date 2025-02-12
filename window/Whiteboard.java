package window;


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
import java.lang.Math;
//import java.nio.file.Path;

import javafx.util.Duration;

import javafx.animation.*;

//import javafx.animation.ParallelTransition;
//import javafx.animation.PathTransition;
//import javafx.scene.shape.HLineTo;
//import javafx.scene.shape.VLineTo;



public class Whiteboard
{
    private Pane panel;
    private HashMap<Integer,GraphicElem> circuitComponents;
    private GraphicElem elemToAdd;
    private Pair<Integer,Shape> shapeToAdd;
    public  boolean cursorOverHere = false;
    private boolean cursorSelectionMode = true;
    private boolean mouseTranslation = false;
    private boolean firstMove = false;
    private Double zoomMultiplicator = 1.025;
    private Double mousePos [];
    private Double lastMousePos [] = null;
    private BoundingBox selectionBounds;
    private Rectangle selectionRec;
    private ArrayList<Integer> selectedElemsId;
    private static final double animationSpeed = 50;
    private static final double animationSubdivision = 1;
    private ParallelTransition selectionAnimation = null;
    private ArrayList<Rectangle> selectionRecs = null;
    private double scaleFactor = 1; 

    public Whiteboard()
    {
        panel = new Pane();
        circuitComponents = new HashMap<>();
        selectedElemsId = new ArrayList<>();

        lastMousePos = new Double[] {0.0,0.0};

        panel.setOnDragOver(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(DragEvent event) 
            {  
                Node source = (Node) event.getGestureSource();
                if ( (source.getParent().equals(App.getGraphicElemPanel().getPanel())) && event.getDragboard().hasString()) 
                {
                    panel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
                    event.acceptTransferModes(TransferMode.COPY);

                    switch (event.getDragboard().getString()) 
                    {
                        case "circle":
                            elemToAdd = new GraphicElem(new AndGate());
                            break;

                        case "rectangle":
                            elemToAdd = new GraphicElem(new Circuit("project"));
                            break;    
                        
                        default:
                            elemToAdd = new GraphicElem(new OrGate());
                            break;
                    }
                }
                event.consume();
            }
        });

        panel.setOnDragDropped(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(DragEvent event) 
            {
                panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                elemToAdd.getShape().relocate(event.getX(),event.getY()); 
               // System.out.println("xPos : " + event.getX() + "\n yPos : " + event.getY());
                add(elemToAdd);        
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
                mouseTranslation = true;
                mousePos = new Double[2];
                mousePos[0] = event.getX();
                mousePos[1] = event.getY();

              if (!cursorSelectionMode) 
              {
                App.setCursor(Cursor.CLOSED_HAND);

                System.err.println("mouseXPos: " + mousePos[0]);
                System.err.println("mouseYPos: " + mousePos[1]);
              }

              else
              { 
                selectionRec = new Rectangle();
                selectionRec.getStyleClass().add("selectionSquare");
                panel.getChildren().addAll(selectionRec);  
              }

              event.consume();

            }
        });
     
        panel.setOnMouseDragged(new EventHandler<MouseEvent >() 
        {
            @Override
            public void handle(MouseEvent  event) 
            {
                if (!cursorSelectionMode) //hand mode 
                {
                    Double deltaMousePos [] = new Double[2];

                    if (firstMove && lastMousePos!=null) 
                    {
                        deltaMousePos[0] =  event.getX() - lastMousePos[0];
                        deltaMousePos[1] =  event.getY() - lastMousePos[1];
                        firstMove = false;
                        System.out.println("deltaMousePosX : " + deltaMousePos[0]);
                        System.out.println("deltaMousePosY : " + deltaMousePos[1]);


                        for (Node elem : panel.getChildren()) 
                        {
                            //elem.setTranslateX(elem.getTranslateX()+deltaMousePos[0]);
                            //elem.setTranslateY(elem.getTranslateY()+deltaMousePos[1]);
                            //
                            //System.out.println("FIRST) elem.getBoundsInParent().getMinX() : " + elem.getBoundsInParent().getMinX() + 
                            //"\nelem.getBoundsInParent().getMinY() : " + elem.getBoundsInParent().getMinY() + "\n\n");


                            System.out.println("FIRST elem.getTranslateX() : " + elem.getTranslateX() + "\nelem.getTranslateX() : " + elem.getTranslateX()  + "\n\n");

                        }
                    }

                    else
                    {
                        deltaMousePos[0] =  event.getX() - mousePos[0];
                        deltaMousePos[1] =  event.getY() - mousePos[1];

                        for (Node elem : panel.getChildren()) 
                        {
                            elem.setTranslateX(deltaMousePos[0]);
                            elem.setTranslateY(deltaMousePos[1]);

                            //elem.relocate(deltaMousePos[0],deltaMousePos[1]);

                           // System.out.println("elem.getBoundsInParent().getMinX() : " + elem.getBoundsInParent().getMinX() + 
                           // "\nelem.getBoundsInParent().getMinY() : " + elem.getBoundsInParent().getMinY() + "\n\n");
                           
                            System.out.println("elem.getTranslateX() : " + elem.getTranslateX() + "\nelem.getTranslateX() : " + elem.getTranslateX()  + "\n\n");

                        }
                    }
                    
                  
                }

                else //selection mode 
                {
                    double xMin;
                    double yMin;

                    if(event.getX() < mousePos[0]) 
                    {
                        xMin =  event.getX();
                        selectionRec.setX(event.getX());
                    }
                    
                    else
                    {
                        xMin = mousePos[0];
                        selectionRec.setX(mousePos[0]);
                    }

                    if(event.getY() < mousePos[1]) 
                    {
                        yMin =  event.getY();
                        selectionRec.setY(event.getY());
                    }

                    else
                    {
                        yMin =  mousePos[1];
                        selectionRec.setY(mousePos[1]);
                    }

                    selectionBounds = new BoundingBox(xMin,yMin,Math.abs(mousePos[0] - event.getX()),Math.abs(mousePos[1] - event.getY()));

                    selectionRec.setHeight(selectionBounds.getHeight());
                    selectionRec.setWidth(selectionBounds.getWidth());  
                }

                event.consume();
            }
        });

        panel.setOnMouseReleased(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                if (!cursorSelectionMode) 
                {
                    App.setCursor(Cursor.OPEN_HAND);
                    mouseTranslation = false;
                    firstMove = true;

                    lastMousePos = new Double[2];
                    lastMousePos[0] = event.getX();
                    lastMousePos[1] = event.getY();
                }

                else   
                {
                    mouseTranslation = false;
                    panel.getChildren().removeAll(selectionRec);
                    selectionRec = null;
                    
                    if(selectionAnimation!= null)
                        clearSelectionAnimation();

                    if(selectionBounds!=null)
                    {
                        boolean firstLoop = true;
                        for (Node shape : panel.getChildren()) 
                        {
                            if(firstLoop)
                            {
                                firstLoop = false;
                                selectedElemsId.clear();
                            }

                            if (selectionBounds.contains(shape.getBoundsInParent())) 
                            {
                                for(Map.Entry<Integer,GraphicElem> entry : circuitComponents.entrySet()) 
                                {
                                    if (shape == entry.getValue().getShape()) 
                                    {
                                        selectedElemsId.add(entry.getKey());
                                        //System.err.println(entry.getKey());
                                    }
                                }
                            }
                        }
                        setSelectionAnimation();   
                    }
                    selectionBounds = null;  
                }
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
               //changeCursorSelectionMode();
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
                    
                    if(event.getDeltaY() < 0)
                        scaleFactor = 1/(event.getDeltaY()/event.getMultiplierY()*zoomMultiplicator);
                    else
                        scaleFactor = event.getDeltaY()/event.getMultiplierY()*zoomMultiplicator;

                    elem.setScaleX(elem.getScaleX() * scaleFactor);
                    elem.setScaleY(elem.getScaleY() * scaleFactor);
              
                    System.err.println( "event.getDeltaY() : " + event.getDeltaY() + "event.getMultiplierY() : " + event.getMultiplierY());

                }
            }
        });

    }

    private void add(GraphicElem elem) 
    {
        circuitComponents.put(elem.getId(), elem);
        Shape shape = elem.getShape();

        shape.getStyleClass().add("whiteBoardElem");
        panel.getChildren().addAll(shape);  
        
        shape.setScaleX(shape.getScaleX() * scaleFactor);
        shape.setScaleY(shape.getScaleY() * scaleFactor);

        shape.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                //en attendant juste pour test   
                if (shape instanceof Rectangle)
                    App.setAttributesPanel(new GraphicElem(new Circuit("project")));

                if (shape instanceof Circle)
                    App.setAttributesPanel(new GraphicElem(new AndGate()));

                if (shape instanceof Polygon)
                    App.setAttributesPanel(new GraphicElem(new OrGate()));
                
                event.consume();
            }
        });
    }

    public void changeCursorSelectionMode() 
    {
        if (cursorSelectionMode) 
        {
            cursorSelectionMode = false;
            App.setCursor(Cursor.OPEN_HAND);
            for (Node elem : panel.getChildren()) 
            {
                elem.getStyleClass().clear();
                elem.getStyleClass().add("jsp");
            }
        } 
        
        else
        {
            cursorSelectionMode = true;
            App.setCursor(Cursor.DEFAULT);
            for (Node elem : panel.getChildren()) 
            {
                elem.getStyleClass().add("whiteBoardElem");
            }
        }
    }

    public Pane getPanel() {
        return panel;
    }

    private void clearSelectionAnimation()
    {
        for (Node elem : selectionRecs ) 
        {
            System.err.println("trouve");
            panel.getChildren().remove(elem);    
            elem = null;
        }
        selectionAnimation.stop();
        selectionAnimation = null;
        selectionRecs = null;
        selectedElemsId.clear();
    }

    private void setSelectionAnimation()
    {
        if (selectedElemsId.isEmpty())
            return; 

            selectionAnimation = new ParallelTransition();
            selectionRecs = new ArrayList<>();           

            for (int id : selectedElemsId) 
            {     
                Shape shape = circuitComponents.get(id).getShape();  

                for (int i=0; i< 4; ++i)//pas trop de sens
                {
                    selectionAnimation.getChildren().addAll(selectRecAnimation(shape,i));
                } 
            }  
            selectionAnimation.setCycleCount(Animation.INDEFINITE);
            selectionAnimation.play();
    }

    private SequentialTransition selectRecAnimation(Shape shape,int count)
    {
        SequentialTransition animation = new SequentialTransition();
        Double pathWidth = shape.getBoundsInLocal().getWidth();
        double pathHeight = shape.getBoundsInLocal().getHeight();
        double perimeter = 2*(pathWidth+pathHeight);
        double gap = perimeter/(10*2);
        double recWidth = perimeter/10 - gap;
        double recHeight = recWidth/3;
        pathWidth += recWidth;
        pathHeight += recWidth;
        double perimeterPortion = count*(recWidth+gap);
        BoundingBox path = new BoundingBox(shape.getBoundsInParent().getMinX()-recWidth,shape.getBoundsInParent().getMinY()-recWidth,pathWidth,pathHeight);
        Rectangle rec = new Rectangle();
        rec.getStyleClass().add("recSelection");
        panel.getChildren().addAll(rec); 
        selectionRecs.add(rec);

        double totalPerimeter = pathWidth;
        System.err.println(" perimeterPortion : " + perimeterPortion + "\t pathWidth : " + pathWidth + "\t pathHeight : " + pathHeight );

        if (/*perimeterPortion < totalPerimeter*/count==0) //topline
        {
            rec.setWidth(recWidth);
            rec.setHeight(recHeight);

            //double offset = perimeterPortion;
            double offset = 0;
            System.err.println("offset : " + offset);

            rec.relocate(path.getMinX()+offset,path.getMinY());
            Pair<ParallelTransition,Rectangle> currentPair = new Pair<>(null,rec);

            for(int i=0; i < 4; ++i)
            {
                if(i==0)
                    animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),i,offset));
                else
                    animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),i,0));
                currentPair = changingSideAnimation(path,currentPair.getValue(),i);
                animation.getChildren().add(currentPair.getKey());
            }
            animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),0,path.getWidth() - offset));

            return animation;
        } 

        totalPerimeter += path.getHeight();
        if (/*perimeterPortion < totalPerimeter*/count==1) //leftLine
        {
            rec.setWidth(recHeight);
            rec.setHeight(recWidth);

            //double offset = perimeterPortion-path.getWidth();
            double offset = 0;

            System.err.println("offset : " + offset +"\n");

            rec.relocate(path.getMaxX(),path.getMinY()+offset);
            Pair<ParallelTransition,Rectangle> currentPair = new Pair<>(null,rec);

            for(int i=1; i < 4; ++i)
            {
                if(i==1)
                    animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),i,offset));
                else
                    animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),i,0));
                    
                currentPair = changingSideAnimation(path,currentPair.getValue(),i);
                animation.getChildren().add(currentPair.getKey());
            }
            animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),0,0));
            currentPair = changingSideAnimation(path,currentPair.getValue(),0);
            animation.getChildren().add(currentPair.getKey());

            animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),1,path.getHeight() - offset));

            return animation;
        } 

        totalPerimeter += path.getWidth();
        if (/*perimeterPortion < totalPerimeter*/count==2) //bottomLine
        {
            rec.setWidth(recWidth);
            rec.setHeight(recHeight);

            //double offset = perimeterPortion-(path.getWidth()+path.getHeight());
            double offset = 0;

            System.err.println("offset prout2 : " + offset +"\n");
            System.err.println("perimeterPortion " + perimeterPortion + "totalPerimeter"  + totalPerimeter);

            rec.relocate(path.getMaxX()-offset,path.getMaxY());
            Pair<ParallelTransition,Rectangle> currentPair = new Pair<>(null,rec);
 
            for(int i=2; i < 4; ++i)
            {
                if(i==2)
                    animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),i,offset));
                else
                    animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),i,0));

                currentPair = changingSideAnimation(path,currentPair.getValue(),i);
                animation.getChildren().add(currentPair.getKey());
            }

            for(int i=0; i < 2; ++i )
            {
                animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),i,0));
                currentPair = changingSideAnimation(path,currentPair.getValue(),i);
                animation.getChildren().add(currentPair.getKey());
            }

            animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),2,path.getWidth() - offset));

            return animation;
        } 

        else //rightLine
        {
            rec.setWidth(recHeight);
            rec.setHeight(recWidth);
            
            //double offset = perimeterPortion-(2*path.getWidth()+path.getHeight());
            double offset = 0;

            System.err.println("offset prout : " + offset);

            rec.relocate(path.getMinX(),path.getMaxY()-offset);
            Pair<ParallelTransition,Rectangle> currentPair = new Pair<>(null,rec);
 
            animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),3,offset));
            currentPair = changingSideAnimation(path,currentPair.getValue(),3);
            animation.getChildren().add(currentPair.getKey());
            for(int i=0; i < 3; ++i)
            {
                animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),i,0));
                currentPair = changingSideAnimation(path,currentPair.getValue(),i);
                animation.getChildren().add(currentPair.getKey());
            }
            animation.getChildren().add(displacementAnimation(path,currentPair.getValue(),3,path.getHeight() - offset));

            return animation;
        } 
    }

    private TranslateTransition displacementAnimation(Bounds path,Rectangle rec,int side,double offset)
    {
        TranslateTransition trans = null;
        double dist = 0;
        
        switch (side) {
            case 0: //topLine
                dist = path.getMaxX() - rec.getBoundsInParent().getMinX();
                break;

            case 1: //RightLine
                dist = path.getMaxY() - rec.getBoundsInParent().getMinY();
                break;

             case 2: //bottomLine
                dist = rec.getBoundsInParent().getMaxX() - path.getMinX();
                break;

             case 3: //LeftLine
                dist = rec.getBoundsInParent().getMaxY() - path.getMinY();
                break;

            default:
                break;
        }

        dist -= offset;
        //System.err.println("dist : " + dist);
        if (dist < 0.0 )
            dist = 0;
            
        switch (side) {
            case 0: //topLine
                trans = new TranslateTransition(Duration.millis(dist*animationSpeed), rec);
                //trans.setByX(animationSpeed);
                trans.setFromX(0);
                trans.setToX(path.getWidth() - offset);
                break;

            case 1: //RightLine
                trans = new TranslateTransition(Duration.millis(dist*animationSpeed), rec);
                trans.setFromY(0);
                trans.setToY(path.getHeight() - offset);
                break;

             case 2: //bottomLine
                trans = new TranslateTransition(Duration.millis(dist*animationSpeed), rec);
                trans.setFromX(0);
                trans.setToX(-path.getWidth() + offset);
                break;

             case 3: //LeftLine
                trans = new TranslateTransition(Duration.millis(dist*animationSpeed), rec);
                trans.setFromY(0);
                trans.setToY(-path.getWidth() + offset);
                break;

            default:
                break;
        }

        return trans;
    } 

    private Pair<ParallelTransition,Rectangle>changingSideAnimation(Bounds path,Rectangle rec,int side)
    {
        ScaleTransition shrink = null; 
        ScaleTransition grow = null;
        TranslateTransition trans1 = null;
        TranslateTransition trans2 = null;
        Double duration;
        Rectangle rec2 = new Rectangle(rec.getHeight(),rec.getWidth());   
        rec2.getStyleClass().add("recSelection");
        panel.getChildren().addAll(rec2); 
        selectionRecs.add(rec2);

        double offset = 0;

        switch (side) 
        {
            case 0: //topLine
                offset = path.getMaxX() - rec.getBoundsInParent().getMaxX();
                break;

            case 1: //RightLine
                offset = path.getMaxY() - rec.getBoundsInParent().getMaxY();
                break;

             case 2: //bottomLine
                offset = rec.getBoundsInParent().getMinX() - path.getMinX();
                break;

             case 3: //LeftLine
                offset = rec.getBoundsInParent().getMinY() - path.getMinY();
                break;

            default:
                break;
        }

        if (offset > 0.0 )
            offset = 0;

        switch (side) 
        {
            case 0: //topLine
                duration = 20*rec.getWidth() - offset;

                shrink = new ScaleTransition(Duration.millis(duration), rec);
                shrink.setFromX(1);
                shrink.setToX(0.0);
                trans1 = new TranslateTransition(Duration.millis(duration),rec);
                trans1.setByX(rec.getWidth()/duration);

                rec2.relocate(path.getMaxX(),path.getMinY());
                rec2.setScaleY(0.0);

                grow = new ScaleTransition(Duration.millis(duration), rec2);
                grow.setFromY(0.0);
                grow.setToY(1.0);
                trans2 = new TranslateTransition(Duration.millis(duration),rec2);
                trans2.setByY(-rec.getWidth()/duration);
                break;
         
            case 1: //RightLine
                duration = 20*rec.getHeight() - offset;

                shrink = new ScaleTransition(Duration.millis(duration), rec);
                shrink.setFromY(1);
                shrink.setToY(0.0);
                trans1 = new TranslateTransition(Duration.millis(duration),rec);
                trans1.setByY(rec.getHeight()/duration);

                rec2.relocate(path.getMaxX(),path.getMaxY());
                rec2.setScaleX(0.0);

                grow = new ScaleTransition(Duration.millis(duration), rec2);
                grow.setFromX(0.0);
                grow.setToX(1.0);
                trans2 = new TranslateTransition(Duration.millis(duration),rec2);
                trans2.setByX(rec.getHeight()/duration);
                break;

             case 2: //bottomLine
                duration = 20*rec.getWidth() - offset;

                shrink = new ScaleTransition(Duration.millis(duration), rec);
                shrink.setFromX(1);
                shrink.setToX(0.0);
                trans1 = new TranslateTransition(Duration.millis(duration),rec);
                trans1.setByX(-rec.getWidth()/duration);

                rec2.relocate(path.getMinX(),path.getMaxY());
                rec2.setScaleY(0.0);

                grow = new ScaleTransition(Duration.millis(duration), rec2);
                grow.setFromY(0.0);
                grow.setToY(1.0);
                trans2 = new TranslateTransition(Duration.millis(duration),rec2);
                trans2.setByY(rec.getWidth()/duration);
                break;

             case 3: //LeftLine
                duration = 20*rec.getHeight() - offset;

                shrink = new ScaleTransition(Duration.millis(duration), rec);
                shrink.setFromY(1);
                shrink.setToY(0.0);
                trans1 = new TranslateTransition(Duration.millis(duration),rec);
                trans1.setByY(rec.getHeight()/duration);

                rec2.relocate(path.getMinX(),path.getMinY());
                rec2.setScaleX(0.0);

                grow = new ScaleTransition(Duration.millis(duration), rec2);
                grow.setFromX(0.0);
                grow.setToX(1.0);
                trans2 = new TranslateTransition(Duration.millis(duration),rec2);
                trans2.setByX(-rec.getHeight()/duration);
                break;

            default:
                break;
        }

        return new Pair(new ParallelTransition(shrink,grow,trans1,trans2),rec2);
    } 

}


