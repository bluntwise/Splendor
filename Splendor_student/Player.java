
// import pour la classe Player
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
/**
 * Classe qui permet de modéliser et afficher tout type de joueur.
 * Chaque joueur a un identifiant, un nom, un nombre de points, des cartes achetées 
 * et des jetons des différentes ressources.
 *
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 *
**/
public abstract class Player implements Displayable {
    // attributs de la classe Player
    private int id;
    private String name;
    private int points ; // nombre de points prestiges
    private ArrayList<DevCard> purchasedCards ; //les cartes achetées
    private Resources resources;//les ressources achetées de type Resources, modélisé par un Hashmap
    
    /**
     * constructeur de la classe Player
     */
    public Player(int id, String name){
        this.id = id;
        this.name = name;
        this.points = 0;
        purchasedCards = new ArrayList<DevCard>();
        resources = new Resources();
        for(Resource res: Resource.values()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            resources.setNbResource(res,0);        
        }
    }
    
    /**
     * accesseurs pour le nom du joueur
     * @return: retourne le nom du joueur
     */
    public String getName(){
        return name;
    }
    
    /** 
     * accesseur pour l'id du joueur
     * @return: retourne l'identifiant du joueur
     */
    public int getId(){
        return id;
    }

    /**
     * accesseurs pour le nombre de points prestiges
     * @return: retourne le nombre de points prestiges
     */
    public int getPoints(){
        return points;
    }
    
    /**
     * Méthode getNbTokens()
     * @return: retourne le nombre total de ressources achetées par le joueur
     */
    public int getNbTokens(){
        // on utilise la méthode getAvailableResources() pour les instances de type Resources
        // on récupère le nombre de ressources achetés avec size()
        int nbResAchetées= resources.getAvailableResources().size();
        return nbResAchetées;
    }
    
    /**
     * Méthode getNbPurchasedCards()
     * @return: retourne le nombre total de cartes achetées par le joueur
     */
    public int getNbPurchasedCards(){
        //pour obtenir le nombre de cartes achetées, on utilise la méthode size de 
        //l'ArrayList qui permet d'obtenir la taille de la liste.
        return purchasedCards.size();
    }
    
    /**
     * Méthode getNbResource()
     * @return: retourne le nombre de ressources achetées pour un type donné
     * @parametre: Prend en paramètre un type de ressource
     */
    public int getNbResource(Resource typeResource){
        // on utilise la méthode getNbResource() de la classe Resouces
        return resources.getNbResource(typeResource);
    }
    
    /**
     * Méthode getAvailableResources()
     * @return: retourne la liste des ressources disponibles
     */
    public ArrayList<Resource> getAvailableResources(){
        // on utilise la méthode getAvailableResources() de la classe Resources
        return resources.getAvailableResources();
    }
    
    /**
     * Methode getResFromCards()
     * @return: retourne le nombre de ressources d’un type donné présents sur les cartes achetées
     * @parametre: Prend en paramètre un type de ressource
     */
    public int getResFromCards(Resource typeResource){
        int nbRes = 0;// initialisation d'une variable nbResqui correspond 
        // au nombre de ressources d’un type donné présents sur les cartes achetées 
        for(DevCard cards : purchasedCards){ // on parcourt toutes les cartes de la liste des cartes achetées par le joueur
            if (cards.getResourceType() == typeResource){ //si la ressource type de la carte est égale à la ressource type demandée,
                // on incrémente le nombre de ressources
                nbRes += 1;
            }
        }
        return nbRes;
    }
    
    /**
     * Méthode updateNbResource
     * met à jour le nb resources pour un type de resource donné, ne retourne rien
     * @parametre: prend en paramètre un type de ressource et une quantité v
     */
    public void updateNbResource(Resource typeResource, int v){
        // on utilise la méthode updateNbResource de la classe Resources
        resources.updateNbResource(typeResource, v);
    }
    
