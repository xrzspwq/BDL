package src;

import java.util.ArrayList;

public class Circuit extends ElemLogique {

    private String name;
    private ArrayList<Elem> elements;
    private ArrayList<Wire> wires;

    public Circuit() {
        this.elements = new ArrayList<>();
        this.wires = new ArrayList<>();
    }

    public Circuit(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
        this.wires = new ArrayList<>();
    }

    /**
     * Retrieves the name of the circuit.
     *
     * @return The name of the circuit as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a new element to the circuit.
     * 
     * @param elem The element to be added to the circuit. This should be an
     *             instance of the Elem class or its subclasses.
     */
    public void addElement(Elem elem) {
        elements.add(elem);
    }

    /**
     * Adds a new wire to the circuit.
     * 
     * @param wire The wire to be added to the circuit. This should be an instance
     *             of the Wire class.
     */
    public void addWire(Wire wire) {
        wires.add(wire);
    }

    /**
     * Simulates the circuit by evaluating the state of each wire.
     *
     * This method iterates through each wire in the circuit, evaluates its state
     * using the evaluate() method,
     * and checks if the result contains any ERR or NOTHING values. If such values
     * are found, the method returns ERR. Otherwise, it returns ERR as a default
     * value.
     * 
     * @param wires The list of wires to be evaluated.
     * @return The result of the simulation, which is either ERR or NOTHING
     */
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
