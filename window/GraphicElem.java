package window;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.*;

public class GraphicElem
{
    static int idCount=0;
    private final int id;
    private Elem elem;
    private List<Object> attributes;
    private Shape shape;

    
    public GraphicElem(Elem elem)
    {
        id = idCount++;
        this.elem = elem;
        attributes = GraphicElemInfo.getAttributes(elem);
        shape = GraphicElemInfo.getShape(elem);
    }

    public GraphicElem(Elem elem,List<Object> attributes)
    {
        id = idCount++;
        this.elem = elem;
        shape = GraphicElemInfo.getShape(elem);

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

    public Shape getShape() {
        return shape;
    }
    
    public Elem getElem() {
        return elem;
    }

    public Object clone()
    {
        return null;
    }

}
