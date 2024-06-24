class Wythoff1{
	void principal(){
		int [] placepion;
		jouer();
		//System.out.println("***TEST METHODE***");
		//testcreerPlateau();
		//testdeplacePion();
		//testestArrive();
		//testchangeJoueur();
	}
	
	/**
	* Affichage du plateau de Wythoff avec les indices de lignes 
	* et de colonnes 
	* @param plateau le tableau a afficher 
	*/
	void affichePlateau(char[][] plateau){
		System.out.println();
		for (int i=plateau.length-1; i>=0; i--){
			//problème d'affichage des nombres à deux chiffres
			if (i>9){
				System.out.print(" "+i);
			} else {
				System.out.print(" "+i+" ");
			}
			for (int j=0; j<plateau[i].length; j++){
				System.out.print(" | "+plateau[i][j]);
			}
			System.out.println(" | ");
		}
		System.out.print("      "+0);
		for (int k=1; k<plateau.length; k++){
			if (k<10){
				System.out.print("   "+k);
			} else {
				System.out.print("  "+k);
			}
		}
		System.out.println();
	}

	/**
	* Créer un plateau de jeu carré rempli de caractere espace ’ ’
	* @param lg taille du plateau
	* @return tableau de caractere en deux dimensions
	*/
	char[][] creerPlateau(int lg){
		char [][] plateau= new char[lg][lg];
		for (int i=0; i<lg; i++){
			for (int j=0; j<lg; j++){
				plateau[i][j]=' ';
			}
		}
		return plateau;
	}
	
	/**
	* Demande la direction puis le nombre de cases à parcourir à l’utilisateur. 
	* Si la direction est correcte et que les nombres de cases ne dépasse pas 
	* le plateau, le pion est déplacé sur le plateau sinon, on redemande
	* des coordonnées à l’utilisateur en expliquant l’erreur
	* @param plateau le plateau de jeu
	* @param joueur le nom du joueurp ou du joueurd
	* @param placepion la position du pion sur le plateau
	*/
	void deplacePion(char[][] plateau, String joueur, int[] placepion){
		char direction;
		int nbcases;
		boolean place = false;
		while (!place){
			System.out.println("C'est au tour du joueur: "+joueur);
			direction = SimpleInput.getChar("Direction g(gauche)  b(bas)  d(diagonale) :");
			nbcases = SimpleInput.getInt("Nombre de case a parcourir: ");

			if (direction == 'g'){
				while ((placepion[0] - nbcases)<0){
					System.out.println("Le nombre de cases est trop grande!");
					nbcases = SimpleInput.getInt("Veuillez saisir un autre nombre de cases: ");
				}
				placepion[0] = (placepion[0] - nbcases);
				place = true;
			} else if (direction == 'b'){
				while ((placepion[1] - nbcases)<0){
					System.out.println("Le nombre de cases est trop grande!");
					nbcases = SimpleInput.getInt("Veuillez saisir un autre nombre de cases: ");
				}
				placepion[1] = (placepion[1] - nbcases);
				place = true;
			} else if (direction == 'd'){
				while ((placepion[0] - nbcases)<0 && (placepion[1] - nbcases)<0){
					System.out.println("Le nombre de cases est trop grande!");
					nbcases = SimpleInput.getInt("Veuillez saisir un autre nombre de cases: ");
				}
				placepion[0] = (placepion[0] - nbcases);
				placepion[1] = (placepion[1] - nbcases);
				place = true;
			} else {
				System.out.println("La direction est incorrect");
			}
		}
		
		// suppression de l'emplacement précédent
		for (int i=0; i<plateau.length; i++){
			for (int j=0; j<plateau[i].length; j++){
				if(plateau[i][j]=='o'){
					plateau[i][j] = ' ';
				}
			}
		}
		// placement du nouvelle emplacement du pion sur le plateau pour l'affichage
		int p1 = placepion[1];
		int p2 = placepion[0];
		plateau[p1][p2]='o';

	}
	
	/**
	* Verifie si le pion est arrivé à la position (0,0)
	* @param placepion la position du pion sur le plateau
	* @return true si le pion est à la position (0,0), false sinon.
	*/
	boolean estArrive(int [] placepion){
		boolean arrive=false;
		if (placepion[0] == 0 && placepion[1] == 0){
			arrive=true;
		}
		return arrive;
	}
	
	/**
	* Change le joueur courant
	* @param joueurInitial une chaine de caractère représentant le joueurp ou le joueurd
	* @return si le joueurp est en parametre alors renvoie joueurd
	* sinon renvoie joueurp
	*/
	String changeJoueur(String joueurInitial, String joueurp, String joueurd){
		String joueurSuivant;
		if (joueurInitial == joueurp){
			joueurSuivant = joueurd;
		} else {
			joueurSuivant = joueurp;
		}
		return joueurSuivant;
	}
	
	/**
	* Lance une partie de Wythoff
	*/
	void jouer(){
		// nom du premier joueur
		String joueurp = SimpleInput.getString("Entrez le nom du premier joueur: ");
		// nom du deuxième joueur
		String joueurd = SimpleInput.getString("Entrez le nom du second joueur: ");
		//nom du joueur qui commence
		String joueur = joueurp;
		
		//Demande une taille de plateau à l’utilisateur
		int taille = SimpleInput.getInt("Entrez la taille du plateau: ");
		//la taille du tableau ne doit pas dépasser 99 cases 
		//et ne doit pas être en dessous de 2 cases
		while (taille > 99 || taille < 2){
			taille = SimpleInput.getInt("Entrez la taille du plateau: ");
		}
		//Création du jeu
		char[][] plateau = creerPlateau(taille);
		
		//choix et placement du pion initial
		int [] placepion = {(int) (Math.random() * taille), (int) (Math.random() * taille)};
		//boucle pour que la place du pion ne soit pas une place pour
		//atteindre facilement (0,0),càd sa diagonale, l'abscisse 0 et l'ordonnée 0
		while (placepion[0] == 0 || placepion[1]== 0 || placepion[0] == placepion[1]){
			placepion[0] = (int) (Math.random() * taille);
			placepion[1] = (int) (Math.random() * taille);
		}
		plateau[placepion[1]][placepion[0]]= 'o';
		
		//Boucle de jeu : elle continue tant que le pion n'est pas arrivé à la case (0,0)
		boolean estarrive = estArrive(placepion);
		while(estarrive==false){
			//Affichage du tableau
			affichePlateau(plateau);
			//les deux joueurs jouent l’un après l’autre
			deplacePion(plateau, joueur, placepion);
			//Changement de joueur
			joueur = changeJoueur(joueur, joueurp, joueurd);
			//on regarde s'y le pion est arrivé à la case (0,0)
			estarrive = estArrive(placepion);
		}
		// Affichage du tableau final
		affichePlateau(plateau);
		// Annonce de fin du jeu et déclaration du gagnant s’il existe
		System.out.println("Fin du jeu ! "+changeJoueur(joueur, joueurp, joueurd)+" a gagne ;)");
	}
	
	
	// ***TEST METHODE***
	
	//test fonction creerPlateau
	void testcreerPlateau(){
		System.out.println();
		System.out.println("*** testcreerPlateau");

		char [][] result1 = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		testcascreerPlateau(3, result1);
		char [][] result2 = {{' ',' '},{' ',' '}};
		testcascreerPlateau(2, result2);
		char [][] result3 = {{' '}};
		testcascreerPlateau(1, result3);
	}

	void testcascreerPlateau(int lg, char[][] result){
		//Arrange
		System.out.print("La fonction renvoie bien le tableau avec la taille "+lg+" : ");
		
		//Act
		char [][] resExec=creerPlateau(lg);
		affichePlateau(resExec);
		
		//Assert
		if (resExec.length == result.length){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	
	//test fonction deplacePion
	void testdeplacePion (){
		System.out.println();
		System.out.println("*** testdeplacePion");
		
		char [][] plateau = {{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '}};
		String joueur = "test";
		
		int[] placepion1 = {2,4};
		int[] result1 = {0,4};
		char direction1 = 'g';
		int nbcases1 = 2;
		testcasdeplacePion(plateau, joueur, placepion1, result1, direction1, nbcases1);
		
		int[] placepion2 = {2,4};
		int[] result2 = {2,1};
		char direction2 = 'b';
		int nbcases2 = 3;
		testcasdeplacePion(plateau, joueur, placepion2, result2, direction2, nbcases2);
		
		int[] placepion3 = {2,4};
		int[] result3 = {0,2};
		char direction3 = 'd';
		int nbcases3 = 2;
		testcasdeplacePion(plateau, joueur, placepion3, result3, direction3, nbcases3);
	}
	
	void testcasdeplacePion(char[][] plateau, String joueur, int[] placepion, int[] result, char direction, int nbcases){
	//Arrange
		System.out.println("La fonction deplacePion deplace la position du pion de (2,4) a ("+result[0]+","+result[1]+") avec un input ("+direction+","+nbcases+"): ");
		
		//Act
		deplacePion(plateau,joueur,placepion);
		int [] resExec = placepion;
		
		//Assert
		if (resExec[0] == result[0] && resExec[1] == result[1]){
			System.out.println("=> OK");
		} else {
			System.err.println("=> ERREUR");
		}
	}


	//test fonction estArrive
	void testestArrive(){
		System.out.println();
		System.out.println("*** testestArrive");
		
		int [] placepion1 = {0, 0};
		testcasestArrive(placepion1, true);
		int [] placepion2 = {1, 2};
		testcasestArrive(placepion2, false);
		int [] placepion3 = {0, 2};
		testcasestArrive(placepion3, false);
	}

	void testcasestArrive(int [] placepion, boolean result){
		//Arrange
		System.out.print("La fonction estArrive renvoie bien: "+result+" quand placepion est egal a ("+placepion[0]+","+placepion[1]+"): ");
		
		//Act
		boolean resExec=estArrive(placepion);
		
		//Assert
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
		
	}
	
	
	//test fonction changeJoueur
	void testchangeJoueur(){
		System.out.println();
		System.out.println("*** testchangeJoueur");
		
		testcaschangeJoueur("Toto", "Toto", "Titi", "Titi");
		testcaschangeJoueur("Titi", "Toto", "Titi", "Toto");
	}

	void testcaschangeJoueur(String joueurInitial, String joueurp, String joueurd, String result){
		//Arrange
		System.out.print("Le joueur initial etait: "+joueurInitial+" et la fonction renvoie donc: "+result+" : ");
		
		//Act
		String resExec = changeJoueur(joueurInitial, joueurp, joueurd);
		
		//Assert
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
		
	}
}
