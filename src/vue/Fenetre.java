package vue;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import modele.*;

/** Fenêtre de test du compte à rebours.
 */
public class Fenetre extends JFrame
{
	//
	// Attributs
	//

	/** La couleur bleu.*/
	public static final Color VERT = new Color(48, 145, 0, 255);

	/** La couleur bleu.*/
	public static final Color JAUNE = Color.ORANGE;

	/** La couleur bleu.*/
	public static final Color ORANGE = new Color(249, 137, 0, 255);

	/** La couleur bleu.*/
	public static final Color ROUGE = Color.RED;

	/** La couleur bleu.*/
	public static final Color BLEU = new Color(22, 61, 201, 255);


	//
	// Constructeurs
	//

	/** Construit la fenêtre.*/
	public Fenetre()
	{
		this.setTitle("Ceci n'est pas un jeu");
		this.getContentPane().add(new Menu(this));
	}


	//
	// Méthodes
	//

	/** Rend la fenêtre visible.*/
	public void afficher()
	{
		super.setSize(new Dimension(500, 600));
		super.setResizable(false);
		//this.pack();
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/** Affiche le menu dans la fenêtre.*/
	public void afficherMenu()
	{
		this.getContentPane().removeAll();
		Menu menu = new Menu(this);
		this.getContentPane().add(menu);
		menu.revalidate();
		menu.placerFocus();
	}

	/** Affiche le menu des niveaux dans la fenêtre.*/
	public void afficherMenuNiveau()
	{
		this.getContentPane().removeAll();
		MenuNiveau menuNiveau = new MenuNiveau(this);
		this.getContentPane().add(menuNiveau);
		menuNiveau.revalidate();
		menuNiveau.placerFocus();
	}

	/** Affiche l'écran de jeu dans la fenêtre.*/
	public void afficherNouveauJeu(Niveau niveau)
	{
		this.getContentPane().removeAll();
		EcranDeJeu ecranDeJeu = new EcranDeJeu(this, niveau);
		this.getContentPane().add(ecranDeJeu);
		ecranDeJeu.revalidate();
		ecranDeJeu.placerFocus();
	}

	/** Affiche l'écran de fin de partie.
	 * @param jeu le jeu qui vient de se terminer.
	 */
	public void afficherFinish(Jeu jeu)
	{
		this.getContentPane().removeAll();
		EcranFinish ecranFinish = new EcranFinish(this, jeu);
		this.getContentPane().add(ecranFinish);
		ecranFinish.revalidate();
		ecranFinish.placerFocus();
	}

	/** Affiche l'écran des meilleurs scores.
	 */
	public void afficherScores()
	{
		// XXX à compléter
	}

	/** Quitte la fenetre.
	 */
	public void quitter()
	{
		this.dispose();
	}
}
