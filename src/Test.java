
package src;

import java.util.ArrayList;

class Test {
 public static void main(String[] args) {
    ArrayList<Integer> TailleBus = new ArrayList<>();
    ArrayList<ArrayList<EnumBool>> out = new ArrayList<>();
    TailleBus.add(1);
    
    And andtest = new And(1, 1);
    Input intest1 = new Input(1, TailleBus);
    Input intest2 = new Input(1, TailleBus);

    intest1.toggle();
    intest2.toggle();
    Wire lien1 = new Wire(1, TailleBus, intest1, 1);
    Wire lien2 = new Wire(1, TailleBus, intest2, 1);
    
    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2); 
    out = andtest.evaluate();
    System.out.println("1-true\n\t---> "+out+"\n2-true\n\n ::::"+andtest.getIn()+"\n------------------");
    
    lien1.disconnect(lien1.getExit().get(0));
    lien2.disconnect(lien2.getExit().get(0));
    andtest.getIn().removeAll(andtest.In);
    andtest.getOut().removeAll(andtest.Out);
    intest1.toggle();
    intest2.toggle();
    intest1.evaluate();
    intest2.evaluate();
    System.out.println("+++ "+intest1.getOut()+" "+intest2.getOut());
    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2); 
    out = andtest.evaluate();
    
    System.out.println("1-false\n\t---> "+out+"\n2-false\n\n   ::::"+andtest.getIn());
    
    /*intest1.toggle();
    //intest2.toggle();
    out = andtest.evaluate();
    System.out.println("1-true\n\t---> "+out+"\n2-false\n\n   ::::"+andtest.getIn());

    intest1.toggle();
    intest2.toggle();
    out = andtest.evaluate();
    System.out.println("1-true\n\t---> "+out+"\n2-false\n\n    ::::"+andtest.getIn());
    */
}
    
}