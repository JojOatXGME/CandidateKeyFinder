package de.xgme.util.candidatekey;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class CUI {

	private CUI() {
		// make it "impossible" to create an instance.
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Too few arguments.");
			System.exit(1);
		}

		final AttributeSet all = makeAttributeSet(args[0]);
		final Collection<FunctionalDependency> fds = new LinkedList<>();

		for (int i = 1; i < args.length; ++i) {
			String[] parts = args[i].split("->");
			if (parts.length != 2) {
				System.err.println("Illegal argument (\"" + args[i] + "\")");
				System.exit(1);
			}
			final AttributeSet left = makeAttributeSet(parts[0]);
			final AttributeSet right = makeAttributeSet(parts[1]);
			fds.add(new FunctionalDependency(left, right));
		}

		CandidateKeyFinder finder = new CandidateKeyFinder(all, fds);
		finder.find();
		for (final AttributeSet set : finder.getCandidateKeys()) {
			System.out.println(set);
		}
	}

	private static AttributeSet makeAttributeSet(String str) {
		Set<Attribute> set0 = new TreeSet<>();
		for (int i = 0; i < str.length(); ++i) {
			set0.add(new Attribute("" + str.charAt(i)));
		}
		return AttributeSet.getSet(set0);
	}
}
