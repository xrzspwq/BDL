package src;

// Exception pour le cas où aucun chemin n'est trouvé
public class AucunChemin extends Exception {
    AucunChemin(){
        super("Aucun Chemin n'a été trouvée");
    }
}
