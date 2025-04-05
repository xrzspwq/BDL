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
    private List<Circle> outputs;
    private Line inputsLine = null; 
    private Line outputsLine = null; 
    private Bounds bounds; //elem position in whiteBoard
    private Orientation orientation;
    private int sizeMultiplicator = 1;
    
    
    public GraphicElem(Elem elem)
    {
        id = idCount++;
        this.elem = elem;
        attributes = GraphicElemInfo.getAttributes(elem);
        shape = GraphicElemInfo.getShape(elem);
        inputs = new ArrayList<>(elem.getInputNb()); 
        outputs = new ArrayList<>(elem.getOutputNb());
    }
    
    public GraphicElem(ElemLogique elem)
    {
        id = idCount++;
        this.elem = elem;
        attributes = GraphicElemInfo.getAttributes(elem);
        shape = GraphicElemInfo.getShape(elem);
        inputs = new ArrayList<>(elem.getInputNb()); 
        outputs = new ArrayList<>(elem.getOutputNb());
    }

    public GraphicElem(ElemLogique elem,List<Object> attributes)
    {
        this(elem);
        this.attributes = new ArrayList<>(attributes.size());
    }

    /**
     * Returns the unique identifier of the GraphicElem instance.
     *
     * @return The id of the GraphicElem.
     */
    public int getId() {
        return id;
    }


    /**
     * Returns the list of attributes associated with the GraphicElem instance.
     *
     * @return A list of attributes. Each attribute is represented as an Object.
     *         The actual type of the attribute can be determined by checking the
     *         attribute's name and value.
     */
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
        List<Circle> inOrOutInputs = setIn ? inputs : outputs;
        Line inOrOutInputLine = setIn ? inputsLine : outputsLine;

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

            inOrOutInputLine = new Line(inputsStartingPos.getX(),elemBound.getMinY()-missingSpace/2,inputsEndPos.getX(),inputsEndPos.getY()+missingSpace/2);

            inputsStartingPos.setLocation(inputsStartingPos.getX(),elemBound.getMinY()-missingSpace/2);
            inputsEndPos.setLocation(inputsStartingPos.getX(), inputsEndPos.getY()+missingSpace/2);
        }

        double currStartingPos = inputsStartingPos.getY();

        for(int inputNb = 0; inputNb < inOutNb/2; ++inputNb)
        {
            inOrOutInputs.add(new Circle(inputsStartingPos.getX()-inputRadius,currStartingPos+inputRadius,inputRadius));
            currStartingPos+=2*inputRadius+inputSpacing;
        }
        firstHalfEndingPos = currStartingPos-inputSpacing;

        currStartingPos = inputsEndPos.getY();
        for(int inputNb = 0; inputNb < inOutNb/2; ++inputNb)
        {
            inOrOutInputs.add(new Circle(inputsStartingPos.getX()-inputRadius,currStartingPos-inputRadius,inputRadius));
            currStartingPos-=2*inputRadius+inputSpacing;
        }
        secondHalfEndingPos = currStartingPos-inputSpacing;

        if (inOutNb%2!=0)
        {
            double halfWayPos = inputsStartingPos.getY() + Math.abs((firstHalfEndingPos - secondHalfEndingPos) /2);
            inOrOutInputs.add(new Circle(inputsStartingPos.getX()-inputRadius,halfWayPos,inputRadius));
        } 

        for(Circle circle : inOrOutInputs)
        {
            circle.getStyleClass().add("IOCircles");
        }
    }

    /**
     * Returns the graphical representation of the element associated with this GraphicElem instance.
     *
     * @return The Shape object representing the graphical element.
     *         The Shape object can be of any type, such as Rectangle, Circle, Line, or a custom Shape.
     *         The actual type of the Shape object can be determined by checking the type of the Shape class.
     */
    public Shape getShape() 
    {
        return shape;
    }

    
    /**
     * Returns the logical element associated with this GraphicElem instance.
     *
     * @return The Elem object representing the logical element.
     *         The Elem object can be of any type, such as ElemLogique, ElemPhysique, or a custom Elem.
     *         The actual type of the Elem object can be determined by checking the type of the Elem class.
     *         This method provides a way to access the underlying logical element associated with this graphical representation.
     */
    public Elem getElem() {
        return elem;
    }


    public Object clone()
    {
        return null;
    }

    /**
     * Returns the bounding box of the graphical representation of the element.
     *
     * @return The Bounds object representing the bounding box of the graphical element.
     *         The Bounds object contains the minimum and maximum x and y coordinates of the element's shape.
     *         This information can be used to position and manipulate the graphical element within a graphical user interface (GUI).
     *         The actual type of the Bounds object can be determined by checking the type of the Bounds class.
     */
    public Bounds getBounds() 
    {
        return bounds;
    }


    /**
     * Sets the bounding box of the graphical representation of the element.
     *
     * @param bounds The Bounds object representing the bounding box of the graphical element.
     *               The Bounds object contains the minimum and maximum x and y coordinates of the element's shape.
     *               This information can be used to position and manipulate the graphical element within a graphical user interface (GUI).
     *               The actual type of the Bounds object can be determined by checking the type of the Bounds class.
     */
    public void setBounds(Bounds bounds) 
    {
        this.bounds = bounds;
    }


    /**
     * Returns the orientation of the graphical element.
     *
     * @return The orientation of the graphical element.
     *         The orientation can be one of the following: EAST, SOUTH, WEST, or NORTH.
     *         This information can be used to determine the direction in which the graphical element is facing within a graphical user interface (GUI).
     */
    public Orientation getOrientation() 
    {
        return orientation;
    }


    /**
     * Sets the orientation of the graphical element.
     *
     * @param orient The orientation to be set for the graphical element.
     *               The orientation can be one of the following: EAST, SOUTH, WEST, or NORTH.
     *               This information can be used to determine the direction in which the graphical element is facing within a graphical user interface (GUI).
     *               The provided orientation value will be assigned to the 'orientation' attribute of the GraphicElem instance.
     *
     * @return void
     *         This method does not return any value. It only sets the orientation attribute of the GraphicElem instance.
     */
    public void setOrientation(Orientation orient) 
    {
        orientation = orient;
    }


    /**
     * Returns the list of input circles associated with the graphical element.
     * Each input circle represents a connection point for incoming connections.
     *
     * @return A list of Circle objects representing the input circles.
     *         The Circle objects can be used to visualize and interact with the input connections.
     *         The actual type of the Circle object can be determined by checking the type of the Circle class.
     *         The list is empty if there are no input connections for the graphical element.
     */
    public List<Circle> getInputs() 
    {
        return inputs;
    }


    /**
     * Returns the line representing the input connections for the graphical element.
     * This line is used to visually connect the input circles to the graphical element.
     *
     * @return A Line object representing the input connections.
     *         The Line object connects the input circles to the graphical element.
     *         The actual type of the Line object can be determined by checking the type of the Line class.
     *         The Line object is null if there are no input connections for the graphical element.
     */
    public Line getInputsLine() 
    {
        return inputsLine;
    }


    /**
     * Returns the size multiplicator of the graphical element.
     * The size multiplicator is used to scale the graphical representation of the element.
     *
     * @return An integer representing the size multiplicator.
     *         The size multiplicator is a positive integer value that determines the scaling factor of the graphical element.
     *         A value of 1 indicates that the element is displayed at its original size.
     *         A value greater than 1 indicates that the element is scaled up by the specified factor.
     *         A value less than 1 indicates that the element is scaled down by the specified factor.
     */
    public int getsizeMultiplicator()
    {
        return sizeMultiplicator;
    }


    /**
     * Sets the size multiplicator of the graphical element.
     * The size multiplicator is used to scale the graphical representation of the element.
     *
     * @param sizeMultiplicator An integer representing the size multiplicator.
     *                          The size multiplicator is a positive integer value that determines the scaling factor of the graphical element.
     *                          A value of 1 indicates that the element is displayed at its original size.
     *                          A value greater than 1 indicates that the element is scaled up by the specified factor.
     *                          A value less than 1 indicates that the element is scaled down by the specified factor.
     *
     * @return void
     *         This method does not return any value. It only sets the size multiplicator attribute of the GraphicElem instance.
     */
    public void set(int sizeMultiplicator)
    {
        this.sizeMultiplicator = sizeMultiplicator;
    }

}
