package modele;

import java.util.*;
import java.io.*;

/** Représente une entrée dans une table des scores.
 */
public class Score implements Comparable<Score>, Serializable
{
	//
	// Attributs
	//

	/** Numéro de sérialisation.*/
	private static final long serialVersionUID = 1;

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

	/** Renvoie une description du score.*/
	public String toString()
	{
		return ("" + this.nomJoueur + " " + this.points);
	}
}

