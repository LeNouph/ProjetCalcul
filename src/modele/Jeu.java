package modele;

import java.util.*;

import calcul.*;

/** Décrit le déroulement et les règles du jeu.
 */
public class Jeu extends Observable
{
	//
	// Attributs
	//
	
	/** Valeur maximum des nombres proposés au niveau FACILE.*/
	public static final int MAX_FACILE = 20;
	
	/** Valeur maximum des nombres proposés au niveau MOYEN.*/
	public static final int MAX_MOYEN = 50;
	
	/** Valeur maximum des nombres proposés au niveau DIFFICILE.*/
	public static final int MAX_DIFFICILE = 100;
	
	/** Valeur maximum des nombres proposés au niveau EXPERT.*/
	public static final int MAX_EXPERT = 1000;

	/** Valeur initiale du compteur.*/
	public static final int TEMPS_INITIAL = 1;
	
	/** Niveau de la partie.*/
	private Niveau niveau;

	/** Valeur maximum des nombres proposés.*/
	private int valMax;

	/** Générateur de calculs aléatoires.*/
	private GenerateurCalculs generateur;

	/** Liste des calculs donnés au joueur.
	 * Elle pourra être affichée à la fin du jeu avec les réponses :)
	 */
	private ArrayList<Calcul> listeCalculs;

	/** Indice du calcul courant dans la liste des calculs.*/
	private int indiceCourant;

	/** Nombre de bonnes réponses consécutives données par le joueur.*/
	private int bonnesReponses;

	/** Score du joueur.*/
	private int score;


	//
	// Contructeurs
	//

	/** Crée un jeu.
	 * C'est ici qu'on choisit la valeur maximale des nombres en fct° du niveau.
	 * @param niveau le niveau de la partie.
	 */
	public Jeu(Niveau niveau)
	{
		this.niveau = niveau;
		this.valMax = getMax(niveau); // redondant mais pratique
		this.generateur = new GenerateurCalculs(this.valMax);
		this.listeCalculs = new ArrayList<Calcul>();
		this.indiceCourant = 0;
		this.listeCalculs.add(generateur.genererCalcul());
		this.listeCalculs.add(generateur.genererCalcul());
		this.listeCalculs.add(generateur.genererCalcul());
		this.listeCalculs.add(generateur.genererCalcul());
		this.bonnesReponses = 0;
		this.score = 0;
	}


	//
	// Méthodes
	//
	
	/** Renvoie la liste des calculs.*/
	public ArrayList<Calcul> getCalculs()
	{
		return this.listeCalculs;
	}

	/** Renvoie le niveau de la partie.*/
	public Niveau getNiveau()
	{
		return this.niveau;
	}

	/** Renvoie le calcul courant.
	 * @deprecated utiliser plutôt getCalcul(int)
	 */
	@Deprecated
	public Calcul getCalculCourant()
	{
		return this.listeCalculs.get(this.indiceCourant);
	}

	/** Renvoie un calcul d'avance (un des 3 calculs après le calcul courant).
	 * @param indice indice du calcul de réserve, égal à 1 pour le plus proche.
	 * @deprecated utiliser plutôt getCalcul(int).
	 */
	@Deprecated
	public Calcul getCalculAvance(int indice)
	{
		if (indice >= 1 && indice <= 3)
			return this.listeCalculs.get(this.indiceCourant + indice);
		else
			return null;
	}

	/** Renvoie un calcul de la liste.
	 * Le calcul courant s'obtient avec l'indice 0, les calculs d'avance avec 1,
	 * 2 ou 3, et les calculs précédents avec les entiers négatifs.
	 * Si l'indice ne correspond pas à un indice existant dans la liste, la
	 * méthode renvoie 'null'.
	 * @param position indice dans la liste, relativement au calcul courant.
	 */
	public Calcul getCalcul(int position)
	{
		try
		{
			return this.listeCalculs.get(this.indiceCourant + position);
		}
		catch (IndexOutOfBoundsException e)
		{
			return null;
		}
	}

	/** Renvoie le score du joueur.*/
	public int getScore()
	{
		return this.score;
	}
	
	/** Lance le jeu.*/
	public void lancer(Compteur compteur)
	{
		// À discuter : temps restant au départ.
		Thread decompte = new Thread(new CompteARebours(compteur));
		decompte.start();
	}
	
	/** Prend en compte une réponse du joueur.*/
	public void repondre(int reponse, Compteur compteur)
	{
		Calcul calc = this.listeCalculs.get(this.indiceCourant);
		calc.memoriserReponse(reponse);
		if (calc.verifier(reponse))
			bienRepondu(compteur);
		else
			malRepondu();
		// Dans tous les cas on avance dans les calculs
		this.indiceCourant++;
		this.listeCalculs.add(this.generateur.genererCalcul());
		// Et on prévient les observateurs
		super.setChanged();
		super.notifyObservers();
	}

	/** Prend en compte une bonne réponse.*/
	public void bienRepondu(Compteur c)
	{
		this.bonnesReponses++;
		
		// ajouter du temps : 5 secondes / 5 calculs justes
		if (this.bonnesReponses % 5 == 0)
			c.ajouter(5);
		
		// ajouter des points au score.
		if (this.bonnesReponses % 10 == 0)
			this.score += 10;
		else if (this.bonnesReponses % 5 == 0)
			this.score += 5;
		else
			this.score++;
	}

	/** Prend en compte une mauvaise réponse.
	 * Remet à zéro le compteur.
	 */
	public void malRepondu()
	{
		this.bonnesReponses = 0;
	}

	/** Renvoie la valeur max des nombres dans les calculs dans chaque niveau.
	 * @param niveau le niveau dont on souhaite connaître la valeur max.
	 */
	public static int getMax(Niveau niveau)
	{
		switch (niveau)
		{
			case FACILE:
				return MAX_FACILE;
			case MOYEN:
			default:
				return MAX_MOYEN;
			case DIFFICILE:
				return MAX_DIFFICILE;
			case EXPERT:
				return MAX_EXPERT;
			//case PERSONNALISE:
			// XXX À voir en détail plus tard
		}
	}
}
