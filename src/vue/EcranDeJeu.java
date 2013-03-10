package vue;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import modele.*;
import calcul.*;

/** Contenu de la fenêtre quand le jeu est lancé.
 */
public class EcranDeJeu extends JPanel
{
	//
	// Attributs
	//
	
	/** Fenêtre dans laquelle est affiché le jeu.
	 * Conservée pour pouvoir faire passer la fenêtre de l'état "jeu"
	 * à l'état "menu" grâce à une de ses procédures.
	 */
	private Fenetre fenetre;

	/** Modèle du jeu utilisé.*/
	private Jeu jeu;

	/** Mémorise le compteur à afficher.*/
	private Compteur compteur;

	/** Zone de texte indiquant le temps restant.*/
	private JLabel affichageTemps;

	/** Zone de texte indiquant le score du joueur.*/
	private JLabel affichageScore;

	/** Zone d'affichage des calculs précédents.*/
	private JPanel affichageCalculsPrecedents;

	/** Zone d'affichage du calcul précédent le plus récent.
	 * XXX Un jour faudrait faire une liste, quand même...*/
	private JLabel calculPrecedent1;

	/** Zone d'affichage du 2e calcul précédent.*/
	private JLabel calculPrecedent2;

	/** Zone d'affichage du 3e calcul précédent.*/
	private JLabel calculPrecedent3;

	/** Zone d'affichage du calcul courant.*/
	private JLabel calculCourant;

	/** Zone d'affichage du 1er calcul en avance.*/
	private JLabel calculAvance1;

	/** Zone d'affichage du 2e calcul en avance.*/
	private JLabel calculAvance2;

	/** Zone d'affichage du 3e calcul en avance.*/
	private JLabel calculAvance3;

	/** Champ de texte permettant d'ajouter du temps.*/
	private JTextField champReponse;

	/** Bouton qui valide le champ ajout.*/
	private JButton boutonRepondre;

	/** Bouton pour arrêter de jouer.*/
	private JButton boutonArreter;


	//
	// Constructeurs
	//

