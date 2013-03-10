package modele;

import java.util.*;

/** Représente un niveau de difficulté du jeu.
 */
public enum Niveau
{
	FACILE {
		public String toString() {
			return "facile";
		}
	},
	MOYEN {
		public String toString() {
			return "moyen";
		}
	},
	DIFFICILE {
		public String toString() {
			return "difficile";
		}
	},
	EXPERT {
		public String toString() {
			return "expert";
		}
	},
	PERSONALISE {
		public String toString() {
			return "personnalisé";
		}
	}
}
