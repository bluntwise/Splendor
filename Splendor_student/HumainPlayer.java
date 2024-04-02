import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Classe qui permet de créer un joueur humain et les actions qui seront 
 * appelées à chaque tour du joueur.
 * Chaque humain a un identifiant et un nom.
 * 
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class HumainPlayer extends Player

{
    public HumainPlayer(int id, String name){
        super(id,name);
    }
    
    /**
     * L'utilisateur choisit ses action en tapant des chiffres une exception est lancée si 
     * il saisit tout sauf des chiffres entre 1 et 4 compris
     * @param b Le plateau de jeu sur lequel la partie où le joueur se déroule
     * @return action L'action que l'utilisateur a choisi
     */
    
    public Action chooseAction(Board b){
        Game.display.out.println("Quelle action voulez-vous faire " + this.getName() + " ?");
               
        
        try {
            
            // on récupère l'entrée de l'utilisateur et on le convertit en un nombre
            String entry = Game.scanner.nextLine().substring(1);
            int action_choice = Integer.parseInt(entry);
            Action action;
            
            // choix de l'action en fonction de l'entrée
            if (action_choice == 1){
                action = new PickSameTokensAction(this);
            }else if(action_choice == 2){
                action = new PickDiffTokensAction(this);
            }else if(action_choice == 3){
                action = new BuyCardAction(this);
            }else if (action_choice == 4){
                action = new PassAction(this);
            }else{
                // si aucune des action n'a été choisi alors on déclenche une exception
                throw new IllegalArgumentException();
            }
            // on return l'action choisi
            return action;          
        }
        // erreur de saisie
        catch (IllegalArgumentException |  InputMismatchException e) {
            // on demande à l'utilisateur de saisir à nouveau une action
            Game.display.out.println("Erreur de saisie veuillez recommencer !");
            Action newaction = chooseAction(b);
            if (newaction != null){
                  newaction.process(b);
            }
        }return null;
    }
    /**
     * L'utilisateur choisit les jetons à défausser tant qu'il a plus de 10 jetons
     * @return jetons ArrayList<Resource> qui représente les jetons défaussées par l'utilisateur
     */
    public ArrayList<Resource> chooseDiscardingTokens(){
        // nombre de token que l'utilisateur a 
        int totalToken = 0;
        ArrayList<Resource> r = getAvailableResources();
        // liste vide qu'on rempli avec ce qu'on veut défausser
        ArrayList<Resource> jetons = new ArrayList(); 
        for (Resource resource : r){
            totalToken += getNbResource(resource);
        }
        // compteur pour pouvoir afficher un message différent si c'est le premier message ou non
        int i = 0;
        // si on a eu une erreur de saisie
        boolean erreur = false;
        // tant que l'utilisateur a plus de 10 jetons on relance la procédure
        while(totalToken>10){
            // si c'est la première fois qu'on demande ou non
            if (i == 0){
                Game.display.out.print("Choisissez un jeton à défausser : ");
            }else if (i != 0){
                Game.display.out.print("Choisissez un autre jeton à défausser : ");
            }
            
            // on récupère le choix de l'utilisateur e
            String jeton = Game.scanner.nextLine().substring(1);
            Game.display.out.println(jeton);
            String ressource_choice = null;
            // on fait des tests pour savoir quelle ressource il a choisi
            if (jeton.equals("E")){
                ressource_choice = "EMERALD";
            }else if(jeton.equals("O")){
                ressource_choice = "ONYX";
            }else if(jeton.equals("R")){
                ressource_choice = "RUBY";  
            }else if(jeton.equals("D")){
                ressource_choice = "DIAMOND";
            }else if (jeton.equals("S")){
                ressource_choice = "SAPPHIRE";
            }   

            
            try {
                // Convertir la chaîne en une constante d'énumération
                Resource enteredJeton = Resource.valueOf(ressource_choice);
                if (r.contains(enteredJeton)){ //si il l'a dans ses jetons
                    //on actualise les jetons du joueur
                    updateNbResource(enteredJeton,-1); 
                    //on l'ajoute à la liste
                    jetons.add(enteredJeton); 
                    //pour sortir de la boucle
                    totalToken -=1; 
                    i++;
                    erreur = false;
                    Game.display.out.println(this.getName() + " a défaussé un jeton de " + enteredJeton);
                } else {
                    // sinon on demande à l'utilisateur de recommencer sa saisie 
                    // si la resource n'est pas disponible
                    Game.display.out.println("Ressource non disponible. Veuilez recommencer !");
                    erreur = true;
                }
            } catch (IllegalArgumentException |  InputMismatchException  | NullPointerException e) {
                // mauvaise saisie on demande de recommencer
                Game.display.out.println("Mauvaise saisie. Veuilez recommencer !");
                erreur = true;
            }
        }// liste des jetons défaussés
        return jetons;
    }
}

