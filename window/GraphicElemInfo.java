package window;

import src.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.shape.*;


public class GraphicElemInfo 
{
    GraphicElemInfo(){}

    static private final List<Integer> dataBitsOptions = Arrays.asList(1,2,3,4,5,6,16,32);
    static private final List<Integer> NbInputsOptions = Arrays.asList(1,2,3,4,5,6,16);
    static private final List<String>  LabelFontOptions = Arrays.asList("Times","Impact");
    static private final List<GraphicElem> components = Collections.unmodifiableList(new ArrayList<GraphicElem>(){
        {   add(new GraphicElem(new And()));
            add(new GraphicElem(new Or()));
            add(new GraphicElem(new Circuit("")));
        }});

    /**
     * This function returns an unmodifiable list of predefined graphic elements.
     * 
     * @return An unmodifiable list of {@link GraphicElem} objects.
     * 
     * The list contains the following predefined graphic elements:
     * <ul>
     *     <li>An instance of {@link GraphicElem} with an {@link And} gate.</li>
     *     <li>An instance of {@link GraphicElem} with an {@link Or} gate.</li>
     *     <li>An instance of {@link GraphicElem} with a {@link Circuit}.</li>
     * </ul>
     */
    static public final List<GraphicElem> getComponents()
    {
        return components;
    }


    /**
     * This function retrieves the graphical representation of a given element.
     * 
     * @param elem The element for which the graphical representation is required.
     * @return The graphical representation of the element.
     * 
     * The function supports three types of elements:
     * <ul>
     *     <li>An "And Gate" element is represented by a {@link Circle} with a radius of 20.</li>
     *     <li>An "Or Gate" element is represented by a {@link Polygon} with three points: (40.0,0.0), (0.0,0.0), and (20.0,20.0).</li>
     *     <li>A "Circuit" element is represented by a {@link Rectangle} with dimensions 30x30.</li>
     * </ul>
     * 
     * Note: The graphical representation for "And Gate" and "Or Gate" elements is currently hardcoded.
     * For "And Gate", an SVG file is attempted to be loaded, but if the file is not found, a default {@link Circle} is used.
     * 
     * @see Elem
     * @see Circle
     * @see Polygon
     * @see Rectangle
     */
    static public final Shape getShape(Elem elem)
    {
        final Shape res;

        switch (elem.getName()) {

            case "And Gate":
                res = new Circle(20); 
                //SVGPath svg = new SVGPath();
                //String svgPath = null;
                //try {svgPath = new ReaderSVGPath("window/rsc/img/elems/AND.svg").getPath();}  
                //catch (Exception e) {System.err.println("ERROR : AND svg file not found");}
                //svg.setContent(svgPath);
                //res = svg;
            return res;

            case "Or Gate" : 
                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(new Double[]{
                    40.0,0.0, 
                    0.0,0.0,
                    20.0,20.0, });
                res = polygon;
            return res;

            default: //circuit
                res = new Rectangle(30,30);
            return res;
        }
    }


    /**
     * This function retrieves the attributes of a given element.
     * 
     * @param elem The element for which the attributes are required.
     * @return A list of {@link Object} representing the attributes of the element.
     * 
     * The function supports two types of elements: "And Gate" and "Or Gate".
     * For "And Gate" and "Or Gate", the function returns a list of {@link Attribute} objects.
     * Each {@link Attribute} object has a name and a value.
     * The available attributes for "And Gate" and "Or Gate" are:
     * <ul>
     *     <li>Data bits: An {@link Integer} representing the number of data bits. The default value is the first element in {@link #dataBitsOptions}.</li>
     *     <li>Number inputs: An {@link Integer} representing the number of inputs. The default value is the second element in {@link #NbInputsOptions}.</li>
     *     <li>Label size: An {@link Integer} representing the size of the label. The default value is 10.</li>
     *     <li>Label font: A {@link String} representing the font of the label. The default value is the first element in {@link #LabelFontOptions}.</li>
     * </ul>
     * For other elements, the function returns a list of {@link Attribute} objects with only the "Label size" and "Label font" attributes.
     * 
     * @see Elem
     * @see Attribute
     * @see #dataBitsOptions
     * @see #NbInputsOptions
     * @see #LabelFontOptions
     */
    static public final List<Object> getAttributes(Elem elem)
    {
        final List<Object> res;
        switch (elem.getName()) {
            case "And Gate":
            case "Or Gate" :
            res = Collections.unmodifiableList(new ArrayList<Object>(){
                {   add(new Attribute<Integer>("Data bits",dataBitsOptions.get(0)));
                    add(new Attribute<Integer>("Number inputs",NbInputsOptions.get(1)));
                    add(new Attribute<Integer>("Label size",10));
                    add(new Attribute<String>("Label font",LabelFontOptions.get(0)));
                }});
                return res;

            default: //circuit
                res = Collections.unmodifiableList(new ArrayList<Object>(){
                {  
                    add(new Attribute<Integer>("Label size",10));
                    add(new Attribute<String>("Label font",LabelFontOptions.get(0)));
                }}); 
                return res;
        }
    }

        
    /**
     * This function retrieves the available options for a given attribute.
     * 
     * @param attribute The attribute for which the available options are required.
     * @return A list of {@link Object} representing the available options for the attribute.
     * 
     * The function supports three types of attributes: "Data bits", "Number inputs", and "Label font".
     * For "Data bits", the function returns a list of {@link Integer} representing the available options.
     * For "Number inputs", the function returns a list of {@link Integer} representing the available options.
     * For "Label font", the function returns a list of {@link String} representing the available options.
     * 
     * If the given attribute is not recognized, the function returns a list containing two {@link String} objects: "jsp" and "je sais pas".
     * 
     * @see Attribute
     * @see #dataBitsOptions
     * @see #NbInputsOptions
     * @see #LabelFontOptions
     */
    static public final List<?> getOptions(Attribute<?> attribute)
    {
        switch (attribute.getName()) 
        {
            case "Data bits":
                return dataBitsOptions;
            case "Number inputs":
                return NbInputsOptions;
            case "Label font":
                return LabelFontOptions;

            default:
                break;
        }
        return Arrays.asList("jsp","je sais pas");
    }


}