	/** Construit l'écran de jeu du programme.
	 * @param fenetre la fenêtre dans laquelle est affiché le jeu.
	 * @param max la valeur maximum des nombres impliqués dans les calculs.*/
	public EcranDeJeu(Fenetre fenetre, Niveau niveau)
	{
		this.fenetre = fenetre;
		this.compteur = new Compteur(Jeu.TEMPS_INITIAL);
		this.compteur.addObserver(new MajCompteur());
		this.jeu = new Jeu(niveau);
		this.jeu.addObserver(new MajJeu());

		this.setLayout(new BorderLayout());

		//
		// ------ Zone des indicateurs (en haut à droite) ------
		//

		JPanel zoneIndicateurs = new JPanel();
		zoneIndicateurs.setBackground(Color.LIGHT_GRAY);
		BoxLayout b = new BoxLayout(zoneIndicateurs, BoxLayout.PAGE_AXIS);
		zoneIndicateurs.setLayout(b);

		/* Affichage du temps restant, à placer dans un coin. */
		JPanel zoneTemps = new JPanel();
		zoneTemps.setBackground(Color.LIGHT_GRAY);
		zoneTemps.setLayout(new BoxLayout(zoneTemps, BoxLayout.PAGE_AXIS));
		JLabel titreTemps = new JLabel("Temps restant :");
		titreTemps.setAlignmentX(Component.CENTER_ALIGNMENT);
		affichageTemps = new JLabel("" + compteur.getTemps());
		affichageTemps.setFont(new Font(null, Font.BOLD, 30));
		affichageTemps.setAlignmentX(Component.CENTER_ALIGNMENT);
		affichageTemps.setForeground(Fenetre.VERT);
		zoneTemps.add(titreTemps);
		zoneTemps.add(affichageTemps);

		zoneIndicateurs.add(Box.createRigidArea(new Dimension(0,30)));
		zoneIndicateurs.add(zoneTemps, BorderLayout.EAST);
		zoneIndicateurs.add(Box.createRigidArea(new Dimension(0,20)));

		/* Affichage du score. */
		JPanel zoneScore = new JPanel();
		zoneScore.setBackground(Color.LIGHT_GRAY);
		zoneScore.setLayout(new BoxLayout(zoneScore, BoxLayout.PAGE_AXIS));
		JLabel titreScore = new JLabel("Score:");
		titreScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		affichageScore = new JLabel("" + jeu.getScore());
		affichageScore.setFont(new Font(null, Font.BOLD, 20));
		affichageScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		zoneScore.add(titreScore);
		zoneScore.add(affichageScore);

		zoneIndicateurs.add(zoneScore, BorderLayout.EAST);
		
		/* Bouton pour arrêter le jeu. */
		JButton boutonArreter = new JButton("Arrêter");
		boutonArreter.addActionListener(new ActionArreter());
		boutonArreter.addKeyListener(new ActionValider());
		boutonArreter.setAlignmentX(Component.CENTER_ALIGNMENT);
		zoneIndicateurs.add(Box.createRigidArea(new Dimension(0,400)));
		zoneIndicateurs.add(boutonArreter);

		this.add(zoneIndicateurs, BorderLayout.EAST);

		//
		// --------- Zone des calculs (au centre) --------
		//
		
		JPanel zoneCalculs = new JPanel();
		BoxLayout box = new BoxLayout(zoneCalculs, BoxLayout.PAGE_AXIS);
		zoneCalculs.setLayout(box);
		Font policeCalculs = new Font(null, Font.PLAIN, 24);

		/* Affichage des calculs déjà effectués. */
		affichageCalculsPrecedents = new JPanel();
		BoxLayout blbl = new BoxLayout(affichageCalculsPrecedents, BoxLayout.PAGE_AXIS);
		affichageCalculsPrecedents.setLayout(blbl);

		calculPrecedent3 = new JLabel("...");
		calculPrecedent3.setAlignmentX(Component.CENTER_ALIGNMENT);
		calculPrecedent3.setFont(policeCalculs);
		calculPrecedent2 = new JLabel("...");
		calculPrecedent2.setAlignmentX(Component.CENTER_ALIGNMENT);
		calculPrecedent2.setFont(policeCalculs);
		calculPrecedent1 = new JLabel("...");
		calculPrecedent1.setAlignmentX(Component.CENTER_ALIGNMENT);
		calculPrecedent1.setFont(policeCalculs);

		affichageCalculsPrecedents.add(Box.createRigidArea(new Dimension(0,80)));
		affichageCalculsPrecedents.add(calculPrecedent3);
		int marge = 30;
		affichageCalculsPrecedents.add(Box.createRigidArea(new Dimension(0,marge)));
		affichageCalculsPrecedents.add(calculPrecedent2);
		affichageCalculsPrecedents.add(Box.createRigidArea(new Dimension(0,marge)));
		affichageCalculsPrecedents.add(calculPrecedent1);
		affichageCalculsPrecedents.add(Box.createRigidArea(new Dimension(0,marge)));
		zoneCalculs.add(affichageCalculsPrecedents);

		/* Affichage du calcul à effectuer. */
		JPanel affichageCalculCourant = new JPanel();
		affichageCalculCourant.setBackground(Color.DARK_GRAY);

		// Calcul en lui-même
		calculCourant = new JLabel("" + this.jeu.getCalcul(0));
		calculCourant.setForeground(Color.ORANGE);
		calculCourant.setFont(new Font(null, Font.BOLD, 30));
		calculCourant.setAlignmentX(Component.CENTER_ALIGNMENT);
		affichageCalculCourant.add(calculCourant);
		// Zone de réponse après le calcul
		champReponse = new JTextField(5);
		champReponse.addKeyListener(new ActionValider());
		boutonRepondre = new JButton("Valider");
		boutonRepondre.addActionListener(new ActionRepondre());
		boutonRepondre.addKeyListener(new ActionValider());
		affichageCalculCourant.add(champReponse);
		affichageCalculCourant.add(boutonRepondre);

		zoneCalculs.add(affichageCalculCourant);

		/* Affichage des calculs d'avance. */
		JPanel affichageCalculsSuivants = new JPanel();
		BoxLayout bl = new BoxLayout(affichageCalculsSuivants, BoxLayout.PAGE_AXIS);
		affichageCalculsSuivants.setLayout(bl);
		affichageCalculsSuivants.setFont(policeCalculs);

		calculAvance1 = new JLabel("" + this.jeu.getCalcul(1) + " ?");
		calculAvance1.setAlignmentX(Component.CENTER_ALIGNMENT);
		calculAvance1.setFont(policeCalculs);
		calculAvance2 = new JLabel("" + this.jeu.getCalcul(2) + " ?");
		calculAvance2.setAlignmentX(Component.CENTER_ALIGNMENT);
		calculAvance2.setFont(policeCalculs);
		calculAvance3 = new JLabel("" + this.jeu.getCalcul(3) + " ?");
		calculAvance3.setAlignmentX(Component.CENTER_ALIGNMENT);
		calculAvance3.setFont(policeCalculs);

		affichageCalculsSuivants.add(Box.createRigidArea(new Dimension(0,marge)));
		affichageCalculsSuivants.add(calculAvance1);
		affichageCalculsSuivants.add(Box.createRigidArea(new Dimension(0,marge)));
		affichageCalculsSuivants.add(calculAvance2);
		affichageCalculsSuivants.add(Box.createRigidArea(new Dimension(0,marge)));
		affichageCalculsSuivants.add(calculAvance3);
		affichageCalculsSuivants.add(Box.createRigidArea(new Dimension(0,200)));

		zoneCalculs.add(affichageCalculsSuivants);

		this.add(zoneCalculs, BorderLayout.CENTER);

		/* Et on lance le compte à rebours. */
		jeu.lancer(this.compteur);
	}


