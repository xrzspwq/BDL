
package src;

import java.util.ArrayList;

class Test {
 public static void main(String[] args) {
    ArrayList<ArrayList<EnumBool>> out = new ArrayList<>();
    
    //And andtest = new And(1, 1);
    Or andtest = new Or();

    Input intest1 = new Input();
    Input intest2 = new Input();

   
    Wire lien1 = new Wire(intest1, 1, null);
    Wire lien2 = new Wire(intest2, 1, null);
    
    
    ////////////////////////////////////////
    /// Test avec deux entrée True
    intest1.toggle();
    intest2.toggle();

    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2); 
    out = andtest.evaluate();

    System.out.println("1-true\n\t---> "+out+"\n2-true\n\n ::::"+andtest.getIn()+"\n------------------");
    
    //lien1.disconnect(lien1.getExit().get(0));
    //lien2.disconnect(lien2.getExit().get(0));

    andtest.getIn().removeAll(andtest.In);
    andtest.getOut().removeAll(andtest.Out);


    ////////////////////////////////////////
    /// Test avec deux entrée False
    intest1.toggle();
    intest2.toggle();

    intest1.evaluate();
    intest2.evaluate();

    System.out.println("+++ "+intest1.getOut()+" "+intest2.getOut());

    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2); 

    out = andtest.evaluate();
    
    System.out.println("1-false\n\t---> "+out+"\n2-false\n\n   ::::"+andtest.getIn());
  

    andtest.getIn().removeAll(andtest.In);
    andtest.getOut().removeAll(andtest.Out);


    ////////////////////////////////////////
    /// Test avec une entrée True une False
    //intest1.toggle();
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