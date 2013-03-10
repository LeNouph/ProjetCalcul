package vue;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import modele.*;

/** Menu principal du jeu.
 */
public class Menu extends JPanel
{
	//
	// Attributs
	//

	/** Fenetre dans laquelle est affiché le menu.
	 * Conservée pour pouvoir faire passer la fenêtre de l'état "menu"
	 * à l'état "jeu" grâce à une de ses procédures.
	 */
	private Fenetre fenetre;

	/** Bouton qui obtiendra le focus en premier.*/
	private JButton boutonFocus;


	//
	// Constructeurs
	//

	/** Construit le menu.*/
	public Menu(Fenetre fenetre)
	{
		this.fenetre = fenetre;

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		/* Titre */
		JLabel titre = new JLabel();
		titre.setAlignmentX(Component.CENTER_ALIGNMENT);
		titre.setText("Ceci n'est pas un jeu");
		titre.setForeground(Color.BLUE);
		titre.setFont(new Font(null, Font.BOLD, 40));
		
		/* Boutons */
		JButton boutonJouer = new JButton("Jouer");
		boutonJouer.addActionListener(new DeclencherJeu());
		boutonJouer.addKeyListener(new ValiderBouton());
		boutonJouer.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.boutonFocus = boutonJouer;

		JButton boutonScores = new JButton("Meilleurs scores");
		boutonScores.addActionListener(new AfficherScores());
		boutonScores.addKeyListener(new ValiderBouton());
		boutonScores.setAlignmentX(Component.CENTER_ALIGNMENT);
		boutonScores.setEnabled(false);

		JButton boutonQuitter = new JButton("Quitter");
		boutonQuitter.addActionListener(new ActionQuitter());
		boutonQuitter.addKeyListener(new ValiderBouton());
		boutonQuitter.setAlignmentX(Component.CENTER_ALIGNMENT);

		/* Et on ajoute les éléments, avec des marges...*/
		this.add(Box.createRigidArea(new Dimension(0,80)));
		this.add(titre);
		int marge = 100;
		this.add(Box.createRigidArea(new Dimension(0,marge)));
		this.add(boutonJouer);
		this.add(Box.createRigidArea(new Dimension(0,marge)));
		this.add(boutonScores);
		this.add(Box.createRigidArea(new Dimension(0,marge)));
		this.add(boutonQuitter);
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

	/** Déclenche le lancement du jeu (envoie au choix des niveaux).
	 */
	private class DeclencherJeu implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			fenetre.afficherMenuNiveau();
		}
	}

	/** Affiche les meilleurs scores.
	 */
	private class AfficherScores implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			fenetre.afficherScores();
		}
	}

	/** Quitte la fenêtre du jeu.
	 */
	private class ActionQuitter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			fenetre.quitter();
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
