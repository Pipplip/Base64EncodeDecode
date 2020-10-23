package MainPackage;

public enum TabIndex {

	File (0),
	Text (1);
	
	public final int index;
	
	private TabIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
