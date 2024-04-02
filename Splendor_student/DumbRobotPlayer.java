import java.util.ArrayList;
import java.util.Random;

/**
 * Classe qui permet de créer un joueur robot et les actions qui seront 
 * appelées à chaque tour du joueur.
 * Chaque robot a un identifiant et un nom.
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class DumbRobotPlayer extends Player

{
    public DumbRobotPlayer(int id, String name){
        super(id,name);
    }
    
    /**
     * lance une action BuyCardAction avec laquelle le robot va essayer d'acheter la
     * carte la plus haute possible
     * @param b plateau de jeu
     * @return BuyCardAction(this) Action pour acheter une carte
     */
    public Action chooseAction(Board b){
        return new BuyCardAction(this);
    };
    
    /**
     *  choisit les cartes de façon bete pour le robot 
     *  @return jetons ArrayList<Resource> des jetons défaussés
     */
    public ArrayList<Resource> chooseDiscardingTokens(){
        // nombre de jetons que le robot a dans son inventaire
        int totalToken = 0;
        // liste des ressources du joueur
        ArrayList<Resource> r = getAvailableResources();
        ArrayList<Resource> jetons = new ArrayList(); //liste vide qu'on rempli avec ce qu'on veut défausser
        
        // on calcule le nombre de jetons qu'a le joueur
        for (Resource resource : r){
            totalToken += getNbResource(resource);
        }
        
        // tant que le joueur a plus de 10 jetons on continue la procédure
        while(totalToken> 10){
            Random random = new Random();
            int randomInt = random.nextInt(r.size());
            Resource resource = r.get(randomInt);
            if (getNbResource(resource) > 0){
                updateNbResource(resource,-1); //on actualise les jetons du robot
                jetons.add(resource); //on l'ajoute à la liste
                totalToken -=1; //pour sortir de la boucle
            }else{
                r.remove(resource);
            }
        }
        // si la liste des jetons n'est pas vide 
        if(jetons.size()>0){
            String defausse = this.getName() + " a défaussé";
            for(Resource jeton : jetons){
                defausse += " " + jeton;
            }
            // affichage des jetons défaussés
            Game.display.out.println(defausse);
        }
        return jetons;
    }
}
