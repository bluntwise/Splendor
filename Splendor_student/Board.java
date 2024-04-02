import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Map;

/**
 * Classe qui permet de modéliser un plateau de jeu et de l'afficher avec 
 * les différentes ressources et cartes présentes dessus.
 * Chaque plateau a une pile de cartes, des cartes visibles, des jetons 
 * des différentes ressources et un nombre de joueurs.
 *
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class Board implements Displayable {

    /* --- Stringers --- */
    // ArrayList de toutes les cartes modélisées par la classe DevCard
    private ArrayList<DevCard> cartes;
    //ArrayList de stack<DevCard> -> piles de cartes modélisées par DevCard
    private ArrayList<Stack<DevCard>> stackCards;
    // tableau des cartes visibles sur le plateau
    private DevCard[][] visibleCards;
    // ressources sur la plateau
    private Resources ressources;
    // nombre de joueurs
    private int nbj;
    
    public Board(int nbjoueurs) throws java.io.IOException {
        
        cartes = new ArrayList<DevCard>();
        stackCards = new ArrayList<Stack<DevCard>>();
        // tableau de 3 lignes sur 4 colonnes
        visibleCards = new DevCard[3][4];
        ressources = new Resources();
        nbj = nbjoueurs;
        // initialisation des ressources
        init_resources();
        visible();
        // affichage du plateau
        display();
        
        
        
    }
    
    // initialisation des ressources au départ du jeu
    public void init_resources(){
        int c;
        // en fonction du nombre de joueurs 
        // le nombre de ressources différentes
        if (nbj == 2){
            c = 4;
        }else if(nbj == 3){
            c = 5;
        }else{
            c = 7;
        }
        for (Resource elt : Resource.values()){
            // chaque re;ssource -> nombre de joueurs + 2
            ressources.setNbResource(elt, c);
        }
    }
    private String[] deckToStringArray(int tier){
        /** EXAMPLE
         * ┌────────┐
         * │        │╲ 
         * │ reste: │ │
         * │   16   │ │
         * │ cartes │ │
         * │ tier 3 │ │
         * │        │ │
         * └────────┘ │
         *  ╲________╲│
         */
        int nbCards = stackCards.get(tier - 1).size(); //- AREMPLEACER par le nombre de cartes présentes
        String[] deckStr = {"\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510  ",
                            "\u2502        \u2502\u2572 ",
                            "\u2502 reste: \u2502 \u2502",
                            "\u2502   "+String.format("%02d", nbCards)+"   \u2502 \u2502",
                            "\u2502 carte"+(nbCards>1 ? "s" : " ")+" \u2502 \u2502",
                                "\u2502 tier "+tier+" \u2502 \u2502",
                            "\u2502        \u2502 \u2502",
                            "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518 \u2502",
                            " \u2572________\u2572\u2502"};
        return deckStr;
    }

    private String[] resourcesToStringArray(){
        /** EXAMPLE
         * Resources disponibles : 4♥R 4♣E 4♠S 4♦D 4●O
         */
        String[] resStr = {"Resources disponibles : "};
        
        // A decommenter
        for(Resource res : Resource.values()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            resStr[0] += ressources.getNbResource(res)+res.toSymbol()+" ";
        }
                 
        resStr[0] += "        ";
        return resStr;
    }

    private String[] boardToStringArray(){
        String[] res = Display.emptyStringArray(0, 0);
        

        //Deck display
        String[] deckDisplay = Display.emptyStringArray(0, 0);
        for(int i=3;i>0;i--){
            deckDisplay = Display.concatStringArray(deckDisplay, deckToStringArray(i), true);
        }

        //Card display
        String[] cardDisplay = Display.emptyStringArray(0, 0);
        for(int i = 0; i < 3; i++){ //-- parcourir les différents niveaux de carte (i)
            String[] tierCardsDisplay = Display.emptyStringArray(8, 0);
            for(int j = 0; j<visibleCards[0].length; j++){ //-- parcourir les 4 cartes faces visibles pour un niveau donné (j)
                tierCardsDisplay = Display.concatStringArray(tierCardsDisplay, visibleCards[i][j]!=null ? visibleCards[i][j].toStringArray() : DevCard.noCardStringArray(), false);
            }
            cardDisplay = Display.concatStringArray(cardDisplay, Display.emptyStringArray(1, 40), true);
            cardDisplay = Display.concatStringArray(cardDisplay, tierCardsDisplay, true);
        }
        
        res = Display.concatStringArray(deckDisplay, cardDisplay, false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 52), true);
        res = Display.concatStringArray(res, resourcesToStringArray(), true);
        res = Display.concatStringArray(res, Display.emptyStringArray(35, 1, " \u250A"), false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 54, "\u2509"), true);
                 
        return res;
        
    }

    @Override
    public String[] toStringArray() {
        return boardToStringArray();
    }
    /**
     * affichage de chaque ligne du résultat généré par la fonction boardToStringArray
     */
    public void display(){
        for (String line : boardToStringArray()){
            Game.display.outBoard.println(line);
        }
    }
    
    /**
     * Get all data from the file "stat.csv" and put each line as a String table
     * in a ArrayList 
     */
    public ArrayList<String[]> read_file() throws java.io.IOException,FileNotFoundException{
        
        BufferedReader file = new BufferedReader(new FileReader("stats.csv"));
        String newline;
        // AL qui représente chaque ligne du fichier par un tableau String
        ArrayList<String[]> resultat = new ArrayList<String[]>();
        int i = 0;
        // on parcourt toutes les lignes représentant des cartes
        while ((newline = file.readLine()) != null){
            if (newline != null){
                i++;
                // chaque ligne devient un tableau de string
                String[] a = newline.split(",");
                // on ajoute chaque ligne qui représente une carte dans une AL
                resultat.add(a);
                
            }
        }
        file.close();      

        return resultat;

    }
    /**
     * Création de la liste des DevCartes du jeu
     */
    public void creation_cartes() throws java.io.IOException {
        
        // récupération de toutes les cartes au format String[]
        ArrayList<String[]> cartes_brut = new ArrayList<String[]>();
        cartes_brut = read_file();
    
        // on parcourt chaque String[]
        for (int i = 1; i < cartes_brut.size(); i++){     
            String[] line = cartes_brut.get(i);
            
            // on récupère le level la convertissant de chaine à Integer
            int level = Integer.parseInt(line[0]);  
            if (level > 0){
                
                // création d'un HashMap pour stocker les ressources
                Resources cost = new Resources();
                // on récupère chaque ressource 
                cost.setNbResource(Resource.DIAMOND,Integer.parseInt(line[1])); 
                cost.setNbResource(Resource.SAPPHIRE,Integer.parseInt(line[2])); 
                cost.setNbResource(Resource.EMERALD,Integer.parseInt(line[3])); 
                cost.setNbResource(Resource.RUBY,Integer.parseInt(line[4])); 
                cost.setNbResource(Resource.ONYX,Integer.parseInt(line[5])); 
                // on récupère les points 
                int points = Integer.parseInt(line[6]);
                // On cherche quel est le ressourceType de la carte
                Resource a = Resource.valueOf(line[7]);
                // création de la DevCard et ajout de cette dernière dans une AL
                DevCard carte = new DevCard(cost,level,points,a);
                cartes.add(carte);
                
            }
        }
    
    }
    
    /**
     * création de 3 stack pour stocker les cartes par niveau
     */
    public void hiddenCards() throws java.io.IOException {
        // on créé les DevCard
        creation_cartes();
        // on mélange les cartes
        Collections.shuffle(cartes);
        // on fait 3 tour de boucles pour créer 3 niveaux
        for (int i = 1; i < 4; i++){
            // création d'une pile
            Stack<DevCard> pile = new Stack<DevCard>();
            for (DevCard carte : cartes){
                // ajout dans la pile de toutes les cartes de niveau i
                if (carte.getLevel() == i){
                    pile.push(carte);
                }
            }
            // ajout à stackCards de la pile en question dans la boucle
            stackCards.add(pile);
        }
    }
    
    /**
     * modélisation des cartes visibles sur la plateau sous forme de 
     * tableau de taille 3x4
     */
    public void visible() throws java.io.IOException {
        // on initialise les 3 piles de cartes
        hiddenCards();
        
        // on parcourt chaque level (i+1) possible
        for (int i = 0; i < 3; i++){
            // boucle de 4 tours pour tirer de chaque pile une carte 4 fois
            for (int k = 0; k < 4; k++){
                if (stackCards.get(i).isEmpty()){
                    Game.display.out.println("Pile de carte vide");
                }else{
                // on depile la carte au sommet de la pile 
                DevCard sommet = stackCards.get(i).pop();
                
                // on ajoute cette carte dans visibleCards 
                visibleCards[2-i][k] = sommet;
                }
            }
            
        }
    }
    /**
     * Récupération d'une carte d'un certain level et 
     * d'une certaine colonne dans visibleCards
     * @param int level
     * @param int colonne 
     */
    public DevCard getCard(int level, int colonne){
        int i = 3 -level;
        int j = colonne - 1;    
        // vérification de colonne et de level
        if (i >= 0 && i < 3 && colonne <= 4 && colonne >= 0){
            return visibleCards[i][j];
        }return null;
    }
    
    
    /**
     * tire une nouvelle devCard à la place de celle en paramètre
     * @param DevCard card souhaitant etre remplacé s
     */
    public void updateCard(DevCard card){
        int lvl = card.getLevel();
        int rang = lvl - 1;
        // verifie si la pile de devCard du niveau de la devCard en paramètre n'est pas vide
        
            // on parcourt les devCard visibles sur le plateau du niveau rang
            for (int i = 0; i<visibleCards.length; i++){
                for (int j = 0; j<visibleCards[0].length; j++){
                    if (visibleCards[i][j] != null){
                        // on change la carte passé en paramètre et on la remplace par une qu'on tire de la pile
                        if (visibleCards[i][j].equals(card)){
                            visibleCards[i][j] = drawCard(i);
                        }
                    }
                }
            }
    }
    
    /**
     * permet de tirer la carte d'une pile en fonction du level passé en paramètre
     * @param int rang de la pile 
     */
    public DevCard drawCard(int rang){
        // si la pile de carte du niveau n'est pas vide 
        if(!stackCards.get(2-rang).isEmpty()){
            // on retoune le sommet de la pile
            return stackCards.get(2-rang).pop();
        } else {
            return null;
        }
    }
    
    /**
     * renvoie si il est possible de prendre 2 jetons d'un certain type
     * de resource sur le plateau (il faut au minimum ou plus de 4 jetons de cette
     * resource sur le plateau)
     * @param Resource ressourceType souhaitant etre pris 2 fois
     * @return boolean 
     */
    public boolean canGiveSameTokens(Resource ressourceType){
        // si il y a plus de 4 jetons de la resource passé en paramètre alors peut en prendre 2
        return ressources.getNbResource(ressourceType)>=4;
    }    
    
    /** 
     * renvoie si les resources demandés en paramètre sous forme d'objet 
     * Resources peuvent etre prises en fonction des jetons sur le plateau
     * @param ArrayList<Resource> liste des jetons etre retires du plateau
     * @return boolean
     */
    public boolean canGivDiffTokens(ArrayList<Resource> diffTokens){
        
    
        // on parcourt la liste de jetons/tokens passée en paramètre 
        for (Resource resource : diffTokens){
            // si il n'y a pas de jetons de cette ressource on renvoie false et la boucle s'arrete
            if (ressources.getNbResource(resource) == 0){
                return false;
            }// sinon on continue de boucler et à la fin on renvoie true
        }
        return true;
    }
    
    /**
     * accesseur pour le tableau de tableau de cartes visibles 
     * @return DevCard[][]
     */
    public DevCard[][] getVisibleCards(){
        return visibleCards;
    }
    /**
     * accesseur pour la quantite de jeton pour la ressource 
     * passée en paramètre
     * @param Resource 
     * @return int 
     */
    public int getNbResource(Resource r){
        return ressources.getNbResource(r);
    }
    /**
     * setter pour modifier directement la quantite de jetons sur le plateau 
     * pour la resource passé en paramètre
     * @param Resource r
     * @param int quantite
     */
    public void setNbResource(Resource r, int quantite){
        ressources.setNbResource(r, quantite);
    }
    /**
     * permet de mettre à jour la quantite de ressource r sur le plateau
     */
    public void updateNbResource(Resource r, int quantite){
        ressources.updateNbResource(r,quantite);
    }
    /**
     * accesseur pour avoir les jetons de ressources disponibles sur le plateau
     * de jeu
     * @return ArrayList<Resource>
     */
    public ArrayList<Resource> getAvailableResources(){
        return ressources.getAvailableResources();
    }
}
