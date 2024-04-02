import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe qui permet d'actualiser le plateau de jeu ainsi que le jeu du 
 * joueur lorsque celui-ci arrive à acheter une carte de développement visible
 * On divise cette classe en deux parties selon le type de joueur
 * 
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class BuyCardAction implements Action
{
    // joueur qui effectue l'action
    private Player p;
    // carte achetée par le joueur
    private DevCard card;
    public BuyCardAction(Player p){
        this.p = p;
    }
    /**
     * effectue l'action de faire acheter le joueur une carte 
     * @param b plateau de jeu sur lequel le joueur joue
     */
    public void process(Board b){
        // si le joueur est humain
        if (p instanceof HumainPlayer){
            
            try {
                Game.display.out.print("Quelle est le level de la carte que voulez-vous achetez ?");
                Game.display.out.println(" Si vous voulez quitter l'action en cours taper 0 ");
                // récupération du level de la carte dont il veut acheter
                String strlevel = Game.scanner.nextLine().substring(1);
                int level = Integer.parseInt(strlevel);
                // si il veut quitter l'action en cours et 
                // qu'il a tapé 0 on déclenche une exception
                
                if (level == 0){
                    throw new IllegalArgumentException();
                }
                
                Game.display.out.println("level : " + level);
                Game.display.out.println("Quelle est le colonne de la carte que voulez-vous achetez ?");
                // récupération de la colonne ou se situe la carte que le joueur veut acheter
                String strcolonne = Game.scanner.nextLine().substring(1);
                int colonne = Integer.parseInt(strcolonne);
                Game.display.out.println("colonne : " + colonne);

                
                // on stocke la carte que l'utilisateur a choisi
                DevCard cardChoose = b.getCard(level,colonne);
                
                // si le joueur peut acheter la carte 
                if (p.canBuyCard(cardChoose)){
                    // on met à jour les ressource et les cartes visibles du board
                    b.updateCard(cardChoose);
                    // on stocke les ressources de cout de la carte acheté
                    Resources card_ressources = cardChoose.getCost();
                    // parcours de Resources de la carte achetée 
                    for (Map.Entry<Resource, Integer> mapelt : card_ressources.entrySet()) {
                        // si la resource parcourue a un cout > 0 et que
                        // ce cout moins la ressource dans l'inventaire du joueur est > 0 
                        // (on a pris en compte les bonus de carte
                        if (mapelt.getValue() > 0 && mapelt.getValue() - p.getResFromCards(mapelt.getKey()) > 0){
                            // on met à jour les ressources du player et du board
                            p.updateNbResource(mapelt.getKey(),-(mapelt.getValue() - p.getResFromCards(mapelt.getKey())));
                            b.updateNbResource(mapelt.getKey(), mapelt.getValue() - p.getResFromCards(mapelt.getKey()));
                        }
                    }
                    // on mets à jour les points du joueur
                    p.updatePoints(cardChoose);
                    // ajout de la carte achetée aux cartes achetées du joueur
                    p.addPurchasedCard(cardChoose);
                    card = cardChoose;
                    // affichage de l'action
                    Game.display.out.println(this);

                }else{
                    // sinon on déclenche une excetion
                    throw new IllegalArgumentException();                    
                }
            } catch (IllegalArgumentException |  InputMismatchException  | NullPointerException e) {
                // affichage de l'action et on relance la procédure
                Game.display.out.println(this);
                Action newaction = p.chooseAction(b);
                newaction.process(b);
                            }
        }
        // si le joueur est un robot
        else if (p instanceof DumbRobotPlayer){
            // on appelle la fonction qui renvoit la liste des 
            // DevCard triée dans l'ordre décroissant des points
            ArrayList<DevCard> sortedList= getSortedDevCardPoints(b);
            // parcours de la liste des DevCard triée 
            for (int i = 0; i < sortedList.size() && card == null; i++) {

                DevCard cardChoose = sortedList.get(i);
                // si le joueur peut acheter la carte parcourue
                if (p.canBuyCard(cardChoose)){
                    // mise à jour des ressources du board
                    b.updateCard(cardChoose);
                    
                    Resources card_ressources = cardChoose.getCost();
                    for (Map.Entry<Resource, Integer> mapelt : card_ressources.entrySet()) {
                        // si la resource parcourue a un cout > 0 et que
                        // ce cout moins la ressource dans l'inventaire du joueur est > 0 
                        // (on a pris en compte les bonus de carte)
                        if (mapelt.getValue() > 0 && mapelt.getValue() - p.getResFromCards(mapelt.getKey()) > 0){
                            // on met à jour les ressources du player et du board
                            p.updateNbResource(mapelt.getKey(),-(mapelt.getValue() - p.getResFromCards(mapelt.getKey())));
                            b.updateNbResource(mapelt.getKey(), mapelt.getValue() - p.getResFromCards(mapelt.getKey()));
                        }
                    }
                    // mise à jour des points du robot
                    p.updatePoints(cardChoose);
                    // ajout de la carte achetée à celle qui l'a déjà achetée
                    p.addPurchasedCard(cardChoose);
                    card = cardChoose;
                    // affichage de l'action
                    Game.display.out.println(this);
                }
            }
            // si le robot n'a pas réussi a acheté de carte on passe à la prochaine action
            if (card == null){
                Game.display.out.println(this);
                Action newaction = new PickSameTokensAction(p);
                if (newaction != null){
                    //sinon on passe à la prochaine action dans l'ordre indiqué
                    newaction.process(b); 
                }
            }
        }
        }   
    /**
     * @param b plateau de jeu sur lequel joue le joueur 
     * @return sortedDevCardList liste triée de DevCard dans 
     * l'ordre décroissant en fonction des points
     */
    public ArrayList<DevCard> getSortedDevCardPoints(Board b){
        // stockage/copie des cartes visibles
        DevCard [][] visibleCards = b.getVisibleCards();
        // création de la ArrayList qui va etre retournée
        ArrayList<DevCard> sortedDevCardList = new ArrayList<DevCard>();
        
        // 12 tour -> nombre de cartes visibles
        for (int c = 0; c < 12; c++){
            // réinitialistation de la carte max à chaque tour
            DevCard max = null;

            // parcours des cartes visibles sur le plateau
            for (int i = 3; i > 0; i--) { 
                for (int j = 1; j <= 4; j++){
                    DevCard cardChoose = b.getCard(i, j);
                    // si la carte parcourue n'est pas nulle 
                    // et n'est pas déjà dans sortDevCardList
                    if (cardChoose != null && !sortedDevCardList.contains(cardChoose)){
                        // recherche d'un max de façon classique
                        if (max == null){
                            max = cardChoose;
                        }else if(cardChoose.getPoints() > max.getPoints()) {
                            max = cardChoose;
                        }
                    }
                }
            }
            // ajout à chaque tour du max trouvé 
            sortedDevCardList.add(max);
        }
        
        return sortedDevCardList;
    }
    /**
     * @return String message de réussite ou d'échec de l'achat
     */
    public String toString(){
        if (card!= null){
            return p.getName() + " vient d'acheter la carte de développement : " + card.toString() + "!";
        }else{
            return p.getName() + " n'a pas réussi à acheter la carte de développement.";
        }
    }
}
