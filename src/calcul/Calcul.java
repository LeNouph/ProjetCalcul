package calcul;

import java.util.*;

/** Modélise un calcul mental avec sa réponse attendue et sa réponse donnée.
 */
public class Calcul
{
	//
	// Attributs
	//

	/** Opérande gauche du calcul.*/
	private int opGauche;
	
	/** Opérande droit du calcul.*/
	private int opDroit;
	
	/** Résultat du calcul.*/
	private int resultat;

	/** Réponse donnée au calcul.*/
	private int reponse;

	/** Opérateur du calcul.*/
	private Operateur operateur;


	//
	// Constructeurs
	//

	/** Crée un calcul.
	 * Un calcul n'est pas nécessairement mathématiquement juste, on devra s'en
	 * assurer lors de sa création si c'est ce que l'on souhaite.
	 * @param opG l'opérande gauche du calcul.
	 * @param opD l'opérande droit du calcul.
	 * @param res le résultat du calcul.
	 * @param op l'opérateur du calcul.
	 */
	public Calcul(int opG, int opD, int res, Operateur op)
	{
		this.opGauche = opG;
		this.opDroit = opD;
		this.resultat = res;
		this.operateur = op;
	}


	//
	// Méthodes
	//

	/** Renvoie l'opérande gauche du calcul.*/
	public int getOpGauche()
	{
		return this.opGauche;
	}

	/** Renvoie l'opérande droit du calcul.*/
	public int getOpDroit()
	{
		return this.opDroit;
	}

	/** Renvoie le résultat du calcul.*/
	public int getResultat()
	{
		return this.resultat;
	}

	/** Renvoie la réponse qui a été donnée au calcul.*/
	public int getReponse()
	{
		return this.reponse;
	}

	/** Vérifie une réponse au calcul.
	 * @param reponse la réponse donné que l'on doit vérifier.
	 * @return booléen qui vaut true si et seulement si la réponse est juste.
	 */
	public boolean verifier(int reponse)
	{
		return (reponse == this.resultat);
	}

	/** Vérifie si un calcul est juste.
	 * Si le calcul n'a pas encore de réponse, la méthode renvoie 'false'.
	 */
	public boolean estJuste()
	{
		return (this.reponse == this.resultat);
	}

	/** Donne une réponse au calcul.*/
	public void memoriserReponse(int rep)
	{
		this.reponse = rep;
	}

	/** Renvoie une chaîne de caractères décrivant le calcul sans son résultat.
	 */
	public String toString()
	{
		String descr = this.opGauche + " " + this.operateur + " ";
		descr = descr + this.opDroit + " = ";
		return descr;
	}
}
