import graphe.GrapheListe;
import graphe.Graphe;
import graphe.Sommet;
import graphe.Arc;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

public class Main {

	public static void main(String[] args) {
		String path_base = "./data/";

		GrapheListe g1 = GrapheListe.deFichier(path_base + "g1.txt");
		GrapheListe g2 = GrapheListe.deFichier(path_base + "g2.txt");
		GrapheListe g1_sans_metro = GrapheListe.deFichier(path_base + "g1_sans_metro.txt");

		System.out.println("Fin chargement des graphes.\n");

		List<Sommet> les_sommets = new ArrayList<Sommet>(g1.sommets());
		Sommet depot = les_sommets.get(12);

		/* Test à ne pas décommenter (il ne marcherons pas)

		System.out.println("\n");

		Sommet a = les_sommets.get(0);

		Map<Sommet, Integer> distance = initialisation_Dijkstra(g1, a);
		System.out.println("Distance initialisé de g1 : " + distance + "\n");
		System.out.println("Minimum de g1 après initialisation_Dijkstra (devrais être a ) : " + trouver_min(g1.sommets(), distance) + "\n");

		maj_arbre_recouvrant(distance, new HashMap<Sommet, Sommet>(), les_sommets.get(0), les_sommets.get(1));
		System.out.println("Distance initialisé de g1 après maj (doit avoir b = 1) : " + distance + "\n");

		System.out.println("Vérification d'égalité des sommets (doit être True) : " + g2.sommets().contains(depot) + "\n");
		System.out.println("Vérification d'égalité des sommets (doit être False) : " + g2.sommets().contains(a) + "\n");

		System.out.println("Voisin de a (doit avoir b, marron, depot) : " + sommets_voisin(g1, depot) + "\n");

		System.out.println("Distance selon Dijkstra de dépot : " + dijkstra_adapte(g1, g2.sommets(), depot) + "\n");

		*/

		List<Sommet> chemin_optimale_avec_metro = resolution_balade_en_ville(g1, g2, depot);

		System.out.println("Chemin optimale (métro opérationel) : " + chemin_optimale_avec_metro);
		System.out.println("Longueur du chemin : " + chemin_optimale_avec_metro.size() + "\n");

		List<Sommet> chemin_optimale_sans_metro = resolution_balade_en_ville(g1_sans_metro, g2, depot);

		System.out.println("Chemin optimale (métro non opérationel) : " + chemin_optimale_sans_metro);
		System.out.println("Longueur du chemin : " + chemin_optimale_sans_metro.size() + "\n");

	}

	private static void maj_chemins_arc(Map<ArcSimple, List<Sommet>> chemins_arc, Map<Sommet, List<Sommet>> chemins_dikstra, Sommet sommet_depart) {
		for (Sommet sommet_arrive : chemins_dikstra.keySet()) {
			ArcSimple arc = new ArcSimple(sommet_depart, sommet_arrive);

			chemins_arc.put(arc, chemins_dikstra.get(sommet_arrive));
		}
	}

	private static List<Sommet> resolution_balade_en_ville(GrapheListe graphe_ville, GrapheListe graphe_adresse_depot, Sommet depot) {
		Map<ArcSimple, List<Sommet>> chemins_arc = new HashMap<ArcSimple, List<Sommet>>();
		// Contient les chemins par rapport à g1 des arcs de G2 

		for (Sommet sommet_depart : graphe_adresse_depot.sommets()) {

			Map<Sommet, List<Sommet>> chemins_dikstra = Dijkstra.dijkstra_adapte(graphe_ville, graphe_adresse_depot.sommets(), sommet_depart);

			maj_chemins_arc(chemins_arc, chemins_dikstra, sommet_depart);
		}

		return VoyageurDuCommerce.resolution_voyageur_de_commerce(graphe_adresse_depot, chemins_arc, depot);
	}

}