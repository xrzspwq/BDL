package src;
import java.util.ArrayList;


public abstract class ElemLogique extends Elem{
    public ElemLogique(int NbBusIn, int NbBusOut, ArrayList<Integer> TailleFBusIn, ArrayList<Integer> TailleBusOut) {
        super(NbBusIn, NbBusOut, TailleFBusIn, TailleBusOut);
    }

    public ArrayList<ArrayList<EnumBool>> evaluate(){
        return Out;
    }

    public Wire CreateWire(){
        int index=0; // temporaire, probablement quand on aura fait le lien entre l'interface et le code
        return new Wire(NbBusOut, TailleBusOut, this, index);
    }
}