	//
	// Méthodes
	//

	/** Place le focus sur un composant, pour l'ergonomie.*/
	public void placerFocus()
	{
		this.champReponse.requestFocusInWindow();
	}


	//
	// Listeners
	//

	/** Valide la réponse grâce à la touche entrée.
	 * Un KeyEvent est envoyé uniquement par un composant ayant le focus.
	 */
	private class ActionValider implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if (e.getSource() instanceof JButton)
					((JButton)(e.getSource())).doClick();
				else if (e.getSource() == champReponse)
					boutonRepondre.doClick();
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
			// Utile pour les combinaisons de touches CTRL+..., ALT+... etc.
		}
	}

	/** Ecoute le bouton qui donne une réponse au calcul.*/
	private class ActionRepondre implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			String chaineReponse = champReponse.getText();
			try {
				int reponse = Integer.parseInt(chaineReponse);
				jeu.repondre(reponse, compteur);
			} catch (NumberFormatException ex) {
				System.out.print("Attention : \""+chaineReponse+"\"");
				System.out.println(" n'est pas un nombre entier !");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			champReponse.setText("");
			champReponse.requestFocusInWindow();
		}
	}

	/** Ecoute le bouton arrêter.*/
	private class ActionArreter implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// XXX demander confirmation ?
			fenetre.afficherMenu();
		}
	}


	//
	// Observateurs
	//

	/** Observe les changements du compteur.
	 * Prévient l'interface graphique des changement de valeur du compteur.
	 */
	private class MajCompteur implements Observer
	{
		@Override
		public void update(Observable c, Object arg)
		{
			Compteur compteur = (Compteur)c;

			// On affiche le nouveau temps, en couleur
			affichageTemps.setText("" + compteur.getTemps());
			int total = Jeu.TEMPS_INITIAL;
			if (compteur.getTemps() > total*3/4)
				affichageTemps.setForeground(Fenetre.VERT);
			else if (compteur.getTemps() > total*1/2)
				affichageTemps.setForeground(Fenetre.JAUNE);
			else if (compteur.getTemps() > total*1/4)
				affichageTemps.setForeground(Fenetre.ORANGE);
			else
				affichageTemps.setForeground(Fenetre.ROUGE);
			
			// Si on est arrivé à zéro, on arrête le jeu
			if (compteur.getTemps() == 0)
			{
				// On désactive le champ de réponse
				champReponse.setEnabled(false);
				boutonRepondre.setEnabled(false);
				// Et on affiche l'écran de fin
				fenetre.afficherFinish(jeu);
			}
		}
	}

	/** Observe la progression du jeu.
	 * Actualise l'affichage des calculs et du score, ce qui est possible en un
	 * seul coup du fait que les modifications n'arrivent que lors d'une réponse
	 * du joueur.
	 */
	private class MajJeu implements Observer
	{
		@Override
		public void update(Observable j, Object arg)
		{
			Jeu jeu = (Jeu)j;

			// Actualiser le calcul courant et les calculs d'avance
			calculCourant.setText("" + jeu.getCalcul(0));
			calculAvance1.setText(jeu.getCalcul(1) + " ?");
			calculAvance2.setText(jeu.getCalcul(2) + " ?");
			calculAvance3.setText(jeu.getCalcul(3) + " ?");

			// Actualiser les calculs d'avance
			// C'est pénible parce que c'est pas une liste de calculs...
			if (jeu.getCalcul(-1) != null) // calcul le plus récent
			{
				Calcul c1 = jeu.getCalcul(-1);
				calculPrecedent1.setText("" + c1 + c1.getReponse());
				// couleur selon qu'il est juste ou faux
				if (c1.estJuste())
					calculPrecedent1.setForeground(Fenetre.VERT);
				else
					calculPrecedent1.setForeground(Fenetre.ROUGE);

				if (jeu.getCalcul(-2) != null) // 2e calcul précédent
				{
					Calcul c2 = jeu.getCalcul(-2);
					calculPrecedent2.setText("" + c2 + c2.getReponse());
					// couleur selon qu'il est juste ou faux
					if (c2.estJuste())
						calculPrecedent2.setForeground(Fenetre.VERT);
					else
						calculPrecedent2.setForeground(Fenetre.ROUGE);

					if (jeu.getCalcul(-3) != null) // 3e calcul précédent
					{
						Calcul c3 = jeu.getCalcul(-3);
						calculPrecedent3.setText("" + c3 + c3.getReponse());
						// couleur selon qu'il est juste ou faux
						if (c3.estJuste())
							calculPrecedent3.setForeground(Fenetre.VERT);
						else
							calculPrecedent3.setForeground(Fenetre.ROUGE);
					}
				}
			}

			// Actualiser le score
			affichageScore.setText("" + jeu.getScore());
		}
	}
}
