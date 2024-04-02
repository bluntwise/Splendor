import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Classe qui permet d'actualiser le tableau de jeu ainsi que le jeu d'un 
 * joueur quand celui-ci prend des jetons de ressources différentes.
 * On divise cette classe en deux parties selon le type de joueur
 *
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class PickDiffTokensAction implements Action
{
    // joueur qui fait l'action
    private Player p;
    // nombre de ressource choisi par le joueur
    private int nbResource;
    public PickDiffTokensAction(Player p){
       this.p = p;
    }
    
    /**
     * lance l'action qui demande à l'utilisateur quelles ressources il veut prendre, 3 ressources
     * maximum peuvent etres prises
     * @para b plateau de jeu
     */
    public void process(Board b){
        // si le joueur est humain
        if (p instanceof HumainPlayer){
            // liste des ressources de l'utilisateur 
            ArrayList<Resource> resources_list = new ArrayList<Resource>(); 
            
            try{
                Game.display.out.println("Choisissez une ou des ressource(s) disponibles : ");
                String ressource = Game.scanner.nextLine().substring(1);
                // on récupère les ressources sous forme de tableau avec split
                String[] ressources_list = ressource.split(" "); 
                
                
                String ressource_choice = null;
                for (String r:ressources_list){
                    // on convertit l'input ressource en une constante de Resource 
                    if (r.equals("E")){
                        ressource_choice = "EMERALD";
                    }else if(r.equals("O")){
                        ressource_choice = "ONYX";
                    }else if(r.equals("R")){
                        ressource_choice = "RUBY";  
                    }else if(r.equals("D")){
                        ressource_choice = "DIAMOND";
                    }else if (r.equals("S")){
                        ressource_choice = "SAPPHIRE";
                    }   
                    resources_list.add(Resource.valueOf(ressource_choice));
                }// on a converti le tableau de String en ArrayList
                
                // si l'utilisateur rentre plus de 3 ressources ou qu'il en met plus 
                // que ce qu'il y a de disponible il y a une erreur
                if (ressources_list.length > 3 || b.getAvailableResources().size() < ressources_list.length || !b.canGivDiffTokens(resources_list)){
                    throw new IllegalArgumentException();
                }else{
                    // si c'est possible alors on mets à jour les ressoures du plateau
                    // et du joueur pour chaque Resource r
                    for (Resource r:resources_list){
                        b.updateNbResource(r,-1);
                        p.updateNbResource(r,1);
                        nbResource++;
                    }
                    // affichage de l'action
                    Game.display.out.println(this);
                }
            }catch (IllegalArgumentException |  InputMismatchException  | NullPointerException e){
                // affichage de l'action qui n'a pas marché
                Game.display.out.print(this);
                // on relance la procédure de choix pour l'utilisateur
                Action newaction = p.chooseAction(b);
                if (newaction != null){
                    newaction.process(b); 
                }
            }
        }// si le joueur est un robot
        else if (p instanceof DumbRobotPlayer){
            // 
            ArrayList<Resource> ressources_list = b.getAvailableResources();
            
            if (ressources_list.size() >= 3){
                for (int i = 0; i < 3; i++){
                    Resource r = ressources_list.get(i);
                    b.updateNbResource(r,-1);
                    p.updateNbResource(r,1);
                    nbResource++;
                }
                Game.display.out.println(this);

            }else if (ressources_list.size() > 0){
                for (Resource r: ressources_list){
                    b.updateNbResource(r,-1);
                    p.updateNbResource(r,1);
                    nbResource++;
                }
                Game.display.out.println(this);

            }else{
                Game.display.out.println(this);
                Action newaction = new PassAction(p);
                if (newaction != null){
                    newaction.process(b); //sinon on passe à la prochaine action dans l'ordre indiqué
                }
            }
        }
    }
    /**
     * @return String message qui renvoie sir le joueur vient de 
     * prendre différents jetons ou non
     */
    public String toString(){
        if (nbResource > 0){
            return p.getName() + " vient de prendre "+nbResource+" jetons de ressource différente !";

        }else{
            return p.getName() + " n'a pas réussi à prendre des jetons de ressource différente !";

        }
    }
}
