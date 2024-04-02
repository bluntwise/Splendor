import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Cette classe représente une carte de développement du jeu.
 * Chaque carte a un niveau, un coût, un nombre de points et un type de ressource.
 *
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 23/12/2023
 */
public class DevCard implements Displayable {
    //initialisation des attributs
    private int level; //niveau de la carte 
    private Resources cost; //ressource d'achat de la carte 
    private int points; //points de prestiges de la carte 
    private Resource resourceType; //ressource bonus 
    
    
    public DevCard(Resources cost, int level, int points, Resource resourceType){       
        this.cost = cost;
        this.level = level;
        this.points = points;
        this.resourceType = resourceType;
        this.cost = cost;
        
        
    }
    public String[] toStringArray(){
        /** EXAMPLE
         * ┌────────┐
         * │①    ♠S│
         * │        │
         * │        │
         * │2 ♠S    |
         * │② ♣E   │
         * │③ ♥R   │
         * └────────┘
         */
        String pointStr = "  ";

        if(getPoints()>0){
            pointStr = Character.toString(getPoints()+9311);
        }
        String[]cardStr = {"\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510",
                            "\u2502"+pointStr+"    "+resourceType.toSymbol()+"\u2502",
                            "\u2502        \u2502",
                            "\u2502        \u2502",
                            "\u2502        \u2502",
                            "\u2502        \u2502",
                            "\u2502        \u2502",
                            "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518"};
        //update cost of the repr
        int i=6;
        for(Resource res : resourceType.values()){ // parcourir l'ensemble des resources (res)en utilisant l'énumération Resource
            if(getCost().getNbResource(res)>0){
                cardStr[i] = "\u2502"+getCost().getNbResource(res)+" "+res.toSymbol()+"    \u2502";
                i--;
            }
        } 
        return cardStr;
    }

    public static String[] noCardStringArray(){
        /** EXAMPLE
         * ┌────────┐
         * │ \    / │
         * │  \  /  │
         * │   \/   │
         * │   /\   │
         * │  /  \  │
         * │ /    \ │
         * └────────┘
         */
        String[] cardStr = {"\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510",
                            "\u2502 \\    / \u2502",
                            "\u2502  \\  /  \u2502",
                            "\u2502   \\/   \u2502",
                            "\u2502   /\\   \u2502",
                            "\u2502  /  \\  \u2502",
                            "\u2502 /    \\ \u2502",
                            "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518"};
        
        return cardStr;
    }
    
    /**
     * Methode toString
     * @return: retourne une représentation visuelle de la carrte 
     */
    public String toString(){
        String cardStr = ""; // initialisation de la variable cardStr
              
        cardStr = getPoints()+"pts, type "+resourceType.toSymbol()+" | coût: ";
        for(Resource res : resourceType.values()){ //parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            if(getCost().getNbResource(res)>0){ //si les ressources d'achat et les ressources bonnus de la carte sont superieur a 0
                //ajout de getCost().getNbResources(res) a cardStr
                cardStr += getCost().getNbResource(res)+res.toSymbol()+" ";
            }
        }
        return cardStr;
    }
    
    /**
     * Accesseurs pour le nombre de points de prestiges de la carte
     * @return: retourne le nombre de points de prestiges de la carte 
     */
    public int getPoints(){
        return points;
    }
    
    /**
     * Accesseurs pour les ressources d'achat de la carte 
     * @return: retourne les ressources d'achat de la carte 
     */
    public Resources getCost(){
        return cost;
    }
    
    /**
     * accesseurs pour le level de de la carte 
     * @return: retourne le level de la carte 
     */
    public int getLevel(){
        return level;
    }
    
    /**
     * acesseurs pour la ressource bonus de la carte 
     * @return: retourne la ressource bonus la carte 
     */
    public Resource getResourceType(){
        return resourceType;
    }
    
    
    
}        