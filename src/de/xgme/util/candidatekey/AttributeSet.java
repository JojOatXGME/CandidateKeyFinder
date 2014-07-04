package de.xgme.util.candidatekey;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class AttributeSet implements Set<Attribute> {
	private static Map<Set<Attribute>, AttributeSet> setMap = new HashMap<>();
	private final SortedSet<Attribute> attributes;

	private AttributeSet(final SortedSet<Attribute> attributes) {
		this.attributes = Collections.unmodifiableSortedSet(attributes);
	}

	public boolean isSubsetOf(final AttributeSet other) {
		return other.attributes.containsAll(attributes);
	}

	public AttributeSet merge(final AttributeSet other) {
		SortedSet<Attribute> newSet = new TreeSet<>(attributes);
		newSet.addAll(other.attributes);
		return AttributeSet.getSet(newSet);
	}

	public AttributeSet[] getTreeChildren() {
		int attrAmount = attributes.size();
		if (attrAmount <= 1) {
			return new AttributeSet[0];
		}

		final AttributeSet[] children = new AttributeSet[attrAmount];
		Iterator<Attribute> it = attributes.iterator();
		Attribute attr = it.next();
		for (int i = 0;; ++i) {
			SortedSet<Attribute> childAttr = attributes.headSet(attr);
			if (it.hasNext()) {
				attr = it.next();
				childAttr = new TreeSet<>(childAttr);
				childAttr.addAll(attributes.tailSet(attr));
				children[i] = AttributeSet.getSet(childAttr);
			} else {
				children[i] = AttributeSet.getSet(childAttr);
				return children;
			}
		}
	}

	// ------------------------------------------------------------

	@Override
	public String toString() {
		String out = "";
		for (final Attribute attr : attributes) {
			out += attr;
		}
		return out;
	}

	// ------------------------------------------------------------

	@Override
	public boolean add(Attribute e) {
		throw new UnsupportedOperationException("AttributeSet is immutable.");
	}

	@Override
	public boolean addAll(Collection<? extends Attribute> c) {
		throw new UnsupportedOperationException("AttributeSet is immutable.");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("AttributeSet is immutable.");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("AttributeSet is immutable.");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("AttributeSet is immutable.");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("AttributeSet is immutable.");
	}

	@Override
	public boolean contains(Object o) {
		return attributes.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return attributes.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return attributes.isEmpty();
	}

	@Override
	public Iterator<Attribute> iterator() {
		return attributes.iterator();
	}

	@Override
	public int size() {
		return attributes.size();
	}

	@Override
	public Object[] toArray() {
		return attributes.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return attributes.toArray(a);
	}

	@Override
	public int hashCode() {
		return attributes.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return this.attributes.equals(obj);
		AttributeSet other = (AttributeSet) obj;
		return this.attributes.equals(other.attributes);
	}

	// ------------------------------------------------------------

	public static AttributeSet getSet(Set<Attribute> attributes) {
		if (attributes == null)
			throw new IllegalArgumentException("Attributes cannot be null.");

		AttributeSet set = setMap.get(attributes);
		if (set == null) {
			attributes = new TreeSet<>(attributes);
			set = new AttributeSet((SortedSet<Attribute>) attributes);
			setMap.put(attributes, set);
		}
		return set;
	}

	public static AttributeSet getSet(final Attribute attribute) {
		SortedSet<Attribute> set = new TreeSet<>();
		set.add(attribute);
		return AttributeSet.getSet(set);
	}
}
