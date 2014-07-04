package de.xgme.util.candidatekey;

public class FunctionalDependency {
	private final AttributeSet left;
	private final AttributeSet right;

	public FunctionalDependency(final AttributeSet left,
			final AttributeSet right) {

		this.left = left;
		this.right = right;
	}

	public AttributeSet getLeft() {
		return left;
	}

	public AttributeSet getRight() {
		return right;
	}
}
