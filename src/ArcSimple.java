import graphe.Sommet;

public class ArcSimple {

	private Sommet origine;
	private Sommet destination;

	public ArcSimple(Sommet origine, Sommet destination) {
		this.origine = origine;
		this.destination = destination;
	}

	public Sommet origine() {
		return this.origine;
	}

	public Sommet destination() {
		return this.destination;
	}

	@Override public String toString() {
		StringBuilder res = new StringBuilder();

		res.append("(");
		res.append(this.origine().toString());
		res.append(",");
		res.append(this.destination().toString());
		res.append(")");

		return res.toString();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null) {
			return false;
		}

		if (getClass() != o.getClass()) {
			return false;
		}

		return ((ArcSimple) o).origine().equals(this.origine()) && ((ArcSimple) o).destination().equals(this.destination());
	}
}