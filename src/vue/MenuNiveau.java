package vue;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import modele.*;

/** Menu de choix du niveau.
 */
public class MenuNiveau extends JPanel
{
	//
	// Attributs
	//

	/** Fenetre dans laquelle est affiché le menu.
	 */
	private Fenetre fenetre;

	/** Bouton qui obtiendra le focus en premier.*/
	private JButton boutonFocus;

	/** Bouton qui lance le jeu au niveau facile.*/
	private JButton boutonFacile;

	/** Bouton qui lance le jeu au niveau moyen.*/
	private JButton boutonMoyen;
	
	/** Bouton qui lance le jeu au niveau difficile.*/
	private JButton boutonDifficile;
	
	/** Bouton qui lance le jeu au niveau expert.*/
	private JButton boutonExpert;

	//
	// Constructeurs
	//

	/** Construit le menu.*/
	public MenuNiveau(Fenetre fenetre)
	{
		this.fenetre = fenetre;

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		/* Titre */
		JLabel titre = new JLabel();
		titre.setAlignmentX(Component.CENTER_ALIGNMENT);
		titre.setText("Choisissez votre niveau");
		titre.setForeground(Color.BLUE);
		titre.setFont(new Font(null, Font.BOLD, 20));

		/* Boutons */
		boutonFacile = new JButton("Facile");
		boutonFacile.setAlignmentX(Component.CENTER_ALIGNMENT);
		boutonFacile.addActionListener(new LancerJeu());
		boutonFacile.addKeyListener(new ValiderBouton());
		this.boutonFocus = boutonFacile;

		boutonMoyen = new JButton("Moyen");
		boutonMoyen.setAlignmentX(Component.CENTER_ALIGNMENT);
		boutonMoyen.addActionListener(new LancerJeu());
		boutonMoyen.addKeyListener(new ValiderBouton());

		boutonDifficile = new JButton("Difficile");
		boutonDifficile.setAlignmentX(Component.CENTER_ALIGNMENT);
		boutonDifficile.addActionListener(new LancerJeu());
		boutonDifficile.addKeyListener(new ValiderBouton());

		boutonExpert = new JButton("Expert");
		boutonExpert.setAlignmentX(Component.CENTER_ALIGNMENT);
		boutonExpert.addActionListener(new LancerJeu());
		boutonExpert.addKeyListener(new ValiderBouton());

		// XXX Bientôt : niveau personnalisé...

		JButton boutonRetour = new JButton("Retour");
		boutonRetour.setAlignmentX(Component.CENTER_ALIGNMENT);
		boutonRetour.addActionListener(new ActionRetour());
		boutonRetour.addKeyListener(new ValiderBouton());

		/* Et on ajoute tous les éléments, avec des marges... */
		this.add(Box.createRigidArea(new Dimension(0,80)));
		this.add(titre);
		this.add(Box.createRigidArea(new Dimension(0,80)));
		int marge = 60;
		this.add(boutonFacile);
		this.add(Box.createRigidArea(new Dimension(0,marge)));
		this.add(boutonMoyen);
		this.add(Box.createRigidArea(new Dimension(0,marge)));
		this.add(boutonDifficile);
		this.add(Box.createRigidArea(new Dimension(0,marge)));
		this.add(boutonExpert);
		this.add(Box.createRigidArea(new Dimension(0, marge)));
		this.add(boutonRetour);
	}


	//
	// Méthodes
	//

	/** Place le focus sur un composant, pour l'ergonomie.*/
	public void placerFocus()
	{
		boutonFocus.requestFocusInWindow();
	}


	//
	// Listeners
	//

	/** Lance le jeu.
	 * On détermine le niveau à lancer en fonction du bouton appelant.*/
	public class LancerJeu implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == boutonFacile)
			{
				fenetre.afficherNouveauJeu(Niveau.FACILE);
			}
			else if (e.getSource() == boutonMoyen)
			{
				fenetre.afficherNouveauJeu(Niveau.MOYEN);
			}
			else if (e.getSource() == boutonDifficile)
			{
				fenetre.afficherNouveauJeu(Niveau.DIFFICILE);
			}
			else if (e.getSource() == boutonExpert)
			{
				fenetre.afficherNouveauJeu(Niveau.EXPERT);
			}
		}
	}

	/** Effectue un retour vers le menu principal.*/
	public class ActionRetour implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			fenetre.afficherMenu();
		}
	}

	/** Valide un bouton grâce à la touche entrée.
	 * Un KeyEvent est envoyé uniquement par un composant ayant le focus.
	 */
	private class ValiderBouton implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				((JButton)(e.getSource())).doClick();
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
}
