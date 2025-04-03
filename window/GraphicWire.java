package window;

import src.Elem;
import src.Wire;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.scene.shape.Path;
import javafx.scene.shape.*;

public class GraphicWire extends GraphicElem
{
    Point2D start;

    public GraphicWire(Elem entry,int indexEntry,Point2D start)
    {
        super(new Wire(entry, indexEntry, start));
        this.start = start;
    }

    public GraphicWire(Wire wire)
    {
        super(wire);
        if (wire.getPosStart()!=null)
            this.start = wire.getPosStart();
    }

    public void setShape(Point2D endPos,ArrayList<ArrayList<Integer>>grid)
    {   
        Wire wire = (Wire) getElem();
        wire.setPosEnd(endPos);
        ArrayList<Point2D> points = new ArrayList<>(); 
        Path path = new Path();

        System.out.println("Point.size() : " + points.size());

        try {
            points = new ArrayList(wire.CheminLPC(grid));
        } catch (Exception e) { System.err.println("ERREUR"); return;}

        System.out.println("Point.size() : " + points.size());

        MoveTo moveTo = new MoveTo(start.getX(),start.getY());
        path.getElements().add(moveTo);


        for (int pointIndex = 1; pointIndex < points.size(); ++pointIndex) 
        {
            Point2D point = points.get(pointIndex);
            LineTo lineTo = new LineTo(point.getX(),point.getX());
            MoveTo moveTo2 = new MoveTo(point.getX(),point.getX());
            
            path.getElements().add(lineTo);
            path.getElements().add(moveTo2);

            System.out.println("["+point.getX()+"] [" + point.getY() +"]\n");
        }

        super.setShape(path);
    }

    public Wire getWire()
    {
        Wire wire = (Wire) getElem();
        
        return wire;
    }
}