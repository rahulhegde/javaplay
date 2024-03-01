
public class SubProject {
	String name;
	ProjectStatus status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProjectStatus getStatus() {
		return status;
	}
	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SubProject [name=" + name + ", status=" + status + "]";
	}
	
	
}
