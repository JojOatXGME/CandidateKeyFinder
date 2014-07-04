package de.xgme.util.candidatekey;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CandidateKeyFinder {
	/**
	 * Functional dependencies.
	 */
	private final Map<AttributeSet, AttributeSet> fds = new HashMap<>();
	private final AttributeSet allAttr;
	private final Set<AttributeSet> keys = new HashSet<>();

	public CandidateKeyFinder(final AttributeSet allAttributes,
			final Collection<FunctionalDependency> fds) {

		this.allAttr = allAttributes;
		for (final Attribute attr : allAttributes) {
			final AttributeSet set = AttributeSet.getSet(attr);
			this.fds.put(set, set);
		}
		for (final FunctionalDependency fd : fds) {
			final AttributeSet left = fd.getLeft();
			final AttributeSet right = fd.getRight();
			final AttributeSet oldRight = this.fds.get(left);
			if (oldRight == null) {
				this.fds.put(left, right);
			} else {
				this.fds.put(left, oldRight.merge(right));
			}
		}
	}

	public void find() {
		testSubset(allAttr);
	}

	public Set<AttributeSet> getCandidateKeys() {
		return keys;
	}

	private boolean testSubset(AttributeSet set) {
		boolean subset = false;
		for (AttributeSet child : set.getTreeChildren()) {
			if (isSupKey(child)) {
				subset = true;
				if (!testSubset(child)) {
					keys.add(child);
				}
			}
		}
		return subset;
	}

	private boolean isSupKey(AttributeSet set) {
		AttributeSet oldSet;
		do {
			oldSet = set;
			for (Entry<AttributeSet, AttributeSet> entry : fds.entrySet()) {
				if (entry.getKey().isSubsetOf(set)) {
					set = set.merge(entry.getValue());
				}
			}
		} while (!set.equals(oldSet));
		return allAttr.equals(set);
	}
}
