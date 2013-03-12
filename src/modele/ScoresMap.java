package modele;

import java.util.*;
import java.io.*;

/** Classe qui retient les meilleurs scores de chaque niveau.
 */
public class ScoresMap implements Serializable
{
	//
	// Attributs
	//

	/** Numéro de sérialisation.*/
	private static final long serialVersionUID = 1;

	/** Mémorise la table des scores de chaque niveau.*/
	private TreeMap<Niveau,Scores> mapScores;


	//
	// Constructeur
	//

	/** Construit la map des scores.*/
	public ScoresMap()
	{
		this.mapScores = new TreeMap<Niveau,Scores>();
	}


	//
	// Méthodes
	//

	/** Vérifie si un score donné peut être enregistré pour un niveau donné.
	 * @param score le nombre de points faits par le joueur.
	 * @param niveau le niveau auquel le joueur a joué.
	 * @return booléen valant 'true' si et seulement si le score peut être
	 * enregistré parmi les meilleurs scores de son niveau.
	 */
	public boolean accepte(int score, Niveau niveau)
	{
		if (this.mapScores.containsKey(niveau))
			return (this.mapScores.get(niveau).acceptent(score));
		else
			return true;
	}

	/** Ajoute un score donné à un niveau donné.
	 * @param score le score à enregistrer.
	 * @param nom le nom du joueur voulant enregistrer son score.
	 * @param niveau le niveau pour lequel on veut enregistrer un score.
	 */
	public void enregistrer(int score, String nom, Niveau niveau)
	{
		if (this.accepte(score, niveau))
		{
			if (this.mapScores.containsKey(niveau))
				this.mapScores.get(niveau).enregistrer(nom, score);
			else
			{
				Scores table = new Scores();
				table.enregistrer(nom, score);
				this.mapScores.put(niveau,table);
			}
		}
	}

		
	//
	// Classes internes
	//

	/** Modélise les meilleurs scores pour un niveau donné.
	 */
	public class Scores
	{
		//
		// Attributs
		//

		/** Nb de meilleurs scores enregistrés.*/
		private final int NB_SCORES = 10;

		/** Liste des meilleurs scores enregistrés.
		 * La liste est triée du meilleur score au pire, le premier élément étant
		 * donc le meilleur (accessible avec getFirst())
		 */
		private LinkedList<Score> listeScores;


		//
		// Constructeur
		//

		/** Crée une table des meilleurs scores.*/
		public Scores()
		{
			this.listeScores = new LinkedList<Score>();
		}


		//
		// Méthodes
		//

		/** Vérifie si un score mérite d'être ajouté aux meilleurs scores.
		 * @param score nombre de points faits par un joueur.
		 * @return booléen valant 'true' si et seulement si le score proposé peut
		 * être inscrit aux meilleurs scores.
		 */
		public boolean acceptent(int score)
		{
			if (this.listeScores.size() > 0)
				return (score > this.listeScores.getLast().getPoints());
			else
				return true;
		}

		/** Ajoute un score aux meilleurs scores.
		 * @param nom le nom du joueur.
		 * @param score le nombre de points faits par le joueur.
		 */
		public void enregistrer(String nom, int score)
		{
			if (this.acceptent(score))
			{
				if (this.listeScores.size() == this.NB_SCORES)
					this.listeScores.removeLast();

				this.listeScores.add(new Score(nom, score));
				Collections.sort(this.listeScores);
			}
		}
	}
			
	/** Représente une entrée dans une table des scores.
	 */
	private class Score implements Comparable<Score>
	{
		//
		// Attriibuts
		//

		/** Le nom du joueur ayant fait le score.*/
		private String nomJoueur;

		/** Le nombre de points que le joueur a fait.*/
		private int points;


		//
		// Constructeur
		//

		/** Crée un objet Score.*/
		public Score(String nom, int points)
		{
			this.nomJoueur = nom;
			this.points = points;
		}


		//
		// Méthodes
		//

		/** Renvoie le nom du joueur.*/
		public String getNom()
		{
			return this.nomJoueur;
		}

		/** Renvoie les points du joueur.*/
		public int getPoints()
		{
			return this.points;
		}

		/** Compare deux objets de type Score.*/
		@Override
		public int compareTo(Score s)
		{
			// On compare d'abord les scores, et, s'ils sont égaux, les noms
			int diffScores = ((Integer)(this.getPoints())).compareTo(s.getPoints());
			if (diffScores != 0)
				// /!\ compareTo() trie les entiers du + petit au + grand,
				// on veut l'inverse, d'où le "-"
				return -diffScores;
			else
				return this.getNom().compareTo(s.getNom());
		}
	}
}
