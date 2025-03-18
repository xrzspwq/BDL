package src;
import java.util.ArrayList;


public abstract class ElemLogique extends Elem{
    public ElemLogique() {
        super();
    }

    public ArrayList<ArrayList<EnumBool>> evaluate(){
        return Out;
    }
}