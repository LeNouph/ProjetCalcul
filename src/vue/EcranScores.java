package vue;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/** Contenu de la fenêtre quand on demande à voir les scores.
 */
public class EcranScores extends JPanel
{
	//
	// Attributs
	//
	
	/** La fenetre dans laquelle est affiché l'écran de fin de partie.*/
	private Fenetre fenetre;

	/** Bouton de retour au menu principal.*/
	private JButton boutonRetour;


	//
	// Constructeur
	//

	/** Crée un écran de fin de partie.
	 * @param fenetre la fenetre dans laquelle cet écran est affiché.
	 * @param jeu le jeu qui vient de se terminer.
	 */
	public EcranScores(Fenetre fenetre)
	{
		/* Titre */
		this.fenetre = fenetre;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel titre = new JLabel("");
		titre.setAlignmentX(Component.CENTER_ALIGNMENT);
		titre.setFont(new Font(null, Font.BOLD, 30));
		titre.setForeground(Fenetre.BLEU);

		/* Récapitulatif */
		JLabel score = new JLabel("Votre score est de : "+jeu.getScore());
		score.setAlignmentX(Component.CENTER_ALIGNMENT);
		// affichage du Not Bad
		JLabel notBad = new JLabel(new ImageIcon("img/Obama_Not_Bad.jpg"));
		notBad.setAlignmentX(Component.CENTER_ALIGNMENT);

		/* Bouton de retour au menu */
		boutonRetour = new JButton("Retour au menu principal");
		boutonRetour.addActionListener(new ActionRetour());
		boutonRetour.addKeyListener(new ActionValider());
		boutonRetour.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(Box.createRigidArea(new Dimension(0,50)));
		this.add(titre);
		this.add(Box.createRigidArea(new Dimension(0,50)));
		this.add(score);
		this.add(Box.createRigidArea(new Dimension(0,10)));
		this.add(notBad);
		this.add(Box.createRigidArea(new Dimension(0,50)));
		this.add(boutonRetour);
	}

	
	//
	// Méthodes
	//

	/** Place le focus sur un composant, pour l'ergonomie.*/
	public void placerFocus()
	{
		//boutonRetour.requestFocusInWindow();
	}

	/** Vérifie si le score du joueur mérite d'être enregistré.*/
	public void comparerScore()
	{
		try
		{
			// Tentative de lecture du fichier de scores
			ObjectInputStream flotLecture = new ObjectInputStream(
					new FileInputStream("scores.obj"));
			Object o = flotLecture.readObject();
			flotLecture.close();
			// Si ça marche et qu'on a bien lu un tableau de scores,
			// on vérifie si le score mérite d'être enregistré
			if (o instanceof TableauScores)
			{
				TableauScores tab = (TableauScores)o;
				System.out.println("Scores lus :\n" + tab);
				if (tab.accepte(this.jeu.getScore(), this.jeu.getNiveau()))
					enregistrerScore(tab);
			}
			else
				System.out.println("Attention : objet lu de type incorrect !");
		}
		catch (FileNotFoundException fnfe)
		{
			// Si le fichier n'existe pas, on enregistre à partir de rien
			enregistrerScore(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/** Propose à l'utilisateur d'enregistrer son score.
	 * Si la fonction reçoit 'null', un nouveau fichier sera créé avec le score
	 * du joueur.
	 * @param tab tableau des scores lu dans le fichier.
	 */
	public void enregistrerScore(TableauScores tab)
	{
		// On demande son nom au joueur
		String msg = "Votre score figure parmi les " + Scores.NB_SCORES;
		msg += " meilleurs pour ce niveau !\nQuel est votre nom ?";
		String nom = JOptionPane.showInputDialog(null, msg, "Bravo !", 1);

		if (nom != null) // Si le joueur n'a pas annulé
		{
			// Si pas encore de scores enregistrés, on en crée.
			if (tab == null)
				tab = new TableauScores();

			// Et puis on enregistre le score
			Jeu j = this.jeu;
			tab.enregistrer(j.getScore(), nom, j.getNiveau());
			try
			{
				ObjectOutputStream flotEcriture = new ObjectOutputStream(
						new FileOutputStream("scores.obj"));
				flotEcriture.writeObject(tab);
				flotEcriture.close();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
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

	/** Écoute le bouton de retour au menu.*/
	private class ActionRetour implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			fenetre.afficherMenu();
		}
	}
}

