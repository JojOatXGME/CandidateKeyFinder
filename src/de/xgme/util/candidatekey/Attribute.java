package de.xgme.util.candidatekey;

public class Attribute implements Comparable<Attribute> {
	private final String name;

	public Attribute(final String name) {
		if (name == null)
			throw new IllegalArgumentException("Name cannot be null.");

		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int compareTo(Attribute o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (!name.equals(other.name))
			return false;
		return true;
	}
}
