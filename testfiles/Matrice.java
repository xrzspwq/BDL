package testfiles;

import java.util.ArrayList;

public class Matrice {

    public Matrice(ArrayList<ArrayList<Integer>> data, int lineNB, int colNB) {
        if (data.size()!=0) {
        
            if (lineNB > data.size() || lineNB == 0) {
                lineNB = data.size();
                
            }
            
    
            for (int i = 0; i < lineNB; i++) {
                if (data.get(i).size() < colNB || colNB ==0) {
                    System.out.println(data.get(i) + "");
                } else {
                    System.out.print("[");
                    for (int j = 0; j < colNB; j++) {
                        if(j== colNB-1){
                            System.out.print(data.get(i).get(j)+"]"); 
                        }
                        else{
                            System.out.print(data.get(i).get(j)+", ");
                        }
                    }
                    System.out.println();
    
                }
            } 

        }
        else{
            System.out.println("vide");
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
}
