package src;

import java.util.ArrayList;
import java.util.Collection;

public class Circuit extends ElemLogique {

    private String name;
    private ArrayList<Elem> elements;
    private ArrayList<Wire> wires;

    /**
     * Constructs a new Circuit object with the given name.
     *
     * @param name The name of the circuit. It should be a non-null and non-empty
     *             string. 
     *
     */
    public Circuit(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
        this.wires = new ArrayList<>();
    }


     /**
     * Constructs a new Circuit object with the given name and the set of wires eaqual to c.
     *
     * @param name The name of the circuit. It should be a non-null and non-empty
     *             string.
     * @param c A collection use for set this.Wires 
     *
     */
    public Circuit(String name, Collection<? extends Elem> c) {
        this.name = name;
        this.elements = new ArrayList<>();
        this.wires = new ArrayList<>();
        elements.addAll(c);
    }


    /**
     * Constructs a new Circuit object with a default name "circuit".
     *
     */
    public Circuit() {
        this.name = "circuit";
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
     * Give the list of Wires.
     * 
     * @return The this.Wires
     */
    public ArrayList<Wire> getWires() {
        return wires;
    }

    /**
     * Give the list of elements.
     * 
     * @return The this.elements
     */
    public ArrayList<Elem> getElems() {
        return elements;
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

    public boolean SetElement(Collection<? extends Elem> c) {
        if(!elements.isEmpty()){
            elements.removeAll(elements);
        }
        
        return elements.addAll(c);
    }

    /**
     * Adds a new wire to the circuit.
     * 
     * @param wire The wire to be added to the circuit. This should be an instance
     *             of the Wire class.
     */
    public boolean addWire(Wire wire) {
        return wires.add(wire);
    }

    /**
     * Add a collection of wire to the circuit.
     * 
     * @param c The collection will be add to this.wire
     *
     * @return Return result of this.wires.addAll(c);
     */
    public boolean addAllwire(Collection<? extends Wire> c){
        return this.wires.addAll(c);
    }

    /**
     * Set a collection of wire to the circuit.
     * 
     * @param c The collection will give all new value to this.wires
     * 
     * @return Return result of this.wires.addAll(c);
     */
    public boolean SetWires(Collection<? extends Wire> c){
        if(!wires.isEmpty()){
            wires.removeAll(elements);
        }
        
        return this.wires.addAll(c);
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
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        ArrayList<ArrayList<EnumBool>> fin = new ArrayList<>();
        ArrayList<ArrayList<EnumBool>> result = new ArrayList<>();
        fin.add(new ArrayList<>());
        for (Wire wire : wires) {
            try{   
            result = wire.evaluate();
            }
            catch(Exception e){
                fin.get(0).add(EnumBool.ERR);
                return fin;
            }
            this.elements.add(wire.getEntry().getElem1());
            System.out.println(wire.getEntry()+": "+result+" :"+wire.getExit());
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).contains(EnumBool.ERR) || result.get(i).contains(EnumBool.NOTHING)){
                    fin.get(0).add(EnumBool.ERR);
                    return fin;
                }
            }
        }
        fin.get(0).add(EnumBool.TRUE);
        return fin;
    }

   
}
