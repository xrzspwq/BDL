package src;

import java.util.ArrayList;
import java.util.Iterator;

public class Wire extends Elem{    
    private Pairs<ElemLogique,Integer> entry;
    private ArrayList<Pairs<ElemLogique,Integer>> exit;
    private ArrayList<ArrayList<EnumBool>> output;

    //Passe en private car il n'y a pas d'exit à la création
    private Wire(int NbBusIn, int NbBusOut, ArrayList<Integer> TailleBusIn, ArrayList<Integer> TailleBusOut, ElemLogique elem1, int indexEntry) {
        super(NbBusIn, NbBusOut, TailleBusIn, TailleBusOut);
    }

    public Wire(int NbBusIn, ArrayList<Integer> TailleBusIn, ElemLogique elem1, int indexEntry){
        super(NbBusIn, 0, TailleBusIn, new ArrayList<>());
        this.entry = new Pairs<>(elem1, indexEntry);
        this.exit=new ArrayList<>();
    }
    public Pairs<ElemLogique, Integer> getEntry(){return entry;}

    public ArrayList<Pairs<ElemLogique, Integer>> getExit(){return exit;}

    public void changeEntry(ElemLogique e1, int indexIn){
    /**
     * Change the entry of the wire to a new logical element.
     * The current entry is added to the exit list before updating.
     *
     * @param e1 the new logical element to set as the entry.
     */
        exit.add(entry);
        this.entry=new Pairs<>(e1,indexIn);
        output=e1.evaluate();
    }

    

    public boolean connect(ElemLogique elem, Integer int1){
        exit.add(new Pairs<>(elem,int1));
        if(output==null){ 
            output=entry.getElem1().evaluate();
        }
        
        Iterator<ArrayList<EnumBool>> i = output.iterator();
        ArrayList<EnumBool> a= i.next();
        return elem.In.add(a);
    }

    public boolean disconnect(Pairs<ElemLogique,Integer> p){
        
        return exit.remove(p);
    }   

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate(){
        if(output!=null){return output;}
        output=entry.getElem1().evaluate();
        return output;
    }

}

