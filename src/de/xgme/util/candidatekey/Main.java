package de.xgme.util.candidatekey;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;


public class Main {
	private static Set all;
	private static List<String> keys = new LinkedList<>();
	private static Map<Set,Set> rules = new HashMap<>();

	public static void main(String[] args) {
		all = Set.get(args[0]);
		for (int i = 0; i < all.set.length(); ++i) {
			rules.put(Set.get("" + all.set.charAt(i)), Set.get("" + all.set.charAt(i)));
		}
		for (int i = 1; i < args.length; ++i) {
			String[] parts = args[i].split("->", 2);
			final Set left = Set.get(parts[0]);
			final Set right = Set.get(parts[1]);
			Set ret = rules.get(left);
			if (ret == null) {
				rules.put(left, right);
			} else {
				rules.put(left, Set.get(right.set + ret.set));
			}
		}
		testSubset(all.set);
		for (String key : keys) {
			System.out.println(key);
		}
	}
	
	private static boolean testSubset(String set) {
		boolean subset = false;
		for (int i = 0; i < set.length(); ++i) {
			String newSet = set.replace("" + set.charAt(i), "");
			if (isSupKey(Set.get(newSet))) {
				subset = true;
				if (!testSubset(newSet)) {
					keys.add(newSet);
				}
			}
		}
		return subset;
	}
	
	private static boolean isSupKey(Set set) {
		Set oldSet;
		do {
			oldSet = set;
			for (Entry<Set, Set> entry : rules.entrySet()) {
				if (entry.getKey().isSubsetOf(set)) {
					set = Set.get(set.set + entry.getValue().set);
				}
			}
		} while (!set.set.equals(oldSet.set));
		return all.set.equals(set.set);
	}

	private static class Set {
		private static Map<String,Set> sets = new HashMap<>();
		private final String set;
		private boolean supKey = true;
		
		private Set(String set) {
			this.set = set;
		}
		
		private boolean isSubsetOf(Set other) {
			for (int i = 0; i < set.length(); ++i) {
				if (!other.set.contains("" + set.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		
		private static Set get(String set) {
			SortedSet<Character> sortedSet = new TreeSet<>();
			for (int i = 0; i < set.length(); ++i) {
				sortedSet.add(set.charAt(i));
			}
			set = "";
			for (char c : sortedSet) {
				set += c;
			}
			Set ret = sets.get(set);
			if (ret == null) {
				ret = new Set(set);
				sets.put(set, ret);
			}
			return ret;
		}
	}

}
