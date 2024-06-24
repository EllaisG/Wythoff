class Wythoff2{
	void principal(){
		int [] placepion;
		jouer();
		//System.out.println("***TEST METHODE***");
		//testcreerPlateau();
		//testdeplacePion();
		//testtourOrdi();
		//testpositionGagnante();
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
	* Si la direction est correcte et que les nombres de cases ne dépasse pas le plateau,
	* le pion est déplacé sur le plateau sinon, on redemande
	* des coordonnées à l’utilisateur en expliquant l’erreur
	* @param plateau le plateau de jeu
	* @param joueur le nom du joueurp
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
				while ((placepion[0] - nbcases)<0 && nbcases<=0){
					System.out.println("Le nombre de cases est trop grande!");
					nbcases = SimpleInput.getInt("Veuillez saisir un autre nombre de cases: ");
				}
				placepion[0] = (placepion[0] - nbcases);
				place = true;
			} else if (direction == 'b'){
				while ((placepion[1] - nbcases)<0 && nbcases<=0){
					System.out.println("Le nombre de cases est trop grande!");
					nbcases = SimpleInput.getInt("Veuillez saisir un autre nombre de cases: ");
				}
				placepion[1] = (placepion[1] - nbcases);
				place = true;
			} else if (direction == 'd'){
				while ((placepion[0] - nbcases)<0 && (placepion[1] - nbcases)<0 && nbcases<=0){
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
		
		// placement de l'emplacement précédent
		for (int i=0; i<plateau.length; i++){
			for (int j=0; j<plateau[i].length; j++){
				if(plateau[i][j]=='o'){
					plateau[i][j] = ' ';
				}
			}
		}
		// placement du nouvelle emplacement du pion
		int p1 = placepion[1];
		int p2 = placepion[0];
		plateau[p1][p2] = 'o';
	}
	
	/**
	* Genère la direction et le nombre de cases à parcourir dans le plateau
	* @param plateau le plateau de jeu
	* @param joueur le nom du joueur, autrement dit ordi
	* @param placepion la position du pion sur le plateau
	*/
	void tourOrdi(char[][] plateau, String joueur, int[] placepion){
		System.out.println("C'est au tour du joueur: "+joueur);
		
		//le programme choisit quand c’est possible une position gagnante
		int [][] position = positionGagnante(plateau);
		boolean limite = false;
		boolean change = false;
		for (int i=0; i<position.length && !limite && !change; i++){
			//si le place gagnante est trop grande par rapport à la place du pion 
			//alors on arrête les test
			if (placepion[0]>=position[i][0] && placepion[1]>=position[i][1] || placepion[0]>=position[i][1] && placepion[1]>=position[i][0]){
				//si le pion est déjà sur une position gagnante
				//alors faire un déplacement aléatoire
				if (placepion[0] == position[i][0] && placepion[1] == position[i][1] || placepion[0] == position[i][1] && placepion[1] == position[i][0]){
					change = false;
				}
				//si le pion est sur la même abscisse qu'une position gagnante de la moitié supérieur
				//et que l'ordonnée du pion est plus grand que l'ordonnée de la position gagnante
				if (placepion[0] == position[i][0] && placepion[1]>position[i][1] && !change){
					placepion[1] = position[i][1];
					change = true;
				} 
				//si le pion est sur la même abscisse qu'une position gagnante de la moitié inférieur
				//et l'ordonnée du pion est plus grand que l'ordonnée de la position gagnante
				if (placepion[0] == position[i][1] && placepion[1]>position[i][0] && !change){
					placepion[1] = position[i][0];
					change = true;
				} 
				//si le pion est sur le même ordonnée d'une position gagnante de la moitié inférieur
				//et l'abscisse du pion est plus grand que l'abscisse de la position gagnante 
				if (placepion[1] == position[i][0] && placepion[0]>position[i][1] && !change){
					placepion[0] = position[i][1];
					change = true;
				}
				//si le pion est sur le même ordonnée qu'une position gagnante de la moitié supérieur
				//et l'abscisse du pion est plus grand que l'abscisse de la position gagnante 
				if (placepion[1] == position[i][1] && placepion[0]>position[i][0] && !change){
					placepion[0] = position[i][0];
					change = true;
				} 
				//si le pion est sur la diagonale pour aller à (0,0)
				if (placepion[0] == placepion[1] && !change){
					placepion[0] = 0;
					placepion[1] = 0;
					change = true;
				} 
				 
				//si une des positions du pion est sur la ligne/colonne 0
				//autrement dit peut aller directement à (0,0)
				if (placepion[0]==0 || placepion[1]==0 && !change){
					placepion[0] = 0;
					placepion[1] = 0;
					change = true;
				}
				//si en faisant une diagonale le pion arrive sur une position gagnante
				int p1 = placepion[0]-position[i][0];
				int p2 = placepion[1]-position[i][1];
				if ((p1 == p2) && (p1!= 0) && !change){
					placepion[0] = position[i][0];
					placepion[1] = position[i][1];
					change = true;
				}
				//si en faisant une diagonale le pion arrive sur une position gagnante
				int p3 = placepion[0]-position[i][1];
				int p4 = placepion[1]-position[i][0];
				if ((p3 == p4) && (p3!= 0) && !change){
					placepion[0] = position[i][1];
					placepion[1] = position[i][0];
					change = true;
				}
			} else {
				limite = true;
			}
		}
		
		//le programme fait un déplacement au hasard si aucune position gagnante n'a été trouvé
		char direction;
		int nbcases;
		boolean place = false;
		if (change == false){
			while (!place){
				char [] directions = {'g', 'b', 'd'};
				int d = (int) (Math.random() * 3);
				direction = directions[d];
				
				//l'ordi va vers la direction avec un nombre de cases inférieures 
				//au nombres de cases entre 0 et la position du pion
				if (direction == 'g'){
					if (placepion[0] == 1){
						nbcases = 1;
					} else {
						nbcases = (int) (Math.random() * placepion[0]);
						while (nbcases == 0){
							nbcases = (int) (Math.random() * placepion[0]);
						}
					}
					placepion[0] = (placepion[0] - nbcases);
					place = true;
				} 
				else if (direction == 'b'){
					if (placepion[1] == 1){
						nbcases = 1;
					} else {
						nbcases = (int) (Math.random() * placepion[1]);
						while (nbcases == 0){
							nbcases = (int) (Math.random() * placepion[1]);
						}
					}
					placepion[1] = (placepion[1] - nbcases);
					place = true;
				} 
				else if (direction == 'd'){
					//pour ne pas que l'ordi fasse un nombre de cases trop grande 
					//en diagonale et sorte du plateau
					if (placepion[0]<=placepion[1]){
						if (placepion[0] == 1){
							nbcases = 1;
						} else {
							nbcases = (int) (Math.random() * placepion[0]);
							while (nbcases == 0){
								nbcases = (int) (Math.random() * placepion[0]);
							}
						}
						placepion[0] = (placepion[0] - nbcases);
						placepion[1] = (placepion[1] - nbcases);
						place = true;
					} else if (placepion[0]>placepion[1]){
						if (placepion[1] == 1){
							nbcases = 1;
						} else {
							nbcases = (int) (Math.random() * placepion[1]);
							while (nbcases == 0){
								nbcases = (int) (Math.random() * placepion[1]);
							}
						}
						placepion[0] = (placepion[0] - nbcases);
						placepion[1] = (placepion[1] - nbcases);
						place = true;
					}
				}
			}
		}
		
		//suppression de l'emplacement précédent
		for (int i=0; i<plateau.length; i++){
			for (int j=0; j<plateau[i].length; j++){
				if(plateau[i][j]=='o'){
					plateau[i][j] = ' ';
				}
			}
		}
		
		//placement du nouvelle emplacement du pion
		int p1 = placepion[1];
		int p2 = placepion[0];
		plateau[p1][p2]='o';

	}
	
	/**
	* Calcule position gagnante
	* @param plateau le plateau de jeu
	* @return position un tableau en 2d avec les positions gagnantes de tout le plateau
	*/
	int[][] positionGagnante(char [][] plateau){
		int [][] position = new int [plateau.length][2];
		//Initialisation du premier rang, càd (0,0)
		position[0][0] = 0;
		position[0][1] = 0;
		int i = 1;
		int nb = 1;
		boolean limite = false;
		while (i<position.length && !limite){
			if (nb+i < position.length){
				position[i][0] = nb;
				position[i][1] = position[i][0] + i;
				int j=0; 
				while (j<=i){
					if (nb == position[j][0]){
						nb++;
						j=0;
					} 
					if (nb == position[j][1]){
						nb++;
						j=0;
					}
					j++;
				}
			} else {
				limite = true;
			}
			i++;
		}
		
		//suppression des tableaux non utilisés
		boolean nonUtile = false;
		int l = 1;
		for (int k = 1; k<position.length && !nonUtile; k++){
			if (position[k][0] == 0 && position[k][1] == 0){
				nonUtile = true;
			} else {
				l++;
			}
		}
		int [][] positionfinal = new int [l][2];
		position[0][0] = 0;
		position[0][1] = 0;
		for (int j=1; j<l; j++){
			positionfinal[j][0] = position[j][0];
			positionfinal[j][1] = position[j][1];
		}
		return positionfinal;
	}
	
	/**
	* Affiche dans plateau les positions gagnantes
	* @param plateau le plateau de jeu
	*/
 	void afficherPositionGagnante(char [][] plateau){
		int [][] positionGagnante = positionGagnante(plateau);
		int p1;
		int p2;
		for (int i=0; i<positionGagnante.length; i++){
			p1 = positionGagnante[i][0];
			p2 = positionGagnante[i][1];
			plateau[p1][p2] = 'x';
			plateau[p2][p1] = 'x';
		}
	}
	
	/**
	* Verifie si le pion est arrivé à la position (0,0)
	* @param plateau le plateau de jeu
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
	* @param joueurInitial un caractère représentant le joueurp ou l'ordinateur
	* @return si le joueurp est en parametre alors renvoie ordi
	* sinon renvoie joueurp
	*/
	String changeJoueur(String joueurInitial, String joueurp, String ordi){
		String joueurSuivant;
		if (joueurInitial == joueurp){
			joueurSuivant = ordi;
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
		String joueurp = SimpleInput.getString("Entrez le nom du joueur: ");
		String ordi = "ordi";
		String joueur;
		//choix du joueur qui commence
		int choixjoueur= (int) (Math.random() * 2);
		if (choixjoueur == 0){
			joueur = joueurp;
		} else { 
			joueur = ordi;
		}
		//Demande une taille de plateau à l’utilisateur
		int taille = SimpleInput.getInt("Entrez la taille du plateau: ");
		//la taille du tableau ne doit pas dépasser 99 cases 
		//et ne doit pas être en dessous de 2 cases
		while (taille > 99 || taille < 2){
			taille = SimpleInput.getInt("Entrez la taille du plateau: ");
		}
		//Création du jeu
		char[][] plateau = creerPlateau(taille);
		//affichage des positions gagnantes sur le plateau
		afficherPositionGagnante(plateau);
		//sauvegarde des position gagnantes
		positionGagnante(plateau);
		//choix et placement du pion initial
		int [] placepion = {(int) (Math.random() * taille), (int) (Math.random() * taille)};
		//boucle pour que la place du pion ne soit pas une place pour
		//atteindre facilement (0,0),càd sa diagonale, l'abscisse 0 et l'ordonnée 0
		while (placepion[0] == 0 || placepion[1]== 0 || placepion[0] == placepion[1]){
			placepion[0] = (int) (Math.random() * taille);
			placepion[1] = (int) (Math.random() * taille);
		}
		plateau[placepion[1]][placepion[0]]= 'o';
		
		//Boucle de jeu : elle continue tant que le pion n'est pas arrivé à la case (0, 0)
		boolean estarrive = estArrive(placepion);
		while(estarrive==false){
			//Affichage du tableau
			affichePlateau(plateau);
			//le joueur joue si c'est son tour
			if (joueur == joueurp){
				deplacePion(plateau, joueur, placepion);
			//sinon c'est au tour de l'ordinateur
			} else {
				tourOrdi(plateau, joueur, placepion);
			}
			//Changement de joueur
			joueur = changeJoueur(joueur, joueurp, ordi);
			estarrive = estArrive(placepion);
		}
		// Affichage du tableau final
		affichePlateau(plateau);
		// Annonce de fin du jeu et déclaration du gagnant s’il existe
		System.out.println("Fin du jeu ! "+changeJoueur(joueur, joueurp, ordi)+" a gagne ;)");
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
	
	
	//test fonction tourOrdi
	void testtourOrdi(){
		System.out.println();
		System.out.println("*** testtourOrdi");
		
		char [][] plateau = {{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '}};
		String joueur = "ordi";
		
		int[] placepion1 = {2,4};
		int[] result1 = {2,1};
		testcastourOrdi(plateau, joueur, placepion1, result1);
		
		int[] placepion2 = {1,1};
		int[] result2 = {0,0};
		testcastourOrdi(plateau, joueur, placepion2, result2);
		
		int[] placepion3 = {4,2};
		int[] result3 = {1,2};
		testcastourOrdi(plateau, joueur, placepion3, result3);
	}
	
	void testcastourOrdi(char[][] plateau, String joueur, int[] placepion, int[] result){
		//Arrange
		System.out.println("La fonction tourOrdi deplace la position du pion de {"+placepion[0]+","+placepion[1]+"} a {"+result[0]+","+result[1]+"}: ");
		
		//Act
		tourOrdi(plateau, joueur, placepion);
		int [] resExec = placepion;
		
		//Assert
		if (resExec[0] == result[0] && resExec[1] == result[1]){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	
	//testpositionGagnante
	void testpositionGagnante(){
		System.out.println();
		System.out.println("*** testpositionGagnante");
		
		char [][] plateau1 = {{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' '}};
			
		int[][] result1 = {{0,0},{1,2},{3,5}};
		testcaspositionGagnante(plateau1, result1);
		
		char [][] plateau2 = {{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '}};
			
		int[][] result2 = {{0,0},{1,2},{3,5},{4,7},{6,10},{8,13},{9,15},{11,18}};
		testcaspositionGagnante(plateau2, result2);
		
	}
	
	void testcaspositionGagnante(char[][] plateau, int[][] result){
		//Arrange
		System.out.print("La fonction positionGagnante doit renvoyer: ");
		System.out.print("{");
		for (int i=0; i<result.length; i++){
			System.out.print("{");
			for(int j=0; j<result[i].length-1; j++){
				System.out.print(result[i][j]+",");
			}
			System.out.print(result[i][result[i].length-1]);
			System.out.print("}");
			if ((i+1)<result.length){
				System.out.print(",");
			}
		}
		System.out.print("}");
		System.out.println();
		
		//Act
		int[][] resExec = positionGagnante(plateau);

		//Assert
		boolean diff = false;
		for (int k=0; k<resExec.length; k++){
			if (resExec[k][0] != result[k][0] && resExec[k][1] != result[k][1]){
				diff = true;
			}
		}
		if (diff == false){	
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
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
		
		testcaschangeJoueur("Toto", "Toto", "Ordi", "Ordi");
		testcaschangeJoueur("Ordi", "Toto", "Ordi", "Toto");
	}

	void testcaschangeJoueur(String joueurInitial, String joueurp, String Ordi, String result){
		//Arrange
		System.out.print("Le joueur initial etait: "+joueurInitial+" et la fonction doit renvoye: "+result+" : ");
		
		//Act
		String resExec = changeJoueur(joueurInitial, joueurp, Ordi);
		
		//Assert
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
		
	}
}
