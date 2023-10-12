import graphe.Sommet;
import graphe.Graphe;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class VoyageurDuCommerce {

	protected static List<Sommet> resolution_voyageur_de_commerce(Graphe g, Map<ArcSimple, List<Sommet>> chemins_arc, Sommet sommet_depart) {
		Set<Sommet> sommet_a_visiter = new HashSet<Sommet>(g.sommets());
		sommet_a_visiter.remove(sommet_depart);

		return resolution_vdc_recursif(g, chemins_arc, sommet_depart, sommet_depart, sommet_a_visiter);
	}

	private static List<Sommet> resolution_vdc_recursif(Graphe g, Map<ArcSimple, List<Sommet>> chemins_arc, Sommet sommet_actuel, Sommet sommet_depart, Set<Sommet> sommet_a_visiter) {

		if (sommet_a_visiter.isEmpty()) {

			ArcSimple arcEmprunte = new ArcSimple(sommet_actuel, sommet_depart);

			return chemins_arc.get(arcEmprunte);
		}

		Integer score_min = Integer.MAX_VALUE; // Infinie
		List<Sommet> chemin_optimale = null;

		for (Sommet sommet_destination : sommet_a_visiter) {

			Set<Sommet> sommet_a_visiter_copie = new HashSet<Sommet>(sommet_a_visiter);
			sommet_a_visiter_copie.remove(sommet_destination);

			ArcSimple arcEmprunte = new ArcSimple(sommet_actuel, sommet_destination);
			List<Sommet> chemin = chemins_arc.get(arcEmprunte);

			chemin.addAll(resolution_vdc_recursif(g, chemins_arc, sommet_destination, sommet_depart, sommet_a_visiter_copie));
			// Concatenation des listes

			if (chemin.size() < score_min) {
				score_min = chemin.size();
				chemin_optimale = chemin;
			}
		}

		return chemin_optimale;

	}

}
