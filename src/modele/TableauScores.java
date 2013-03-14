package modele;

import java.util.*;
import java.io.*;

/** Classe qui retient les meilleurs scores de chaque niveau.
 */
public class TableauScores implements Serializable
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
	public TableauScores()
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
		{
			System.out.println("Ce niveau possède déjà des scores.");
			return (this.mapScores.get(niveau).acceptent(score));
		}
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

	/** Renvoie une description du tableau de scores;*/
	public String toString()
	{
		return this.mapScores.toString();
	}
}
