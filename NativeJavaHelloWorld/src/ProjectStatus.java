
public enum ProjectStatus {
	Start ("2", ".start."), Running("1", ".running."), Completed ("3", ".completed");

	ProjectStatus(String i, String string) {
		id = i;
		name = string;
	}
	
	private final String id;
	private final String name;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	
}
