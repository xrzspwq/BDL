package window;

import src.Elem;
import src.Wire;
import java.awt.geom.Point2D;

public class GraphicWire extends GraphicElem
{
    public GraphicWire(Elem entry,int indexEntry,Point2D start)
    {
        super(new Wire(entry, indexEntry, start));
    }
}