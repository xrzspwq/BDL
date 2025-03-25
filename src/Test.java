
package src;

import java.util.ArrayList;


class Test {
 public static void main(String[] args) {
    ArrayList<ArrayList<EnumBool>> out = new ArrayList<>();
    
    Object[][] table = new String[5][]; 
    table[0] = new String[] {"Input","Output"}; 

    Or ortest = new Or();
    And andtest = new And(1,1);
    Xor xortest = new Xor(1, 1);
    Not nottest = new Not(1, 1);

    Input intest1 = new Input();
    Input intest2 = new Input();

   
    Wire lien1 = new Wire(intest1, 1, null);
    Wire lien2 = new Wire(intest2, 1, null);
    
    
/*  AND TEST */

    ////////////////
    /* TRUE TRUE */
    intest1.toggle();
    intest2.toggle();

    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2); 
    out = andtest.evaluate();
    
    table[1] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};
    
    andtest.getIn().removeAll(andtest.In);
    andtest.getOut().removeAll(andtest.Out);

    ////////////////
    /*False False */

    intest1.toggle();
    intest2.toggle();


    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2); 

    out = andtest.evaluate();
    
    table[2] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};
  

    andtest.getIn().removeAll(andtest.In);
    andtest.getOut().removeAll(andtest.Out);

    ////////////////
    /*False True */
    
    intest2.toggle();
    
    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2); 

    out = andtest.evaluate();
    
    table[3] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};
  
    andtest.getIn().removeAll(andtest.In);
    andtest.getOut().removeAll(andtest.Out);

    ////////////////
    /*True False */

    intest1.toggle();
    intest2.toggle();

    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2); 

    out = andtest.evaluate();
    
    table[4] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};

    andtest.getIn().removeAll(andtest.In);
    andtest.getOut().removeAll(andtest.Out);
   
    System.out.println("----------------------------------");
    System.out.println("\t\tAND TEST");
    for(Object[] row : table){
        System.out.format("%15s%15s%n", row);
    }
    System.out.println("----------------------------------");


///////////////////////////////////// 
/* RESET INPUT TO VALUE TRUE TRUE */
    intest1.toggle();
///////////////////////////////////


/*    OR TEST  */

    ///////////////
    /*True True */

    intest1.toggle();
    intest2.toggle();

    lien1.connect(ortest, 1);
    lien2.connect(ortest, 2); 
    out = ortest.evaluate();
    
    table[1] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};
    
    ortest.getIn().removeAll(ortest.In);
    ortest.getOut().removeAll(ortest.Out);

    /////////////////
    /*False False */

    intest1.toggle();
    intest2.toggle();

    lien1.connect(ortest, 1);
    lien2.connect(ortest, 2); 

    out = ortest.evaluate();
    
    table[2] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};
  
    ortest.getIn().removeAll(ortest.In);
    ortest.getOut().removeAll(ortest.Out);

    ////////////////
    /*False True */
    
    intest2.toggle();

    lien1.connect(ortest, 1);
    lien2.connect(ortest, 2); 

    out = ortest.evaluate();
    
    table[3] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};
  
    ortest.getIn().removeAll(ortest.In);
    ortest.getOut().removeAll(ortest.Out);
    
    ////////////////
    /*True False */

    intest1.toggle();
    intest2.toggle();

    lien1.connect(ortest, 1);
    lien2.connect(ortest, 2);

    out = ortest.evaluate();
    
    table[4] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};

    ortest.getIn().removeAll(ortest.In);
    ortest.getOut().removeAll(ortest.Out);

    System.out.println("\t\tOR TEST");
    for(Object[] row : table){
        System.out.format("%15s%15s%n", row);
    }
    System.out.println("----------------------------------");


///////////////////////////////////// 
/* RESET INPUT TO VALUE TRUE TRUE */
intest1.toggle();
///////////////////////////////////


/*    XOR TEST  */

    ///////////////
    /*True True */

    intest1.toggle();
    intest2.toggle();

    lien1.connect(xortest, 1);
    lien2.connect(xortest, 2);

    out = xortest.evaluate();
    
    table[1] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};
    
    xortest.getIn().removeAll(xortest.In);
    xortest.getOut().removeAll(xortest.Out);

    /////////////////
    /*False False */

    intest1.toggle();
    intest2.toggle();

    lien1.connect(xortest, 1);
    lien2.connect(xortest, 2); 

    out = xortest.evaluate();
    
    table[2] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};

    xortest.getIn().removeAll(xortest.In);
    xortest.getOut().removeAll(xortest.Out);

    ////////////////
    /*False True */
    
    intest2.toggle();
    
    lien1.connect(ortest, 1);
    lien2.connect(ortest, 2); 

    out = ortest.evaluate();
    
    table[3] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};
  
    ortest.getIn().removeAll(ortest.In);
    ortest.getOut().removeAll(ortest.Out);
    
    ////////////////
    /*True False */

    intest1.toggle();
    intest2.toggle();

    lien1.connect(ortest, 1);
    lien2.connect(ortest, 2);

    out = ortest.evaluate();
    
    table[4] = new String[] {intest1.getOut()+" "+intest2.getOut(), ""+out};

    ortest.getIn().removeAll(ortest.In);
    ortest.getOut().removeAll(ortest.Out);

    System.out.println("\t\tXOR TEST");
    for(Object[] row : table){
        System.out.format("%15s%15s%n", row);
    }
    System.out.println("----------------------------------");



/*  NOT TEST  */

    ///////////////
    /*True*/

    intest1.toggle();

    lien1.connect(nottest, 1);
    
    out = nottest.evaluate();
    
    table[1] = new String[] {intest1.getOut()+"", ""+out};
    
    nottest.getIn().removeAll(nottest.In);
    nottest.getOut().removeAll(nottest.Out);

    /////////////////
    /*False*/

    intest1.toggle();

    lien1.connect(nottest, 1);
    
    out = nottest.evaluate();
    
    table[2] = new String[] {intest1.getOut()+"", ""+out};

    nottest.getIn().removeAll(nottest.In);
    nottest.getOut().removeAll(nottest.Out);

    table[3][0]= null;

    System.out.println("\t\tNOT TEST");
    for(Object[] row : table){
        if (row[0] == null) {
            break;
        }
        System.out.format("%15s%15s%n", row);
    }
    System.out.println("----------------------------------");

/*  little circuit*/


    System.out.println("\tLITTLE CIRCUIT\n");
    intest2.toggle();
    intest1.toggle();

    lien1.connect(andtest, 1);
    lien2.connect(andtest, 2);
    out=andtest.evaluate();
    
    System.out.println("input\t\tinput\n"+intest1.getOut() +"\t"+intest2.getOut());
    System.out.println("   |\t\t   |\n    ---------------\n\t |");
    System.out.println("\tAnd\n\t"+out+"");
    System.out.println("\t |");

    lien1.changeEntry(andtest, 1);
    lien1.connect(nottest, 1);
    out = nottest.evaluate();
    intest1.toggle();
    System.out.println("\tNot\t\tinput\n\t"+out+"\t"+intest1.getOut()+"");
    System.out.println("\t   |\t\t   |\n\t    ---------------\n\t\t |");
    
    
    lien1.changeEntry(nottest, 1);
    lien2.changeEntry(intest1, 1);

    lien1.connect(xortest,1);
    lien2.connect(xortest, 2);
    out = xortest.evaluate();

    System.out.println("\t\tXor\n\t\t"+out);
    System.out.println("\n----------------------------------");

}
    
}