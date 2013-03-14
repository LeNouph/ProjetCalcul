package modele;

import java.util.*;
import java.io.*;

/** Modélise les meilleurs scores pour un niveau donné.
 */
public class Scores implements Serializable
{
	//
	// Attributs
	//

	/** Numéro de sérialisation.*/
	private static final long serialVersionUID = 1;

	/** Nb de meilleurs scores enregistrés.*/
	public static final int NB_SCORES = 10;

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
		// Si la liste est pleine, on regarde si le score est meilleur 
		// que le dernier de la liste
		if (this.listeScores.size() == NB_SCORES)
			return (score > this.listeScores.getLast().getPoints());
		else // Sinon osef y a de la place
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
			if (this.listeScores.size() == NB_SCORES)
				this.listeScores.removeLast();

			this.listeScores.add(new Score(nom, score));
			Collections.sort(this.listeScores);
		}
	}

	/** Renvoie une description des scores.*/
	public String toString()
	{
		return this.listeScores.toString();
	}
}
