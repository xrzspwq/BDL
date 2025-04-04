package logic;

// Exception if no path is found
public class AucunChemin extends Exception {
    AucunChemin(){
        super("Aucun Chemin n'a été trouvée");
    }
}
