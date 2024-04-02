     import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Classe qui permet de créer une partie, de la faire se dérouler et d'afficher 
 * tous les joueurs et le tableau de jeu en l'actualisant à chaque action. 
 * Chaque partie a un plateau de jeu et un nombre de joueurs.
 * Une simulation de 100 parties avec seulement des robots est possible.
 *
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public class Game {
    /* L'affichage et la lecture d'entrée avec l'interface de jeu se fera entièrement via l'attribut display de la classe Game.
     * Celui-ci est rendu visible à toutes les autres classes par souci de simplicité.
     * L'intéraction avec la classe Display est très similaire à celle que vous auriez avec la classe System :
     *    - affichage de l'état du jeu (méthodes fournies): Game.display.outBoard.println("Nombre de joueurs: 2");
     *    - affichage de messages à l'utilisateur: Game.display.out.println("Bienvenue sur Splendor ! Quel est ton nom?");
     *    - demande d'entrée utilisateur: new Scanner(Game.display.in);
     */
    private static final int ROWS_BOARD=50, ROWS_CONSOLE=12, COLS=82;
    public static final  Display display = new Display(ROWS_BOARD, ROWS_CONSOLE, COLS);
    // plateau de jeu
    private Board board;
    // liste des joueurs présents dans la partie
    private List<Player> players;
    // nombre de jouers présents dans la partie
    private int nbPlayers;
    // variable qui est permet de savoir qui joue 
    private int current;
    public static int compteurPassAction;
    public static int compteurPartieInfinie = 0;
    // constante accessible partout dans le projet qui permet de ne pas recréer un scanner
    public static final Scanner scanner = new Scanner(Game.display.in);
    public static void main(String[] args) throws java.io.IOException{
        //-- à modifier pour permettre plusieurs scénarios de jeu
        
        // variable pour continuer à demander à l'utilisateur si il a bien rentré oui ou non
        boolean c = false;
        // variable qui permet de savoir si le string 
        int string_problem = 0;
        display.out.println("Voulez vous faire une simulation ? Taper oui sinon non");
        String simulation;
        // tant que l'utilisateur n'a pas bien tapé oui ou non
        while (!c){
            try{
                
                // si c'est le premier input
                if (string_problem != 0){
                    simulation = scanner.nextLine().substring(1);
                }// sinon
                else{
                    simulation = scanner.nextLine();
                    string_problem = 1;
                }
                // si l'utilisateur veut une simulation de 100 parties avec 4 robots
                if (simulation.equals("oui")){
                    int i = 0;
                    display.out.println("SIMULATION");
                    c = true;
                    while (i<100){
                        display.out.println("Bienvenue sur Splendor !");
                        Game game = new Game(4,true);
                        game.play();
                        i++;
                        System.out.println(i);
                        c = true;
                    }System.out.println("Nombre de partie infinie avec les robots : " + compteurPartieInfinie);
                }
                // si il veut jouer 
                else if (simulation.equals("non")){
                    Game game = new Game(4);
                    game.play();
                    c = true;
                }else{
                    throw new Exception();
                }
            }catch (Exception e){
                display.out.println("Erreur de saisie. \nVoulez vous faire une simulation ? Taper oui sinon non");
            }
        }
    }
    // 
    public Game(int nbPlayers) throws IllegalArgumentException,java.io.IOException{
        compteurPassAction = 0;
        if (nbPlayers < 2 || nbPlayers > 4){
            throw new IllegalArgumentException("Le nombre de joueurs doit etre compris entre 2 et 4.");
        } 
        else{
            
            this.nbPlayers = nbPlayers;
            players = new ArrayList<Player>();
            board = new Board(nbPlayers);
            Game.display.out.println("Bienvenue sur Splendor ! Quel est ton nom?");
            String nom = Game.scanner.nextLine().substring(1);
            players.add(new HumainPlayer(1, nom));

            for(int i = 1; i<nbPlayers; i++){
                players.add(new DumbRobotPlayer(i+1,"robot "+i));
            }
        }
    }
    
    // constructeur pour lancer la simulation avec 100 parties 
    public Game(int nbPlayers, boolean c) throws IllegalArgumentException,java.io.IOException{
        compteurPassAction = 0;
        if (nbPlayers < 2 || nbPlayers > 4){
            throw new IllegalArgumentException("Le nombre de joueurs doit etre compris entre 2 et 4.");
        } 
        else{
            // initialisation des attributs
            this.nbPlayers = nbPlayers;
            players = new ArrayList<Player>();
            board = new Board(nbPlayers);
            // on ajoute le nombre de robots souhaités 
            for(int i = 1; i<=nbPlayers; i++){
                players.add(new DumbRobotPlayer(i+1,"ROBOT "+i));
            }
        }        
    }
    /**
     * getter de la taille de la liste des joueurs présents dans le plateau
     */
    public int getNbPlayers(){
        return players.size();
    }
    /**
     * méthode d'affichage du jeu
     */
    private void display(int currentPlayer){
        String[] boardDisplay = board.toStringArray();
        String[] playerDisplay = Display.emptyStringArray(0, 0);
        for(int i=0;i<players.size();i++){
            String[] pArr = players.get(i).toStringArray();
            if(i==currentPlayer){
                pArr[0] = "\u27A4 " + pArr[0];
            }
            playerDisplay = Display.concatStringArray(playerDisplay, pArr, true);
            playerDisplay = Display.concatStringArray(playerDisplay, Display.emptyStringArray(1, COLS-54, "┉"), true);
        }
        String[] mainDisplay = Display.concatStringArray(boardDisplay, playerDisplay, false);
        display.outBoard.clean();

        display.outBoard.print(String.join("\n", mainDisplay));
        display.outBoard.println("\nCode pour choisir des ressources dans la console :");
        display.outBoard.println("E -> EMERALD");
        display.outBoard.println("O -> ONYX");
        display.outBoard.println("R -> RUBY");
        display.outBoard.println("D -> DIAMOND");
        display.outBoard.println("S -> SAPPHIRE");
        
        Game.display.outBoard.println("\nCode pour choisir les action");
        Game.display.outBoard.println("1 -> Prendre deux jetons de la meme ressource");
        Game.display.outBoard.println("2 -> Prendre 3 jetons de ressources différentes ");
        Game.display.outBoard.println("3 -> Achetez une Carde de devellopement");
        Game.display.outBoard.println("4 -> Passer son tour");
        
        Game.display.outBoard.println("\nFormat pour séléctionner plusieurs ressource: ");
        Game.display.outBoard.println("écrire les initiales des ressources en majuscules séparées d'un espaces");
        Game.display.outBoard.println("Exemple : 'E R D' on veut séléctionner EMERALD RUBY et DIAMOND");

        Game.display.outBoard.println("\nPour acheter une carte, la colonne est comprise entre 1 et 4. ");
        Game.display.outBoard.println("Si vous voulez achetez une carte dans la colonne la plus à gauche tapez 1");
        Game.display.outBoard.println("puis 2 si la carte est dans la 2 ème colonne la plus gauche jusqu'à 4.");
        
    }
    /**
     * méthode qui lance la partie avec 
     */
    public void play(){
        // tant que la partie n'est pas fini on continue à faire jouer les joueurs
        while(isGameOver() == false){
            
            // on joueur est la variable qui permet d'obtenir l'id du joueur qui doit jouer 
            int joueur = current % nbPlayers;
            if (joueur == 1){
                compteurPassAction = 0;
            }
            // affichage du joueur avant qu'il ne joue mis à jour
            display(joueur);
            // on fait jouer le joueur
            Game.display.out.println("\nC'est au tour de " + players.get(joueur).getName() + " de jouer : ");
            move(players.get(joueur));
            // on mets l'affichage du joueur à jour
            display(joueur);
            // on regarde si le joueur doit défausser des jetons ou non
            discardToken(players.get(joueur));
            // dernière mise à jour de l'affichage du joueur
            display(joueur);
            // on fait avancer l'état de la partie en incrémentant current de 1
            current += 1;
        }
        // quand la partie est finie, affichage du gagnant
        gameOver();
    }

    /**
     * Cette méthode permet de faire jouer le joueur que l'on rénseigne en paramètre
     */
    private void move(Player player){
        Action a = player.chooseAction(board);
        // si l'action renvoyé par chooseAction n'est pas nulle on éxécute l'action 
        if (a != null){
            a.process(board);
        }
    }
    
    /**
     * méthode qui mets à jour les ressources présentes sur le plateau de jeu
     * en fonction de ce que le joueur renseigné en paramètres à défausser 
     */
    private void discardToken(Player player){
        // 
        ArrayList<Resource> jetons = player.chooseDiscardingTokens();
        for (Resource jeton : jetons){
            board.updateNbResource(jeton, 1);
        }
    }

    /**
     * renvoie vrai si le jeu est fini et faux sinon
     */
    public boolean isGameOver(){
        if (compteurPassAction == nbPlayers){
            return true;
        }
        
        // on vérifie que tous les jouers ont joué sur le tour en question
        if (current % nbPlayers == 0){
            // on parcourt tous les joueurs
            for (Player p:players){
                // si le joueur p a plus de 15 points, on renvoie vrai
                if (p.getPoints() >= 15){
                    return true;
                }
            }
        }
        // si aucun joueur n'a plus de 15 points alors on renvoie que la partie n'est pas fini
        return false;
    }

        
    
    private void gameOver_(){//initialisation la variable plusPoint
        Player gagnant = players.get(0); 
        int nbgagnant=1; //nombre de gagnant dans la partie//vérification que la méthode isGameOver est vrai pour chercher le vrai gagnant
        
        if(isGameOver()){
            for(int i=1;i<players.size();i++){//vérification que le joueur est +15 points et que ses point soit superieur de la variable gagnant 
                if(players.get(i).getPoints()>15 && players.get(i).getPoints()>gagnant.getPoints()){
                gagnant=players.get(i);
                nbgagnant=1; //reisialisation de gagnant à 1}//le joueur a +15 points mais ses points sont égale au nombre de point de la variable gagnant
                }
                else if (players.get(i).getPoints()>15 && players.get(i).getPoints()==gagnant.getPoints()){//si le joueur a moins de carte que la variable gagnant
                    if(players.get(i).getNbPurchasedCards()<gagnant.getNbPurchasedCards()){
                        gagnant=players.get(i);
                        nbgagnant=1; //renisialisation de gagnant à 1}//si le joueur a le même nombre de carte que la variable gagnant
                        }
                        
                    }
                    else if(players.get(i).getNbPurchasedCards()==gagnant.getNbPurchasedCards()){
                    nbgagnant+=1; //on ajoute un gagnant en plus 
                }
                }
            }
        //si le nombre de gagnant est de 1 on affiche son nom.
        if(nbgagnant==1){
            Game.display.out.println("Le gagnant de la partie est "+gagnant.getName());
        }
        //si le nombre de gagnant est different de 1 on considère que la partie est nulle.
        else{
            Game.display.out.println("La partie est nulle");}
        }
        
    /**
     * affiche le joueur qui a gagné ou que la partie est nulle
     * 
     */
    public void gameOver(){
        if (compteurPassAction == nbPlayers){
            Game.display.out.println("Partie finie, tous les joueurs ont passé leur tour.");
            compteurPartieInfinie++;
        }else{
            ArrayList<Player> gagnants = new ArrayList<Player>();
            for (Player p:players){
                if (p.getPoints() >= 15){
                    gagnants.add(p);
                }
            }
            if (gagnants.size() > 1){
                Player g_temp = gagnants.get(0);
                int c = 0;
                //parcours de la liste des gagnants en partant du deuxième élément de la liste
                for (int i = 1; i < gagnants.size(); i++){
                    //cas où le gagnant temporel a moins de points que l'élément courant de la liste 
                    if (gagnants.get(i).getPoints() > g_temp.getPoints()){
                        //on passe le nombre de gagnant à 1
                        c=1; 
                        //on change la variable temporelle
                        g_temp = gagnants.get(i);
                    }
                    //cas d'égalité
                    else if (gagnants.get(i).getPoints() == g_temp.getPoints())
                        //cas où l'élément courant a moins de carte acheté
                        if (c == 0 && gagnants.get(i).getNbPurchasedCards() < g_temp.getNbPurchasedCards()){
                            //l'élément courant devient le gagnant
                            g_temp = gagnants.get(i);
                            c=1;
                        }//cas où ils ont le même nombre de cartes achetées
                        else if (gagnants.get(i).getNbPurchasedCards() == g_temp.getNbPurchasedCards()){
                            c++;
                        }
                }
                //afffichage selon les différents cas
                if (c == 1){
                    Game.display.out.println("Bravo à " + g_temp.getName() + " le gagnant de la partie avec un total de " + g_temp.getPoints() + " de points et le moins de carte achetées!");
                }else if (c != 0){
                    Game.display.out.println("Partie nulle," + c + " jouers sont à égalités ! ");
                }
            }else{
                Player g = gagnants.get(0);
                Game.display.out.println("Bravo à " + g.getName() + " le gagnant de la partie avec un total de " + g.getPoints() + " points !");
            }
        }
        
    }
}
    
    
    
    