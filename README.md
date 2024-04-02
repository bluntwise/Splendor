1) Projet Splendor

Le but de ce projet est de programmer en équipe le jeu de plateau Splendor, en utilisant la programmation orientée objet.

2) Pour commencer

Pour pouvoir programmer correctement le jeu nous devons connaitre son fonctionnement et les règles du jeu.

Règles du jeu: https://www.regledujeu.fr/splendor/

Jouer en ligne: https://spendee.mattle.online/welcome

3)  Pré-requis

Pour commencer notre projet, certains pré-requis sont nécessaires :
A savoir, les concepts élémentaires de la programmation orienté objet
- Abstraction
- Encapsulation
- Héritage
- Polymorphisme
- Exceptions

4) Environnement

Pour effectuer ce projet en groupe, nous avons utilisé le logiciel de programmation BlueJ. De plus, pour communiquer entre nous avons utilisé la plateforme Discord.

5) Developpement 

Tout d'abord, nous avons eu à notre disposition une base de code, que nous devions compléter à l'aide d'un cahier des charges.
Pour commencer, nous avons réparti les différentes tâches entre nous.
A chaque étape de la programmation nous avons pris le soin de commenter les lignes de code.

Nous avions trois classes principales à implémenter: Board, Player, Game.

La première classe que nous avions implémenté est la classe Board: 
l'objectif de cette classe est de préparer le tableau de jeu, et de le mettre à jour au fur et à mesure que les joueurs prennent des ressources et/ou des cartes.

    - Elle modélise l'ensemble des éléments du plateau de jeu. A savoir, les ressources disponibles, les piles de cartes visibles/cachées.
    - Elle implémente l’interface Displayable
    - Elle implémente la classe DevCard que nous avons définie. Cette classe permet de caratériser les cartes de développement.
    - Elle implémente la classe Resources que nous avons définie ( utilisation d’une table de hachage implémentée avec la classe HashMap ). Celle-ci permet de représenter les jetons disponibles sur le plateau de jeu.
    - Elle comporte des méthodes représentant les interactions avec le plateau de jeu
    
La deuxième classe que nous avons implémenté est la classe Player:
l'objectif de cette classe est de regrouper les caractéristiques communes à tous les joueurs.

    - Celle-ci est abstraite car elle est une super classe des classes HumanPlayer et DumbRobotPlayer (définis après Player)
    - Cette classe est définie par plusieurs méthodes qui permettent de mettre à jour les caractéristiques de chaque joueur.
    - Les sous classes HumanPlayer et DumbRobitPlayer que nous avons définies, représentent respectivement un joueur humain et un joueur robot. Celles-ci permettent à chaque joueur de choisir une action grâce aux classes qui implémentent l'interface Action.


Puis, nous avons implémenté la classe Game:
l'objectif de cette classe est de modéliser une partie de jeu de 2 à 4 joueurs.

    - Cette classe implémente toutes les classes
    - Elle est définie par plusieurs méthodes qui permettent le bon déroulement de chaque partie de jeu.
    - Elle nécessite également la gestion d'erreurs

Après avoir implémenté les différentes méthodes de la classe Game, nous avons créé les classes qui implémentent l'interface Action:

    - PickSameTokensAction (prendre deux jetons de la même ressource)
    - PickDiffTokensAction (prendre trois jetons de ressources différentes)
    - BuyCardAction (acheter une carte développement)
    - DiscardTokensAction (défausser des jetons)
    - PassAction (passer son tour)

5) Packtages utilisés

- ArrayList
- Scanner
- Hashmap
- Stack

6) Difficultées rencontrées

Durant notre implémentation, nous avons rencontré certaines difficultés telles que la compréhension des méthodes, des erreurs lors de l'exécution des classes, problème lors de l'écriture des méthodes.

7) Auteurs

Alan Dely dely.e2202116@etud.univ-ubs.fr et 3 autres Camarades.
