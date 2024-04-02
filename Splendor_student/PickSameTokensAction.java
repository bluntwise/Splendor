import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.InputMismatchException;

/**
 * Classe qui permet d'actualiser le plateau de jeu ainsi que le jeu 
 * du joueur quand celui-ci prend deux fois le même jeton
 * On divise cette classe en deux parties selon le type de joueur
 *
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class PickSameTokensAction implements Action 
{   
    // joueur qui fait l'action
    private Player p;
    // resource choisi par me joueur
    private Resource r;
    
    public PickSameTokensAction(Player p){
       this.p = p;
    }
    
    /**
     * lance l'action qui demande à l'utilisateur quelle ressource il veut prendre 
     * en double et pour le robot cette ressource est choisi de manière bete
     * @para b plateau de jeu
     */
    public void process(Board b){
        // si le joueur est un humain
        if ( p instanceof HumainPlayer){
            // on récupère la ressource que l'utilisateur veut prendre en deux
            Game.display.out.println("Quelle ressource voulez-vous prendre en double ?");
            String action_choice = Game.scanner.nextLine().substring(1);
            
            // on stocke la ressource que l'utilisateur choisi 
            String ressource = null;
            if (action_choice.equals("E")){
                ressource = "EMERALD";
            }else if(action_choice.equals("O")){
                ressource = "ONYX";
            }else if(action_choice.equals("R")){
                ressource = "RUBY";  
            }else if(action_choice.equals("D")){
                ressource = "DIAMOND";
            }else if (action_choice.equals("S")){
                ressource = "SAPPHIRE";
            }
            
            // try pour gèrer les erreurs de saisie
            try {
                // pour convertir le string rentré en une constante d'énumération
                
                Resource enteredRessource = Resource.valueOf(ressource);
                // on regarde si c'est possible d'en prendre 2
                
                if (b.canGiveSameTokens(enteredRessource)){
                    
                    //si c'est possible on actualise les ressources
                    b.updateNbResource(enteredRessource, -2);
                    p.updateNbResource(enteredRessource, 2);
                    r = enteredRessource;
                    // affichage de l'action
                    Game.display.out.println(this);

                } else {
                    // sinon on lance une Exception pour que le catch gère l'errreur
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException |  InputMismatchException  | NullPointerException e) {
                // affichage de l'action                
                Game.display.out.println(this);
                // on relance une procédure de choix d'action
                Action newaction = p.chooseAction(b);
                // si l'utilisateur a entré une action valide alors on lance l'action choisi
                if (newaction != null){
                    newaction.process(b);
                }

            }
        }
        // si le joueur est un robot
        else if (p instanceof DumbRobotPlayer){
            // liste des jetons présents sur le plateau
            ArrayList<Resource> dispo = b.getAvailableResources(); 
            // liste de ceux qu'on peut prendre deux fois
            ArrayList<Resource> onPeutEnPrendreDeux = new ArrayList<Resource>();
            // création d'un random
            Random random = new Random();
            // on parcourt les ressources disponibles sur la plateau
            for(Resource jeton : dispo){
                //on vérifie ceux qu'on peut prendre deux fois 
                if (b.canGiveSameTokens(jeton)){
                    // ajout de cette ressource valide 
                    onPeutEnPrendreDeux.add(jeton);  
                }
            }
            //si il y en a, on en choisit un au hasard
            if (onPeutEnPrendreDeux.size() != 0){ 
                // on génère un index aléatoire 
                int randomInt = random.nextInt(onPeutEnPrendreDeux.size());
                Resource prise = onPeutEnPrendreDeux.get(randomInt);
                // on mets à jour les jetons du plateau et du joueur
                b.updateNbResource(prise, -2);
                p.updateNbResource(prise, 2);
                // on stocke la ressource choisi et valide
                r = prise;
                // affichage de l'action
                Game.display.out.println(this);
            } 
            // sinon on passe à la prochaine action dans l'ordre indiqué
            else {
                Game.display.out.println(this);
                Action newaction = new PickDiffTokensAction(p);
                if (newaction != null){
                    newaction.process(b); 
                }
            }
        }
    }
    /**
     * @return String message qui informe que le joueur 
     * vient de prendre deux jetons de resource r ou non
     */
    public String toString(){
        // affichage en console si oui ou non l'action a réussi
        if (r != null) {
            return p.getName() + " vient de prendre deux jetons de " + r + "!";
        }else{
            return p.getName() + " n'a pas réussi à prendre deux jetons !";            
        }
    }
}