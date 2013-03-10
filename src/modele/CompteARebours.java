package modele;

/** Effectue le compte à rebours du compteur dans un thread dédié.
 */
public class CompteARebours implements Runnable
{
	/** Mémorise le compteur dont on s'occupe.
	 */
	public Compteur compteur;

	/** Crée le compte à rebours.
	 */
	public CompteARebours(Compteur c)
	{
		this.compteur = c;
	}

	/** Lance le compte à rebours.
	 */
	@Override
	public void run()
	{
		// Renvoyer à une méthode plus intelligente, par la suite. (sûr ?)
		while (this.compteur.estNonNul())
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.compteur.decrementer();
		}
	}
}
