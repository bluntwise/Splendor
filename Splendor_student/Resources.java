
import java.util.HashMap;
import java.util.Map;
import java.lang.Math;
import java.util.ArrayList;

/**
 * On modélise les ressources par un HashMap sous cette forme
 * ressources = { 
 *     Resource.DIAMOND -> 4,
 *     Resource.EMERALD -> 6,
 *     Resource.RUBY -> 1,
 *     Resource.ONYX -> 3,
 *     Resource.SAPPHIRE
 *  }
 *  On étend la classe HashMap<Resource, Integer> à Resources pour que 
 *  Resources soit lui meme un HashMap.
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */

public class Resources extends HashMap<Resource, Integer>
{    
    public Resources(){
        //on initialise notre HashMap<Resource, Integer> 
        super();
    }
    
    /** 
     * renvoie la qqté de typeResource dans Resources
     * Exemple avec le HashMap du début : 
     * ressources.getNbResource(Resource.DIAMOND) -> 4
     */ 
    public int getNbResource(Resource typeResource){
        //utilisation de la methode get() de HashMap pour acceder a la valeur
        if (super.get(typeResource) == null){ //si get(typeResource) est null
            return 0; //la quantite est egale a 0
        }else{
            return super.get(typeResource); 
        }
    }

    /** 
     * permet de modifier la valeur sur laquelle typeResource renvoyait 
     * directement. Exemple :
     * ressources.setNbResource(Resource.DIAMOND,5) -> rien
     * et donc Resource.DIAMOND = 5
     */
    
    public void setNbResource(Resource typeResource, int quantite){
        //utilisation de la methode put() de HashMap pour ajouter un element
        if (quantite >= 0){ //si la nvll quantite est superieur à 0 
            super.put(typeResource, quantite);//on change la quantite par la nvll la nvll quantite
        }
    }
    
    /** 
     * permet d'ajouter ou d'enlever une quantite sur laquelle renvoit typeResource
     * Exemple : ressources.updateNbresource(Resource.DIAMOND, 3) -> rien
     * Resource.DIAMOND = 8 (5+3)
     */ 
    public void updateNbResource(Resource typeResource, int quantite){
        //utilisation de la methode get() de HashMap pour acceder a la valeur
        if (get(typeResource) != null){ //si get(typeResource) est different de null
            //on ajoute la nvll quantite a celle de la quantite deja present de get(typeResourve) 
            int temp_value = quantite + get(typeResource);
            if (temp_value >= 0){//si temp_value est suprieur ou egale a 0 
                //la nvll quantite est egale  a temp_value
                setNbResource(typeResource, temp_value);
            }
        }else{
            //la nvll quantite est egale a quantite
            setNbResource(typeResource, quantite);
        }
    }
    
    /**
     * Renvoie une ArrayList avec des objets de type resource à l'intérieur
     * qui renvoie une sur une valeur > 0
     * Exemple : ressources.getAvailableResources() -> ArrayList<Resource>
     * [Resource.DIAMOND,Resource.EMERALD,Resource.ONYX, ...]
     */
    public ArrayList<Resource> getAvailableResources(){
        //utilisation de la methode add() d'ArrayList qui permet d'ajouter un element
        //utilisation de la methode getKey() de HashMap pour avoir que la clé 
        //utilisation de la methode getValue() de HashMap pour avoir que la valeur
        ArrayList<Resource> r = new ArrayList<Resource>();// initialisation de la variable r
        for (Map.Entry<Resource, Integer> mapelt : super.entrySet()) {//on parcourt les elements mapelt 
            
            if (mapelt.getValue() > 0){//si mapel.getValue est superieur a 0
                //on ajoute mapelt.getKey() a la liste r
                r.add(mapelt.getKey());
            }
        }
        return r;
    }
    
}