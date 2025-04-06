package src;

// Exception if no path is found
public class AucunChemin extends Exception {
    public AucunChemin(){
        super("Aucun Chemin n'a été trouvée");
    }
}
