package src;

import java.util.ArrayList;

public class Circuit extends ElemLogique {

    private String name;
    private ArrayList<Elem> elements;
    private ArrayList<Wire> wires;
    private int MAX = 100;

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

    public EnumBool simuler() {
        /*
         * initialisation des wires à "I" (vider les wires) // no need for us
         * for MAX times
         * -initialize b to true
         * -evaluate the output of each wire with (wire.evaluate)
         * the input of each element
         * -compare evaluated value with the entry of the wire
         * -if they're the same continu
         * -else update the wires with the new value and b to false
         * if b == true we have a fixed point
         * if we reach the end of MAX iterations without finding a fixed point
         * return ERR
         */
        boolean b;
        for (Wire wire : wires) {
            b = true;
            wire.evaluate();
        }
        return null;
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
