package calcul;

import java.util.*;

/** Génère des calculs mentaux en nombres entiers.
 */
public class GenerateurCalculs
{
	//
	// Attributs
	//

	/** Valeur maximale des opérandes.*/
	int valMax;


	//
	// Constructeurs
	//

	/** Construit un générateur de calculs.
	 * @param valMax valeur maximale des opérandes souhaitée.
	 */
	public GenerateurCalculs(int valMax)
	{
		this.valMax = valMax;
	}


	//
	// Méthodes
	//

	/** Génère un calcul aléatoirement.
	 * Les probabilités d'apparition des opérations sont 4/10 pour l'addition
	 * et la soustraction, et 1/10 pour la multiplication et la division, et
	 * pas une distribution uniforme, pour ne pas obtenir trop de '0' ou de '1'
	 * dans les calculs.
	 * @return calcul arithmétique aléatoirement choisi entre +, -, x et ÷
	 */
	public Calcul genererCalcul()
	{
		// nombre entre 1 et 4
		int alea = (int) Math.floor(10 * Math.random());

		Calcul calc;
		if (alea < 4) // 4 cas sur 10
			calc = genererAddition();
		else if (alea < 8) // 4 cas sur 10
			calc = genererSoustraction();
		else if (alea < 9) // 1 cas sur 10
			calc = genererMultiplication();
		else // 1 cas sur 10
			calc = genererDivision();
		
		return calc;
	}

	/** Génère une addition.
	 * @return addition à résoudre.
	 */
	public Calcul genererAddition()
	{
		// nombre entre 0 et valMax
		int opG = (int) Math.floor((this.valMax + 1) * Math.random());
		// nombre entre 0 et (valMax-opG)
		int opD = (int) Math.floor((valMax - opG + 1) * Math.random());

		int res = opG + opD;

		return new Calcul(opG, opD, res, Operateur.ADDITION);
	}

	/** Génère une soustraction.
	 * @return soustraction à résoudre.
	 */
	public Calcul genererSoustraction()
	{
		// nombre entre 0 et valMax
		int opG = (int) Math.floor((this.valMax + 1) * Math.random());
		// nombre entre 0 et opG
		int opD = (int) Math.floor((opG + 1) * Math.random());

		int res = opG - opD;

		return new Calcul(opG, opD, res, Operateur.SOUSTRACTION);
	}

	/** Génère une multiplication.
	 * @return multiplication à résoudre.
	 */
	public Calcul genererMultiplication()
	{
		// nombre entre 0 et valMax
		int opG = (int) Math.floor((this.valMax + 1) * Math.random());
		// nombre entre 0 et floor(valMax/opG)
		// attention aux divisions par zéro !
		int opD;
		if (opG != 0)
			opD = (int)Math.floor((Math.floor(this.valMax/opG)+1)*Math.random());
		else
			opD = (int) Math.floor((this.valMax + 1) * Math.random());

		int res = opG * opD;

		return new Calcul(opG, opD, res, Operateur.MULTIPLICATION);
	}

	/** Génère une division.
	 * @return division à résoudre.
	 */
	public Calcul genererDivision()
	{
		// astuce pour ne pas avoir de résultats non entiers :
		// on crée une multiplication et on l'inverse =)

		// Seul souci : faire gaffe aux divisions par zéro !
		Calcul mult = new Calcul(0,0,0,Operateur.MULTIPLICATION);
		while (mult.getOpGauche() == 0)
		{
			mult = genererMultiplication();
		}
		int opG = mult.getResultat();
		int opD = mult.getOpGauche();
		int res = mult.getOpDroit();
		return new Calcul(opG, opD, res, Operateur.DIVISION);
	}
}
