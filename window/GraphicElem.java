package window;

import java.util.List;
import javafx.scene.shape.*;

public class GraphicElem 
{
    static int idCount=0;
    private final int id;
    private List<Object> attributes;
    private Shape shape;
    private Elem elem;

    GraphicElem(Elem elem)
    {
        id = idCount++;
        this.elem = elem;
        attributes = GraphicElemInfo.getAttributes(elem);
        shape = GraphicElemInfo.getShape(elem);
    }

    public int getId() {
        return id;
    }

    public List<Object> getAttributes() {
        return attributes;
    }

    public Shape getShape() {
        return shape;
    }
    
    public Elem getElem() {
        return elem;
    }
}
