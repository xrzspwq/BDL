package src;

import java.util.ArrayList;

public class Circuit extends ElemLogique {

    private String name;
    private ArrayList<Elem> elements;
    private ArrayList<Wire> wires;

    public Circuit(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
        this.wires = new ArrayList<>();
    }

    public Circuit() {
        this.name = "circuit";
        this.elements = new ArrayList<>();
        this.wires = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addElement(Elem elem) {
        elements.add(elem);
    }

    public void addWire(Wire wire) {
        wires.add(wire);
    }

    public EnumBool simuler(ArrayList<Wire> wires) {
        ArrayList<ArrayList<EnumBool>> result;
        for (Wire wire : wires) {
            result = wire.evaluate();
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).contains(EnumBool.ERR) || result.get(i).contains(EnumBool.NOTHING))
                    return EnumBool.ERR;
            }
        }
        return EnumBool.ERR;
    }

    public EnumBool sup(EnumBool a, EnumBool b) {
        if (a == EnumBool.ERR || b == EnumBool.ERR)
            return EnumBool.ERR;
        if (a == EnumBool.NOTHING)
            return b;
        if (b == EnumBool.NOTHING)
            return a;
        if (a == EnumBool.FALSE && b == EnumBool.FALSE)
            return EnumBool.FALSE;
        if (a == EnumBool.FALSE || b == EnumBool.FALSE)
            return EnumBool.ERR;
        return EnumBool.TRUE;
    }
}
