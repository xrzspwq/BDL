package testfiles;

import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import src.Constant;
import src.Not;
import src.Pair;
import src.Wire;
import src.Elem;
import src.EnumBool;

public class Wiretest {
    
    public Wiretest(){
        Constant intest1 = new Constant();
        Constant intest2 = new Constant();
        Not nottest = new Not(1);
        Wire lien1 = new Wire(intest1, 1, null);
        ArrayList<ArrayList<Integer>> M = new ArrayList<>();
        M.add(new ArrayList<>());
        M.add(new ArrayList<>());
        M.add(new ArrayList<>());
        M.add(new ArrayList<>());
        M.get(0).addAll(Arrays.asList(1,0,0,0,0,0,0));
        M.get(1).addAll(Arrays.asList(0,0,0,0,0,0,0));
        M.get(2).addAll(Arrays.asList(0,0,0,0,0,0,0));
        M.get(3).addAll(Arrays.asList(0,0,0,0,0,1,0));
        Stack<Point2D> points = new Stack<>();

        Object[][] table = new String[15][]; 
        Integer i = 0;
        
        
        table[0] = new String[] {"OUT","EXPECTED"};

        table[1] = new String[] {"Test","getEntry\n"};
        table[2] = new String[] {lien1.getEntry()+""," <"+intest1+",1>"};
        
        table[3] = new String[] {"Test","connectExit\n"};
        table[4] = new String[] {lien1.connectExit(nottest, 1) + "","True"};
        
        table[5] = new String[] {"Test","getExit\n"};
        table[6] = new String[] {lien1.getExit()+""," [[<"+nottest+",1>]]"};
        
        lien1.swapEntry(intest2, 1);
        table[7] = new String[] {"Test","change entry\n"};
        table[8] = new String[] {"Result: "+lien1.getEntry()+""," <"+intest2+",1>\n\n\told entry:<"+intest1+",1>\n\n\t  Exit:"+lien1.getExit()};
        
        table[9] = new String[] {"Test getState","default state\n"};
        table[10] = new String[] {lien1.getState()+"","true"};
        
        Pair<Elem,Integer> tmp = new Pair<Elem,Integer>(lien1.getExit().getElem1(), lien1.getExit().getElem2());
        lien1.disconnectExit();
        table[11] = new String[] {"Test","disconnect\n"};
        table[12] = new String[] {"Result: "+lien1.getExit() +"","old entry: "+tmp};
    

        points = lien1.CheminLPC(M);
        table[13] = new String[] {"Test","cheminLPC\n"};
        table[14] = new String[] {points+"",""};


        System.out.println("\t\tWIRE TEST");
       
        for(Object[] row : table){
            if (i == 13) {
                break;
            }
            System.out.format("%15s%15s%n", row);
            
            
            if (i%2 == 0) {
                System.out.println("\n::::::::::::::::::::::::::::::::::\n");
            }
            i++;
        }
        System.out.println("----------------------------------");
        


    }
}
