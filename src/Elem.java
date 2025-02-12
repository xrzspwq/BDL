package src;
import java.util.ArrayList;

public abstract class Elem {
    protected ArrayList<ArrayList<EnumBool>> In;  
    protected ArrayList<ArrayList<EnumBool>> Out;   
    protected String name;
    protected int NbBusIn;
    protected int NbBusOut;
    protected ArrayList<Integer> TailleBusIn;
    protected ArrayList<Integer> TailleBusOut;


public Elem( int NbFluxIn, int NbFluxOut, ArrayList<Integer> TailleBusIn, ArrayList<Integer> TailleBusOut) {
    this.In = new ArrayList<ArrayList<EnumBool>>();
    this.Out = new ArrayList<ArrayList<EnumBool>>();
    this.NbBusIn = NbFluxIn;
    this.NbBusOut = NbFluxOut;
    this.TailleBusIn = new ArrayList<Integer>(TailleBusIn);
    this.TailleBusOut = new ArrayList<Integer>(TailleBusOut);
}

public void setName(String name) {
    this.name = name;
}
public String getName() {return this.name;}
    
public ArrayList<ArrayList<EnumBool>> getIn(){return In;}

public ArrayList<ArrayList<EnumBool>> getOut(){return Out;}

public int getNbBusIn(){return NbBusIn;}

public int getNbBusOut(){return NbBusOut;}

public ArrayList<Integer> getTailleBusIn(){return TailleBusIn;}

public ArrayList<Integer> getTailleBusOut(){return TailleBusOut;}

public abstract ArrayList<ArrayList<EnumBool>> evaluate();

}



