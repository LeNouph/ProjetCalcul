package calcul;

import java.util.*;

/** Représente un opérateur arithmétique.
 */
public enum Operateur
{
	ADDITION {
		public String toString() {
			return "+";
		}
	},
	SOUSTRACTION {
		public String toString() {
			return "-";
		}
	},
	MULTIPLICATION {
		public String toString() {
			return "x";
		}
	},
	DIVISION {
		public String toString() {
			return "÷";
		}
	}
}
