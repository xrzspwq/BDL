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
            add(new GraphicElem(new Circuit()));
        }});

    static public final List<GraphicElem> getComponents()
    {
        return components;
    }

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
