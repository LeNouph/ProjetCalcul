package modele;

import java.util.*;

/** Compteur du temps restant au joueur.
 */
public class Compteur extends Observable
{
	//
	// Attributs
	//

	/** Temps restant, en secondes.
	 * Doit rester positif.
	 */
	private int temps;


	//
	// Constructeur
	//

	/** Crée un compteur.
	 */
	public Compteur(int tempsInitial)
	{
		this.temps = tempsInitial;
		super.setChanged();
		super.notifyObservers();
	}


	//
	// Méthodes
	//

	/** Renvoie le temps restant du compteur.
	 */
	public int getTemps()
	{
		return this.temps;
	}

	/** Ajoute un nombre de secondes au compteur.
	 */
	public synchronized void ajouter(int delai)
	{
		if (delai > -this.temps)
		{
			this.temps += delai;
			super.setChanged();
			super.notifyObservers();
		}
		else
		{
			this.temps = 0;
			super.setChanged();
			super.notifyObservers();
		}
	}

	/** Fait descendre le compteur d'une seconde.
	 * Le temps restant ne descend réellement que si le compteur n'est pas déjà
	 * à zéro.
	 */
	public synchronized void decrementer()
	{
		if (this.temps > 0)
		{
			this.temps--;
			super.setChanged();
			super.notifyObservers();
		}
	}

	/** Vérifie si le compteur est nul ou pas.
	 * Renvoie true si et seulement si le compteur n'est pas à zéro.
	 */
	public boolean estNonNul()
	{
		return this.temps != 0;
	}
}
