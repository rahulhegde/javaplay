public enum ElectricState {
	H(1),
	HE(2),
	NE(4);

	public final int label;

	private ElectricState(int label) {
		this.label = label;
	}
}