    /**
     * Méthode addPurchasedCard
     * méthode qui permet d'ajouter une carte donnée à la liste des cartes achetées par le joueur, ne retourne rien
     * @parametre: prend en paramètre une carte de développement
     */
    public void addPurchasedCard(DevCard c){
        //on utilise la méthode add() de l'ArrayList pour ajouter la carte à la liste
        purchasedCards.add(c);
    }
    
    /**
     * Méthode updatePoints()
     * méthode qui permet d'ajouter les points d'une devCard aux points du joueur, ne retourne rien
     * @parametre: prend en paramètre une carte de developpement
     */
    public void updatePoints(DevCard c){
        // on récupère le nombre de points prestiges de la carte de développement ( getPoints() )
        // et on l'ajoute au nombre de points prestige du joueur    
        points += c.getPoints();
    }
    
    /**
     * Méthode canBuyCard()
     * vérifie si le joueur a assez de ressources pour acheter une carte donnée
     * @return: retourne un boolean
     * @parametre: prend en paramètre une carte de developpement
     */
    public boolean canBuyCard(DevCard c){
        if (c!=null){
            int a = 0; // initialisation de la variable a (le joueur à le nombre de ressources necessaire pour chaque resources ou non)
            Resource[] resources = Resource.values(); // DIAMOND,SAPPHIRE,EMERALD,ONYX,RUBY
            for(Resource res: resources){ // on parcourt l'ensemble des resources (res) de la liste resources
                if(c.getCost().getNbResource(res) <= getNbResource(res) +  getResFromCards(res)){
                    // si le nombre de resources nécessaire pour acheté la carte ( c.getCost().getNbResource(res) )
                    // est inférieur ou égal au nombre de ressources detenues par le joueur (getNbResource(res)+ getResFromCards(res))
                    // alors le joueur peut acheté la carte 
                    a+=1; //le joueur à le nombre de ressources necessaires pour la ressource correspondant au tour de boucle
                }
            }
            return a == 5; // True si le joueur à le nombre de ressources necessaires pour chaques ressources (il y a 5 ressources en tout) il peut donc acheter la carte. 
            //False sinon
        }else{
            return false; // la carte est null, il ne peut pas acheter la carte
        }
    }
    /* --- Stringers --- */
   
     
    public String[] toStringArray(){
        /** EXAMPLE. The number of resource tokens is shown in brackets (), and the number of cards purchased from that resource in square brackets [].
         * Player 1: Camille
         * ⓪pts
         * 
         * ♥R (0) [0]
         * ●O (0) [0]
         * ♣E (0) [0]
         * ♠S (0) [0]
         * ♦D (0) [0]
         */
        String pointStr = " ";
        String[] strPlayer = new String[8];
        
        
        if(points>0){
            pointStr = Character.toString(points+9311);
        }else{
            pointStr = "\u24EA";
        }

        
        strPlayer[0] = "Player "+(id+1)+": "+name;
        strPlayer[1] = pointStr + "pts";
        strPlayer[2] = "";
        for(Resource res: Resource.values()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            strPlayer[3+(Resource.values().length-1-res.ordinal())] = res.toSymbol() + " ("+resources.getNbResource(res)+") ["+getResFromCards(res)+"]";
        }
        
        return strPlayer;
    }
    
    /**
     * Méthode absrtaite chooseAction()
     * redéfinition dans les sous classes DumbRobotPlayer et HumanPlayer
     * permet au joueur de choisir l'action qu'il souhaite effectuer
     * prend en paramètre le plateau de jeu
     */
    public abstract Action chooseAction(Board b);
    
    /**
     * Méthode absrtaite chooseDiscardingTokens()
     * redéfinition dans les sous classes DumbRobotPlayer et HumanPlayer
     * méthode pour déffausser les jetons si le joueur en possède plus que 10
     * retourne la liste des ressources supprimées
     */
    public abstract ArrayList<Resource> chooseDiscardingTokens();
}