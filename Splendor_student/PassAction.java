
/**
 * Classe qui permet au joueur de passer son tour.
 *
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class PassAction implements Action{
    // joueur qui effectue l'action
    private Player p;
    public PassAction(Player p){
        this.p = p;
    }
    /**
     * méthode qui fait passer le tour au joueur
     * @param b plateau de jeu sur lequel joue le joueur
     */
    public void process(Board b){
        // affichage de l'action 
        Game.display.out.println(this); 
        Game.compteurPassAction++;
    }
        
    /**
     * @return String message pour informer les joueurs que le joueur passes son tour
     */
    public String toString(){
        return p.getName() + " passe son tour !";
    }
}
