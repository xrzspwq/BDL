package window;

import src.ElemLogique;
import src.Elem;
import java.util.ArrayList;
import java.util.List;


import src.*;
import javafx.scene.shape.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import javafx.scene.shape.Circle;
import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;



public class GraphicElem
{
    public enum Orientation
    {
        EAST,
        SOUTH,
        WEST,
        NORTH,
    }
    
    private final static double inputRadius = 5;
    private final static double inputMargin = 10;
    private final static double inputSpacing = 4;
    static int idCount=0;
    private final int id;
    private final Elem elem;
    private List<Object> attributes;
    private Shape shape;
    private List<Circle> inputs;
    private Line inputsLine = null; 
    private Point2D pos; //elem position in whiteBoard
    private Orientation orientation;
    
    
    
    public GraphicElem(ElemLogique elem)
    {
        id = idCount++;
        this.elem = elem;
        attributes = GraphicElemInfo.getAttributes(elem);
        shape = GraphicElemInfo.getShape(elem);
        inputs = new ArrayList<>(); 
    }

    public GraphicElem(ElemLogique elem,List<Object> attributes)
    {
        this(elem);
        this.attributes = new ArrayList<>(attributes.size());
    }

    public int getId() {
        return id;
    }

    public List<Object> getAttributes() {
        return attributes;
    }

    /*
    public void setAttribute(Attribute<?> attri) 
    {
        for (Object elem : attributes) 
        {
            Attribute<?> attribute = (Attribute<?>)elem;
            
            if (attri.getName().equals(attribute.getName())) 
                attribute.setValue(attri.getValue());
        }
    }
    */

    public void setInOut()
    {
        setInOrOut(true);
        setInOrOut(false);
    }

    private void setInOrOut(boolean setIn)
    {
        int inOutNb = setIn ? elem.getInputNb() : elem.getOutputNb();
        
        Bounds elemBound = shape.getBoundsInParent();
        double inputOccupiedSpace = 2*inputMargin + inputSpacing*(inOutNb-1) + inputRadius*2*inOutNb;

        Point2D.Double inputsStartingPos =  setIn ? new Point2D.Double(elemBound.getMinX()-inputMargin,elemBound.getMinY()) 
                                                : new Point2D.Double(elemBound.getMaxX()+inputMargin,elemBound.getMinY()) ;

        Point2D.Double inputsEndPos =  setIn ? new Point2D.Double(inputsStartingPos.getX(),elemBound.getMinY()+elemBound.getHeight())
                                                : new Point2D.Double(inputsStartingPos.getX(),elemBound.getMinY()+elemBound.getHeight());

        double firstHalfEndingPos;
        double secondHalfEndingPos;

        if(elemBound.getHeight() < inputOccupiedSpace )
        {
            double missingSpace = inputOccupiedSpace-elemBound.getHeight();

            inputsLine = new Line(inputsStartingPos.getX(),elemBound.getMinY()-missingSpace/2,inputsEndPos.getX(),inputsEndPos.getY()+missingSpace/2);

            inputsStartingPos.setLocation(inputsStartingPos.getX(),elemBound.getMinY()-missingSpace/2);
            inputsEndPos.setLocation(inputsStartingPos.getX(), inputsEndPos.getY()+missingSpace/2);
        }

        double currStartingPos = inputsStartingPos.getY();

        for(int inputNb = 0; inputNb < inOutNb/2; ++inputNb)
        {
            inputs.add(new Circle(inputsStartingPos.getX()-inputRadius,currStartingPos+inputRadius,inputRadius));
            currStartingPos+=2*inputRadius+inputSpacing;
        }
        firstHalfEndingPos = currStartingPos-inputSpacing;

        currStartingPos = inputsEndPos.getY();
        for(int inputNb = 0; inputNb < inOutNb/2; ++inputNb)
        {
            inputs.add(new Circle(inputsStartingPos.getX()-inputRadius,currStartingPos-inputRadius,inputRadius));
            currStartingPos-=2*inputRadius+inputSpacing;
        }
        secondHalfEndingPos = currStartingPos-inputSpacing;

        if (inOutNb%2!=0)
        {
            double halfWayPos = inputsStartingPos.getY() + Math.abs((firstHalfEndingPos - secondHalfEndingPos) /2);
            inputs.add(new Circle(inputsStartingPos.getX()-inputRadius,halfWayPos,inputRadius));
        } 

        for(Circle circle : inputs)
        {
            circle.getStyleClass().add("inputCircle");
        }
    }

    public Shape getShape() 
    {
        return shape;
    }
    
    public Elem getElem() {
        return elem;
    }

    public Object clone()
    {
        return null;
    }

    public Point2D getPos() 
    {
        return pos;
    }

    public void setPos(Point2D position) 
    {
        pos = position;
    }

    public Orientation getOrientation() 
    {
        return orientation;
    }

    public void setOrientation(Orientation orient) 
    {
        orientation = orient;
    }

    public List<Circle> getInputs() 
    {
        return inputs;
    }

    public Line getInputsLine() 
    {
        return inputsLine;
    }

}
