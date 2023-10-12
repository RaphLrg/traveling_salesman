import graphe.Sommet;
import graphe.GrapheListe;
import graphe.Graphe;
import graphe.Arc;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Dijkstra {

	private static Map<Sommet, Integer> initialisation_Dijkstra(Graphe graphe, Sommet sommet_debut) {
		Map<Sommet, Integer> distance = new HashMap<Sommet, Integer>();

		for (Sommet sommet : graphe.sommets()) {
			distance.put(sommet, Integer.MAX_VALUE); // Infini
		}

		distance.put(sommet_debut, 0);

		return distance;
	}

	private static Sommet trouver_min(Collection<Sommet> sommets, Map<Sommet, Integer> distance) {
		Integer mini = Integer.MAX_VALUE; // Infini

		Sommet sommet = null;

		for (Sommet s : sommets) {
			if (distance.get(s) < mini) {
				mini = distance.get(s);
				sommet = s;
			}
		}

		return sommet;
	}

	private static void maj_arbre_recouvrant(Map<Sommet, Integer> distance, Map<Sommet, Sommet> predecesseurs, Sommet s1, Sommet s2) {

		if (distance.get(s2) > distance.get(s1) + 1) {
			distance.put(s2, distance.get(s1) + 1);
			predecesseurs.put(s2, s1);
		}
	}

	private static Set<Sommet> sommets_voisin(GrapheListe graphe, Sommet sommet) {
		Set<Sommet> voisins = new HashSet<Sommet>();

		for (Arc arc : graphe.voisins(sommet)) {
			voisins.add(arc.destination());
		}

		return voisins;
	}

	private static boolean reste_sommet_atteignable(Collection<Sommet> sommet_restant, Map<Sommet, Integer> distance) {

		for (Sommet sommet : sommet_restant) {
			if (distance.get(sommet) < Integer.MAX_VALUE) { // Si la distance != infinie
				return true;
			}
		}
		return false;
	}

	private static Map<Sommet, List<Sommet>> chemins_jusqua_adresse(Map<Sommet, Sommet> predecesseurs, Sommet sommet_depart, Collection<Sommet> sommets_arrive) {
		Map<Sommet, List<Sommet>> chemins = new HashMap<Sommet, List<Sommet>>();

		for (Sommet sommet_arrive : sommets_arrive) {
			List<Sommet> chemin = new ArrayList<Sommet>();
			chemin.add(sommet_arrive);

			Sommet precedant = predecesseurs.get(sommet_arrive);
			if (!precedant.equals(sommet_depart)) {
				chemin.add(0, precedant);
			}

			while (predecesseurs.containsKey(precedant)) {
				precedant = predecesseurs.get(precedant);

				if (!precedant.equals(sommet_depart)) {
					chemin.add(0, precedant);
				}
			}

			chemins.put(sommet_arrive, chemin);
		}

		return chemins;
	}

	protected static Map<Sommet, List<Sommet>> dijkstra_adapte(GrapheListe graphe, Collection<Sommet> sommets_adresse_depot, Sommet sommet_debut) {
		Set<Sommet> sommets_adresse_depot_copie = new HashSet<Sommet>(sommets_adresse_depot);
		sommets_adresse_depot_copie.remove(sommet_debut); // Important, sinon l'algorithme ne démarre pas

		Map<Sommet, Sommet> predecesseurs = new HashMap<Sommet, Sommet>();
		Map<Sommet, Integer> distance = initialisation_Dijkstra(graphe, sommet_debut);
		Set<Sommet> sommet_restant = new HashSet<Sommet>(graphe.sommets());

		while (reste_sommet_atteignable(sommet_restant, distance)) {
			Sommet s1 = trouver_min(sommet_restant, distance);
			sommet_restant.remove(s1);

			if (!sommets_adresse_depot_copie.contains(s1)) { 
				/* Si le sommet est une addresse ou un dépot on ne continue pas le parcours,
				   pour respecter la règle de ne pas passer par une adresse.
				 */
				for (Sommet s2 : sommets_voisin(graphe, s1)) {
					maj_arbre_recouvrant(distance, predecesseurs, s1, s2);
				}
			}
		}

		return chemins_jusqua_adresse(predecesseurs, sommet_debut, sommets_adresse_depot_copie);
	}

}