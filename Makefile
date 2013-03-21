###############################################################################
########################## MAKEFILE - PROJET CALCUL ###########################
###############################################################################


# Nom du dossier des sources
SRC = src
# Nom du dossier des binaires
BIN = bin
# Nom du dossier contenant les tests
TEST = tests
# Définition du compilateur
JC = javac
# Options de compilation
JFLAGS = -d $(BIN) -sourcepath $(SRC) -classpath $(BIN) -encoding utf-8 -Xlint
# Options de compilation pour les tests
JTESTFLAGS = -d $(BIN)/$(TEST) -sourcepath $(SRC)/$(TEST) -classpath $(BIN)/$(TEST)
# Nom de la classe contenant le main
MAIN = ProjetCalcul

# Règle par défaut, exhaustive.
all: \
	calcul\
	vue\
	modele\
	$(BIN)/ProjetCalcul.class\
	
$(BIN)/ProjetCalcul.class: $(SRC)/ProjetCalcul.java
	$(JC) $(JFLAGS) $<
	
# Règles de compilation du package 'calcul'
calcul: \
	$(BIN)/calcul/GenerateurCalculs.class\
	$(BIN)/calcul/Calcul.class\
	$(BIN)/calcul/Operateur.class
	
$(BIN)/calcul/GenerateurCalculs.class: $(SRC)/calcul/GenerateurCalculs.java
	$(JC) $(JFLAGS) $<

$(BIN)/calcul/Calcul.class: $(SRC)/calcul/Calcul.java
	$(JC) $(JFLAGS) $<

$(BIN)/calcul/Operateur.class: $(SRC)/calcul/Operateur.java
	$(JC) $(JFLAGS) $<

# Règles de compilation du package 'vue'
vue: \
	$(BIN)/vue/Fenetre.class\
	$(BIN)/vue/Menu.class\
	$(BIN)/vue/EcranDeJeu.class\
	$(BIN)/vue/MenuNiveau.class\
	$(BIN)/vue/EcranFinish.class
#	$(BIN)/vue/EcranScores.class

$(BIN)/vue/Fenetre.class: $(SRC)/vue/Fenetre.java
	$(JC) $(JFLAGS) $<

$(BIN)/vue/Menu.class: $(SRC)/vue/Menu.java
	$(JC) $(JFLAGS) $<

$(BIN)/vue/EcranDeJeu.class: $(SRC)/vue/EcranDeJeu.java
	$(JC) $(JFLAGS) $<

$(BIN)/vue/MenuNiveau.class: $(SRC)/vue/MenuNiveau.java
	$(JC) $(JFLAGS) $<

$(BIN)/vue/EcranFinish.class: $(SRC)/vue/EcranFinish.java
	$(JC) $(JFLAGS) $<

$(BIN)/vue/EcranScores.class: $(SRC)/vue/EcranScores.java
	$(JC) $(JFLAGS) $<

# Règles de compilation du package 'modele'
modele: \
	$(BIN)/modele/Compteur.class\
	$(BIN)/modele/CompteARebours.class\
	$(BIN)/modele/Jeu.class\
	$(BIN)/modele/Niveau.class\
	$(BIN)/modele/TableauScores.class\
	$(BIN)/modele/Scores.class\
	$(BIN)/modele/Score.class


$(BIN)/modele/Compteur.class: $(SRC)/modele/Compteur.java
	$(JC) $(JFLAGS) $<

$(BIN)/modele/CompteARebours.class: $(SRC)/modele/CompteARebours.java
	$(JC) $(JFLAGS) $<

$(BIN)/modele/Jeu.class: $(SRC)/modele/Jeu.java
	$(JC) $(JFLAGS) $<

$(BIN)/modele/Niveau.class: $(SRC)/modele/Niveau.java
	$(JC) $(JFLAGS) $<

$(BIN)/modele/TableauScores.class: $(SRC)/modele/TableauScores.java
	$(JC) $(JFLAGS) $<

$(BIN)/modele/Scores.class: $(SRC)/modele/Scores.java
	$(JC) $(JFLAGS) $<

$(BIN)/modele/Score.class: $(SRC)/modele/Score.java
	$(JC) $(JFLAGS) $<

# Nettoyage des fichiers objets
clean:
	$(RM) $(BIN)/vue/*.class $(BIN)/modele/.class $(BIN)/calcul/*.class

realclean:
	rm -rf $(BIN)/*

# Lancement du programme
run: all
	java -classpath $(BIN) $(MAIN)

# Règle bonus
love:
	@echo ...not war!
