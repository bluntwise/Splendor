import java.util.ArrayList;

/**
 * Classe qui sert à actualiser le plateau de jeu ainsi que le jeu du 
 * joueur quand celui-ci défausse un jeton
 * On divise cette classe en deux parties selon le type de joueur
 * 
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class DiscardTokensAction
 implements Action
{
    // joueur qui fait l'action
    private Player p;
    // liste des jetons défaussés du joueur
    private ArrayList<Resource> discard_tokens;
    
    public DiscardTokensAction(Player p){
        this.p = p;
    }
    /**
     * permet de lancer l'action sur le joueur p
     * @param : b le plateau de jeu
     */
    public void process(Board b){
        discard_tokens = p.chooseDiscardingTokens();
        Game.display.out.println(this);
    }
    
    /**
     * renvoie un message si le joueur a défaussé ou non des jetons
     * @return defausse message qui informe l'utilisateur de ce qu'il a défaussé
     */
    public String toString(){
        if(discard_tokens.size()>0){
            String defausse = p.getName() + " a défaussé";
            for(Resource jeton : discard_tokens){
                defausse += " " + jeton;
            }return defausse;
        }return "";
        
    }
}
